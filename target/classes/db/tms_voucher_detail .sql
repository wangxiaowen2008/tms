CREATE TABLE tms_voucher_detail (
tms_cit_journal_id VARCHAR(50),
line_mark VARCHAR(10) NOT NULL,
voucher_source VARCHAR(20) NOT NULL,
voucher_category VARCHAR(20) NOT NULL,
business_voucher_num VARCHAR(30)  NULL,
trans_date VARCHAR(10) NOT NULL,
ccy VARCHAR(10) NOT NULL,
exchange_rate_type VARCHAR(10) NOT NULL,
voucher_type VARCHAR(20) NOT NULL,
retention_type_id VARCHAR(20) DEFAULT NULL,
budget_version_id VARCHAR(20) DEFAULT NULL,
set_of_books_id VARCHAR(20) NOT NULL,
company_code VARCHAR(20) NOT NULL,
business_code VARCHAR(20) NOT NULL,
cost_center_code VARCHAR(20) NOT NULL,
production_code VARCHAR(20) NOT NULL,
gl_code VARCHAR(20) NOT NULL,
sub_code VARCHAR(20) NOT NULL,
field1 VARCHAR(20) NOT NULL,
field2 VARCHAR(20) NOT NULL,
dr_tran_amt NUMERIC(18, 2) NOT NULL,
cr_tran_amt NUMERIC(18, 2) NOT NULL,
voucher_batch_name VARCHAR(50) NOT NULL,
batch_desc VARCHAR(100)  NULL,
voucher_name VARCHAR(100)  NULL,
voucher_desc VARCHAR(100)  NULL,
voucher_line_desc VARCHAR(100) NOT NULL,
flexfield11 VARCHAR(100) DEFAULT NULL,
flexfield12 VARCHAR(100) DEFAULT NULL,
flexfield13 VARCHAR(100) DEFAULT NULL,
flexfield14 VARCHAR(100) DEFAULT NULL,
flexfield15 VARCHAR(100) DEFAULT NULL,
flexfield16 VARCHAR(100) DEFAULT NULL,
flexfield17 VARCHAR(100) DEFAULT NULL,
flexfield18 VARCHAR(100) DEFAULT NULL,
flexfield19 VARCHAR(100) DEFAULT NULL,
flexfield20 VARCHAR(100) DEFAULT NULL,
system_id VARCHAR(20) NOT NULL,
dr_balance NUMERIC(18, 2)  NULL,
cr_balance NUMERIC(18, 2)  NULL,
dr_balance_prev NUMERIC(18, 2)  NULL,
cr_balance_prev NUMERIC(18, 2)  NULL,
journal_status VARCHAR(50) DEFAULT '1'
);
-- 添加表注释
COMMENT ON TABLE tms_voucher_detail IS '提供给OF的凭证明细表';

-- 添加字段注释
COMMENT ON COLUMN tms_voucher_detail.line_mark IS ' 行标识 ';
COMMENT ON COLUMN tms_voucher_detail.voucher_source IS ' 凭着来源 ';
COMMENT ON COLUMN tms_voucher_detail.voucher_category IS ' 凭证类别 ';
COMMENT ON COLUMN tms_voucher_detail.business_voucher_num IS ' 业务凭证编号 ';
COMMENT ON COLUMN tms_voucher_detail.trans_date IS ' 记账日期 ';
COMMENT ON COLUMN tms_voucher_detail.ccy IS ' 币种 ';
COMMENT ON COLUMN tms_voucher_detail.exchange_rate_type IS ' 汇率类型 ';
COMMENT ON COLUMN tms_voucher_detail.voucher_type IS ' 凭证类型 ';
COMMENT ON COLUMN tms_voucher_detail.retention_type_id IS ' 保留款类型 ID';
COMMENT ON COLUMN tms_voucher_detail.budget_version_id IS ' 预算版本 ID';
COMMENT ON COLUMN tms_voucher_detail.set_of_books_id IS ' 账套 ID';
COMMENT ON COLUMN tms_voucher_detail.company_code IS ' 公司段 ';
COMMENT ON COLUMN tms_voucher_detail.business_code IS ' 业务段 ';
COMMENT ON COLUMN tms_voucher_detail.cost_center_code IS ' 成本中心段 ';
COMMENT ON COLUMN tms_voucher_detail.production_code IS ' 产品段 ';
COMMENT ON COLUMN tms_voucher_detail.gl_code IS ' 科目段 ';
COMMENT ON COLUMN tms_voucher_detail.sub_code IS ' 子目段 ';
COMMENT ON COLUMN tms_voucher_detail.field1 IS ' 备用字段 1';
COMMENT ON COLUMN tms_voucher_detail.field2 IS ' 备用字段 2';
COMMENT ON COLUMN tms_voucher_detail.dr_tran_amt IS ' 借方发生额 ';
COMMENT ON COLUMN tms_voucher_detail.cr_tran_amt IS ' 贷方发生额 ';
COMMENT ON COLUMN tms_voucher_detail.voucher_batch_name IS ' 凭证批名 ';
COMMENT ON COLUMN tms_voucher_detail.batch_desc IS ' 批描述 ';
COMMENT ON COLUMN tms_voucher_detail.voucher_name IS ' 凭证名 ';
COMMENT ON COLUMN tms_voucher_detail.voucher_desc IS ' 凭证描述 ';
COMMENT ON COLUMN tms_voucher_detail.voucher_line_desc IS ' 凭证行描述 ';
COMMENT ON COLUMN tms_voucher_detail.flexfield11 IS ' 弹性域 11';
COMMENT ON COLUMN tms_voucher_detail.flexfield12 IS ' 弹性域 12';
COMMENT ON COLUMN tms_voucher_detail.flexfield13 IS ' 弹性域 13';
COMMENT ON COLUMN tms_voucher_detail.flexfield14 IS ' 弹性域 14';
COMMENT ON COLUMN tms_voucher_detail.flexfield15 IS ' 弹性域 15';
COMMENT ON COLUMN tms_voucher_detail.flexfield16 IS ' 弹性域 16';
COMMENT ON COLUMN tms_voucher_detail.flexfield17 IS ' 弹性域 17';
COMMENT ON COLUMN tms_voucher_detail.flexfield18 IS ' 弹性域 18';
COMMENT ON COLUMN tms_voucher_detail.flexfield19 IS ' 弹性域 19';
COMMENT ON COLUMN tms_voucher_detail.flexfield20 IS ' 弹性域 20';
COMMENT ON COLUMN tms_voucher_detail.system_id IS ' 系统编号 ';
COMMENT ON COLUMN tms_voucher_detail.dr_balance IS ' 借方余额 ';
COMMENT ON COLUMN tms_voucher_detail.cr_balance IS ' 贷方余额 ';
COMMENT ON COLUMN tms_voucher_detail.dr_balance_prev IS ' 上日借方余额 ';
COMMENT ON COLUMN tms_voucher_detail.cr_balance_prev IS ' 上日贷方余额 ';
COMMENT ON COLUMN tms_voucher_detail.journal_status IS '2: 待上传；1: 上传中；4: 上传成功；3: 上传失败 ';