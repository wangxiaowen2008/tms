在 approveJournal方法尾部加上如下需求代码：
会计凭证复核通过或批量复核通过后，会往tms_voucher_detail（提供给OF的凭证明细表）插入记录，具体字段取值TmsCitJournal对象值，逻辑如下：
tms_cit_journal_id 取值 tmsCitJournalId，
line_mark 取值 '10'
voucher_source 取值 '业务'
voucher_category 取值 '记帐凭证'
business_voucher_num 取值 空值
trans_date 取值 effectiveDate
ccy 取值 curNo
exchange_rate_type 取值 '公司'
voucher_type 取值 'A'
retention_type_id 取值 空值
budget_version_id 取值 空值
set_of_books_id 取值 '122'
company_code 取值  segment1
business_code 取值 segment2
cost_center_code 取值 segment3
production_code 取值 segment4
gl_code 取值 segment5
sub_code 取值 segment6
field1 取值 '0000'
field2 取值 segment8
dr_tran_amt 取值 enteredDr
cr_tran_amt 取值 enteredCr
voucher_batch_name 取值 journalSequence
batch_desc 取值 空值
voucher_name 取值 journalSequence
voucher_desc 取值 空值
voucher_line_desc 取值 journalLineDescription
flexfield11 取值 空值
flexfield12 取值 空值
flexfield13 取值 空值
flexfield14 取值 空值
flexfield15 取值 空值
flexfield16 取值 空值
flexfield17 取值 空值
flexfield18 取值 空值
flexfield19 取值 空值
flexfield20 取值 空值
system_id 取值 'PAOB-TMS'

2. 上传凭证
1. 文件目录地址在配置文件中配置，根据voucherDetail对象，生成 A23_yyyymmdd_000000_100.txt 文件，根据凭证编号不同，生成多个文件
文件名解释：A23是固定，yyyyyMMdd是当前日期，0000000是公司段，取值 CompanyCode，从100开始，按步长1 自增长
文件内容：取以下字段值，字段之间通过 | 进行分割
lineMark,voucherSource,voucherCategory,businessVoucherNum,transDate,ccy,exchangeRateType,voucherType,retentionTypeId,budgetVersionId,setOfBooksId,companyCode,businessCode,costCenterCode,productionCode,glCode,subCode,field1,field2,drTranAmt,crTranAmt,voucherBatchName,batchDesc,voucherName,voucherDesc,voucherLineDesc,flexfield11,flexfield12,flexfield13,flexfield14,
flexfield15,flexfield16,flexfield17,flexfield18,flexfield19,flexfield20,systemId
2.调用FileEncryptionUtil方法对A23_yyyymmdd_000000_100.txt进行处理，有多个文件，进行多次调用，
生成压缩文件 A23_yyyymmdd_000000_001.tar.gz 文件后，生成一个A23_yyyymmdd_000000_001.tar.gz.OK空内容文件，
A23_yyyymmdd_Pw.txt生成一个A23_yyyymmdd_Pw.txt.OK空内容文件，生成一个 A23_yyyymmdd.txt文件，文件内容是A23_yyyymmdd_000000_001.tar.gz（有多个文件，则记录多个）和A23_20360404_Pw.txt，
再生成一个A23_yyyymmdd.txt.OK空内容的文件

保存在 A23_yyyymmdd_Pw.txt 文件中，接着生成 A23_yyyymmdd.txt 文件，以及 A23_yyyymmdd.txt.OK，FTS 根据以下三个.OK 是否存在，
如果都存在就发送给 of，否则不发送。（生成的.tar.gz 和.OK 文件都是存放在 FTS 指定的目录下）
A23_yyyymmdd_000000_001.tar.gz
A23_yyyymmdd_000000_001.tar.gz.OK
A23_yyyymmdd.txt.OK
A23_yyyymmdd.txt 这个文件内容如下，001 应该代表一笔凭证，这样 of 返回的结果也是按这个来
A23_yyyymmdd_000000_001.tar.gz
A23_yyyymmdd_000000_002.tar.gz
A23_20360404_Pw.txt

3.OF返回结果
自动任务扫描，查询表tms_voucher_detail中journal_status='1'的数据，按照file_index分组分别处理，根据company_code，file_index（前8位是日期，后三位是序号），组装成文件名，
A23_yyyymmdd_800000_001_return.txt，然后查找路径${voucher.file.path}，是否存在该文件，存在则读取文件内容，如果文件内容只有 30|ok，那代表凭证都验证通过，则根据file_index的值，journal_status='1'，更新journal_status='4'；否则验证不通过，读取文件所有数据，解析每一行记录，数据之间通过"|"分隔符分割，有四列，
读取第二列，第三列，第四列，其中以第二列为key，value为 第三列+第四列（以冒号隔开），保存在一个map中，通过map的中key等于tms_voucher_detail的voucher_batch_name作为提交，更新msg值为value值，journal_status='3'，同时也更新tms_cit_journal表的journal_status和msg信息

4.接收每日汇率，科目余额 数据
自动任务扫描
1.查找路径${voucher.file.path}+yyyyMMdd,当天是否存在fin_gl_daily_rates_to_hkvb.dat文件，存在读取每一行数据，字段之间通过特殊字符 \u001B 进行分割，依次赋值给表中定义的字段。
判断插入还是更新，根据from_currency, to_currency, conversion_date, conversion_type判断
2.查找路径${voucher.file.path}+yyyyMMdd,当天是否存在fin_gl_balances_to_hkvb.dat文件，存在读取每一行数据，字段之间通过特殊字符 \u001B 进行分割，依次赋值给表中定义的字段
判断插入还是更新，根据code_combination_id，set_of_books_id，period_name，currency_code判断