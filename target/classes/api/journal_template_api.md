# 凭证模板

## 1. 凭证模板列表查询

**接口描述**：查询凭证模板列表

**请求URL**：`GET /api/journal-template/list`

**请求参数**：
```json
{
    "remark": "Bond",  // 备注类型：Bond、CD、EFB、UST、Interbank、Fixed Deposit、Repo、FX
    "templateType": "PAOB Buy",  // 模板类型：PAOB Buy、PAOB Sell、PAOB Borrow、PAOB Lend、支付利息、支付本金 & 利息、收取本金、收取本金 & 利息、Repo、FX、计提利息、计提估值变动
    "flowType": "付款",  // 流水类型：付款、收款
    "templateName": "模板1",  // 模板名称，支持模糊搜索
    "pageNum": 1,  // 页码
    "pageSize": 10  // 每页大小
}
```

**响应结果**：

```json
{
    "code": 000000,
    "message": "success",
    "data": {
        "total": 100,
        "pageNum": 1,
        "pageSize": 10,
        "records": [
            {
                "subList": [
                        {
                            "accountedCr": 0,
                            "accountedDr": 0,
                            "booksNo": "JT_SOB",
                            "citJournalTempId": "329C168E3EA2A98BE063B739210A81",
                            "createdBy": "INVESTOP",
                            "createdDate": 1744523603000,
                            "dataState": "0",
                            "direction": "D",
                            "effectiveDate": 1744523603000,
                            "enteredCr": 0,
                            "enteredDr": 0,
                            "journalLineDescription": "111",
                            "payDirection": "B",
                            "pEnteredCr": 0,
                            "pEnteredDr": 0,
                            "segment1": "000105 合并调整_保险业（HFM）",
                            "segment2": "0000 缺省",
                            "segment3": "0000 缺省",
                            "segment4": "000000 缺省",
                            "segment5": "100101 现金1（pos机）",
                            "segment6": "000000 缺省",
                            "segment7": "000000",
                            "segment8": "0000 缺省",
                            "streamType": "银行借款利息",
                            "tempCategory": "4",
                            "tempName": "rteswt23223",
                            "tempNo": 4271,
                            "updatedBy": "INVESTOP",
                            "updatedDate": 1744523603000
                        }
                ],
                 "remark": "Bond",
                "tempType": "PAOB Buy",
                 "streamType": "付款",
                 "tempName": "rteswt23223",
                 "updatedBy": "INVESTOP",
                 "updatedDate": 1744523603000
            }
        ]
    }
}
```

**处理逻辑**：
1. 按更新时间倒序排序
2. 以模板维度合并显示
3. 支持多条件组合查询
4. 支持分页查询

## 2. 新增凭证模板

**接口描述**：新增凭证模板

**请求URL**：`POST /api/journal-template/add`

**请求参数**：
```json
{
    "JournalTempList": [
        {
            "booksNo": "HK_SOB",
             "remark": "Bond",  // 备注类型
   			 "tempType": "PAOB Buy",  // 模板类型
    		"streamType": "付款",  // 流水类型
            "direction": "G",
            "journalLineDescription": ["test"],
            "segment1": "800000",
            "segment1Type": "",
            "segment2": "0000",
            "segment3": "0000",
            "segment4": "000000",
            "segment5": "1001000000",
            "segment6": "000000",
            "segment8": "0000",
            "tempName": "test"
        },
        {
            "booksNo": "HK_SOB",
             "remark": "Bond",  // 备注类型
   			 "tempType": "PAOB Buy",  // 模板类型
    		"streamType": "付款",  // 流水类型
            "direction": "G",
            "journalLineDescription": ["test"],
            "segment1": "800000",
            "segment1Type": "",
            "segment2": "0000",
            "segment3": "0000",
            "segment4": "000000",
            "segment5": "1001000000",
            "segment6": "000000",
            "segment8": "0000",
            "tempName": "test"
        }
    ]
}
```

**响应结果**：
```json
{
    "code": 000000,
    "message": "success",
    "data":null
}
```

**处理逻辑**：
1. 校验模板名称是否重复
2. 校验必填字段
3. 保存凭证模板信息
4. 保存凭证分录信息
5. 设置初始状态为"未提交"

## 3. 编辑凭证模板

**接口描述**：编辑凭证模板

**请求URL**：`PUT /api/journal-template/{id}`

**请求参数**：
```json
{
    "JournalTempList": [
        {
            "booksNo": "HK_SOB",
             "remark": "Bond",  // 备注类型
   			 "tempType": "PAOB Buy",  // 模板类型
    		"streamType": "付款",  // 流水类型
            "direction": "G",
            "journalLineDescription": ["test"],
            "segment1": "800000",
            "segment1Type": "",
            "segment2": "0000",
            "segment3": "0000",
            "segment4": "000000",
            "segment5": "1001000000",
            "segment6": "000000",
            "segment8": "0000",
            "tempName": "test"
        },
        {
            "booksNo": "HK_SOB",
             "remark": "Bond",  // 备注类型
   			 "tempType": "PAOB Buy",  // 模板类型
    		"streamType": "付款",  // 流水类型
            "direction": "G",
            "journalLineDescription": ["test"],
            "segment1": "800000",
            "segment1Type": "",
            "segment2": "0000",
            "segment3": "0000",
            "segment4": "000000",
            "segment5": "1001000000",
            "segment6": "000000",
            "segment8": "0000",
            "tempName": "test"
        }
    ]
}
```

**响应结果**：
```json
{
    "code": 000000,
    "message": "success",
    "data": null
}
```

**处理逻辑**：
1. 校验模板是否存在
2. 校验状态是否为"未提交"
3. 更新凭证模板信息
4. 更新凭证分录信息

## 4. 提交凭证模板

**接口描述**：提交凭证模板

**请求URL**：`POST /api/journal-template/{id}/submit`

**响应结果**：
```json
{
    "code": 000000,
    "message": "success",
    "data": {
        "id": 1,
        "templateName": "模板1",
        "status": "待复核"
    }
}
```

**处理逻辑**：
1. 校验模板是否存在
2. 校验状态是否为"未提交"
3. 更新状态为"待复核"

## 5. 复核凭证模板

**接口描述**：复核凭证模板

**请求URL**：`POST /api/journal-template/{id}/review`

**请求参数**：
```json
{
    "action": "approve",  // 复核动作：approve-通过，reject-拒绝
    "comment": "复核通过"  // 复核意见
}
```

**响应结果**：
```json
{
    "code": 000000,
    "message": "success",
    "data": {
        "id": 1,
        "templateName": "模板1",
        "status": "复核通过"
    }
}
```

**处理逻辑**：
1. 校验模板是否存在
2. 校验状态是否为"待复核"
3. 根据复核动作更新状态
4. 复合通过的凭证，触发对trade blotter，公司行为，计提数据的匹配进行制证

## 6. 获取凭证模板详情

**接口描述**：获取凭证模板详情

**请求URL**：`GET /api/journal-template/{id}`

**响应结果**：
```json
{
    "code": 000000,
    "message": "success",
    "data": {
        "id": 1,
        "remark": "Bond",
        "templateType": "PAOB Buy",
        "flowType": "付款",
        "templateName": "模板1",
        "ledger": "HK SOB",
        "companySegment": "800000",
        "voucherDate": "流水日期",
        "currency": "USD",
        "account": "1001",
        "subAccount": "000000",
        "businessSegment": "0000",
        "costCenter": "0000",
        "productSegment": "000000",
        "entries": [
            {
                "relatedParty": "0000",
                "entryDirection": "借方",
                "entryAmount": "流水金额",
                "baseAmount": "流水金额",
                "summary": "交易摘要"
            }
        ],
        "status": "复核通过",
        "createdBy": "admin",
        "createdTime": "2024-04-01 10:00:00",
        "updatedBy": "admin",
        "updatedTime": "2024-04-01 10:00:00"
    }
}
```

**处理逻辑**：
1. 校验模板是否存在
2. 返回模板详细信息
3. 返回凭证分录信息 

## 7. 未匹配凭证模板邮件提醒

### 接口描述

对交易，计提，公司行为数据未匹配模板每日九点发送提醒邮件，邮件内容见需求文档

# 会计凭证管理

## 1. 查询凭证列表

### 请求URL

```
GET /api/journal/list
```

### 请求参数

| 参数名             | 类型       | 必填 | 说明                                                         |
| ------------------ | ---------- | ---- | ------------------------------------------------------------ |
| booksNo            | String     | 否   | 账套，默认HK SOB                                             |
| segment1           | String     | 否   | 公司段，默认800000                                           |
| effectiveDateStart | Date       | 否   | 凭证日期开始，默认上月1日                                    |
| effectiveDateEnd   | Date       | 否   | 凭证日期结束，默认今日                                       |
| curNo              | String     | 否   | 币种                                                         |
| amountMin          | BigDecimal | 否   | 金额最小值                                                   |
| amountMax          | BigDecimal | 否   | 金额最大值                                                   |
| journalSequence    | String     | 否   | 凭证编号，支持模糊搜索                                       |
| journalStatus      | String     | 否   | 凭证状态：未制证、待复核、复核通过&待上传、复核通过&上传失败、复核通过&上传成功 |
| checkBy            | String     | 否   | 复核人                                                       |
| pageNum            | Integer    | 否   | 页码，默认1                                                  |
| pageSize           | Integer    | 否   | 每页条数，默认10                                             |

### 响应结果

```json
{
    "code": 000000,
    "message": "success",
    "data": {
        "total": 100,
        "pageNum": 1,
        "pageSize": 10,
        "records": [
            {
                "booksNo": "HK_SOB",
                "categoryName": "导入凭证",
                "checkBy": "ZHENGDACHENG976",
                "createdBy": "HUYONG552",
                "createdDate": 1743061774000,
                "curNo": "HKD",
                "dataState": "15",
                "effectiveDate": 1742486400000,
                "flexibleStatus": "Y",
                "journalDetail": [
                    {
                        "accountedCr": 0,
                        "accountedDr": 0,
                        "balanceStatus": "N",
                        "booksNo": "HK_SOB",
                        "categoryName": "3",
                        "checkBy": "ZHENGDACHENG976",
                        "checkDate": 1744013267000,
                        "citJournalId": "314F27AA8E914A1FE0639522B51EBCA8",
                        "createdBy": "HUYONG552",
                        "createdDate": 1743061774000,
                        "curNo": "HKD",
                        "dataState": "15",
                        "dealNumber": 0,
                        "effectiveDate": 1742486400000,
                        "enteredCr": 0,
                        "enteredDr": 6978.32,
                        "exihibitionCount": 0,
                        "journalLineDescription": "3.21支付宝BY VU LIMITED借款利",
                        "journalSequence": "617600-0000-202503-JV01-0029",
                        "journalStatus": 1,
                        "pEnteredCr": 0,
                        "pEnteredDr": 0,
                        "readyState": "Y",
                        "recordPerson": "HUYONG552",
                        "segment1": "617600",
                        "segment13": "HK_SOB",
                        "segment2": "0000",
                        "segment3": "0000",
                        "segment4": "000000",
                        "segment5": "2251000000",
                        "segment5Description": "Currency Exchange - Temp",
                        "segment6": "000000",
                        "segment7": "000000",
                        "segment8": "0000 缺省",
                        "updatedBy": "SYSTEM",
                        "updatedDate": 1743062048000,
                        "writeOffs": "N"
                    },
                    {
                        "accountedCr": 0.00,
                        "accountedDr": 0.00,
                        "citJournalId": "0",
                        "dealNumber": 0,
                        "enteredCr": 6978.32,
                        "enteredDr": 6978.32,
                        "exihibitionCount": 0,
                        "pEnteredCr": 0,
                        "pEnteredDr": 0,
                        "segment5": "合计共：2笔"
                    }
                ],
                "journalDetailCount": 3,
                "journalSequence": "617600-0000-202503-JV01-0029",
                "journalStatus": 1,
                "key": "",
                "name": "",
                "operator": "HUYONG552",
                "readyState": "Y",
                "segment1": "617600_Wisesome Investment Limited",
                "segment13": "HK_SOB",
                "segment14": "0000",
                "segment2": "0000",
                "updatedBy": "SYSTEM",
                "updatedDate": 1743062048000
            }
        ]
    }
}
```

### 处理逻辑

1. 按凭证日期、凭证编号倒序排序
2. 以凭证编号合并显示相同凭证的数据
3. 支持分页查询

## 2. 导出凭证列表

### 请求URL

```
GET /api/journal/export
```

### 请求参数

同查询凭证列表接口

### 响应结果

Excel文件流

### 处理逻辑

1. 根据查询条件导出数据
2. 文件名格式：凭证列表_yyyyMMddHHmmss.xlsx
3. 按凭证日期、凭证编号倒序排序

## 3. 批量复核通过

### 请求URL

```
POST /api/journal/batch-approve
```

### 请求参数

```json
{
    "journalSequence": ["xxx", "yyy"],
}
```

### 响应结果

```json
{
    "code": 000000,
    "message": "已复核通过2条会计凭证"
}
```

### 处理逻辑

1. 校验凭证状态必须为"待复核"
2. 校验复核人不能是凭证创建人
3. 更新凭证状态为"复核通过&待上传"
4. 更新复核人和复核时间

## 4. 复核通过

### 请求URL

```
POST /api/journal/approve
```

### 请求参数

```json
{
    "journalSequence": "111111"
}
```

### 响应结果

```json
{
    "code": 000000,
    "message": "复合通过成功"
}
```

### 处理逻辑

1. 校验凭证状态必须为"待复核"
2. 校验复核人不能是凭证创建人
3. 更新凭证状态为"复核通过&待上传"
4. 更新复核人和复核时间

## 5. 复核不通过

### 请求URL

```
POST /api/journal/unApprove
```

### 请求参数

```json
{
    "journalSequence": "111111"
}
```

### 响应结果

```json
{
    "code": 000000,
    "message": "复合不通过成功"
}
```

### 处理逻辑

1. 校验凭证状态必须为"待复核"
2. 校验复核人不能是凭证创建人
3. 更新凭证状态为"复核通过&待上传"
4. 更新复核人和复核时间

## 6. 批量复核拒绝

### 请求URL

```
POST /api/journal/batch-reject
```

### 请求参数

```json
{
    "journalSequence": ["xxx", "yyy"],
}
```

### 响应结果

```json
{
    "code": 000000,
    "message": "已复核拒绝2条会计凭证"
}
```

### 处理逻辑

1. 校验凭证状态必须为"待复核"
2. 校验复核人不能是凭证创建人
3. 更新凭证状态为"复核拒绝"
4. 更新复核人和复核时间

## 7. 凭证模板下载

### 请求URL

```
GET /api/journal/template
```

### 请求参数

无

### 响应结果

Excel文件流

### 处理逻辑

1. 返回凭证导入模板
2. 文件名：凭证模板.xlsx

## 8. 凭证导入

### 请求URL

```
POST /api/journal/import
```

### 请求参数

| 参数名 | 类型 | 必填 | 说明      |
| ------ | ---- | ---- | --------- |
| file   | File | 是   | Excel文件 |

### 响应结果

```json
{
    "code": 000000,
    "message": "已成功上传2条会计凭证，请联系复核同事进行复核处理！"
}
```

### 处理逻辑

1. 校验文件格式必须为xlsx
2. 校验文件内容格式
3. 校验会计凭证数据（借贷平衡等）
4. 保存凭证数据，状态为"待复核"
5. 记录创建人和创建时间

## 9. 编辑凭证

### 请求URL

```
PUT /api/journal/{journalSequence}
```

### 请求参数

```json
{
    "booksNo": "HK SOB",
    "segment1": "800000",
    "effectiveDate": "2024-01-01",
    "curNo": "HKD",
    "segment5": "1001",
    "segment6": "000000",
    "segment2": "0000",
    "segment3": "0000",
    "segment4": "000000",
    "segment8": "0000",
    "enteredDr": 1000.00,
    "enteredCr": 0.00,
    "accountedDr": 1000.00,
    "accountedCr": 0.00,
    "journalLineDescription": "摘要"
}
```

### 响应结果

```json
{
    "code": 000000,
    "message": "success"
}
```

### 处理逻辑

1. 校验凭证状态必须为"待复核"或"复核拒绝"
2. 校验借贷平衡
3. 校验弹性域
4. 更新凭证数据
5. 更新修改人和修改时间

## 10. 查询凭证详情

### 请求URL

```
GET /api/journal/{journalSequence}
```

### 请求参数

无

### 响应结果

```json
{
    "code": 000000,
    "message": "success",
    "data": {
        "journalSequence": "J202401010001",
        "effectiveDate": "2024-01-01",
        "tempType": "PAOB Buy",
        "booksNo": "HK SOB",
        "segment1": "800000",
        "segment5": "1001",
        "curNo": "HKD",
        "enteredDr": 1000.00,
        "enteredCr": 0.00,
        "accountedDr": 1000.00,
        "accountedCr": 0.00,
        "journalStatus": "待复核",
        "flexibleStatus": "Y",
        "createdBy": "admin",
        "checkBy": "reviewer",
        "tmsCitJournalId": "xxx"
    }
}
```

### 处理逻辑

1. 根据ID查询凭证详情
2. 返回凭证所有字段信息 

## 11. 会计凭证上传失败邮件提醒

### 接口描述

对OF返回失败状态的凭证进行邮件提醒，邮件内容见需求文档

# 科目余额查询

## 1. 查询科目余额列表

### 接口描述
查询会计科目余额信息，支持多条件筛选和分页查询。

### 请求URL
```
GET /api/subject-balance/list
```

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| date | String | 是 | 查询日期，格式：yyyy-MM-dd，默认昨天 |
| setOfBooksId | String | 否 | 账套，默认HK SOB |
| segment1 | String | 否 | 公司段，默认800000 |
| currencyCode | String | 否 | 币种 |
| segment3 | String | 否 | 成本中心 |
| segment4 | String | 否 | 产品段 |
| accCode3 | String | 否 | 三级会计科目 |
| segment6 | String | 否 | 子目段 |
| segment8 | String | 否 | 关联方 |
| segment2 | String | 否 | 业务段 |
| pageNum | Integer | 否 | 页码，默认1 |
| pageSize | Integer | 否 | 每页大小，默认10 |

### 响应结果
```json
{
    "code": 000000,  // 响应码，000000表示成功
    "message": "success",  // 响应消息
    "data": {
        "total": 100,  // 总记录数
        "pageNum": 1,  // 当前页码
        "pageSize": 10,  // 每页大小
        "records": [  // 科目余额记录列表
            {
                "codeCombinationId": "CC001",  // 组合代码ID
                "setOfBooksId": "HK SOB",  // 账套代码
                "booksName": "香港账套",  // 账套名称
                "periodName": "2024-04",  // 会计期间
                "currencyCode": "USD",  // 币种
                "segment1": "800000",  // 公司段
                "segment1Description": "公司A",  // 公司段描述
                "segment2": "B001",  // 业务段
                "segment2Description": "业务A",  // 业务段描述
                "segment3": "C001",  // 成本中心
                "segment3Description": "成本中心A",  // 成本中心描述
                "segment4": "P001",  // 产品段
                "segment4Description": "产品A",  // 产品段描述
                "accCode1": "1000",  // 一级科目代码
                "accCode1Name": "资产类",  // 一级科目名称
                "accCode2": "1001",  // 二级科目代码
                "accCode2Name": "流动资产",  // 二级科目名称
                "accCode3": "100101",  // 三级科目代码
                "accCode3Name": "现金",  // 三级科目名称
                "segment6": "S001",  // 子目段
                "segment6Description": "子目A",  // 子目段描述
                "segment7": "R001",  // 备用段7
                "segment8": "R001",  // 关联方
                "segment8Description": "关联方A",  // 关联方描述
                "beginBalanceDr": 1000.00,  // 期初借方余额
                "beginBalanceCr": 0.00,  // 期初贷方余额
                "periodNetDr": 500.00,  // 本期借方发生额
                "periodNetCr": 200.00,  // 本期贷方发生额
                "endBalanceDr": 1300.00,  // 期末借方余额
                "endBalanceCr": 200.00,  // 期末贷方余额
                "endBalance": 1100.00  // 期末余额（借方余额-贷方余额）
            }
        ]
    }
}
```

### 处理逻辑
1. 参数校验：
   - 日期必填，默认取昨天
   - 账套默认取HK SOB
   - 公司段默认取800000

2. 查询条件：
   - 根据传入的参数构建查询条件
   - 支持模糊查询的字段：科目代码、科目名称
   - 支持精确匹配的字段：账套、公司段、币种等

3. 排序规则：
   - 按账套、公司段、科目正序排序

4. 分页处理：
   - 默认每页10条记录
   - 支持自定义每页大小

## 2. 导出科目余额列表

### 接口描述
导出科目余额列表数据到Excel文件。

### 请求URL
```
GET /api/subject-balance/export
```

### 请求参数
同查询科目余额列表接口

### 响应结果
Excel文件流

### 处理逻辑
1. 根据查询条件导出数据
2. 文件名格式：科目余额列表_yyyyMMddHHmmss.xlsx
3. 按账套、公司段、科目正序排序

## 3. 查询科目余额详情

### 接口描述
查询指定科目的余额详情。

### 请求URL
```
GET /api/subject-balance/{codeCombinationId}
```

### 请求参数
无

### 响应结果
```json
{
    "code": 000000,
    "message": "success",
    "data": {
        "codeCombinationId": "CC001",
        "setOfBooksId": "HK SOB",
        "booksName": "香港账套",
        "periodName": "2024-04",
        "currencyCode": "USD",
        "segment1": "800000",
        "segment1Description": "公司A",
        "segment2": "B001",
        "segment2Description": "业务A",
        "segment3": "C001",
        "segment3Description": "成本中心A",
        "segment4": "P001",
        "segment4Description": "产品A",
        "accCode1": "1000",
        "accCode1Name": "资产类",
        "accCode2": "1001",
        "accCode2Name": "流动资产",
        "accCode3": "100101",
        "accCode3Name": "现金",
        "segment6": "S001",
        "segment6Description": "子目A",
        "segment7": "R001",
        "segment8": "R001",
        "segment8Description": "关联方A",
        "beginBalanceDr": 1000.00,
        "beginBalanceCr": 0.00,
        "periodNetDr": 500.00,
        "periodNetCr": 200.00,
        "endBalanceDr": 1300.00,
        "endBalanceCr": 200.00,
        "endBalance": 1100.00
    }
}
```

### 处理逻辑
1. 根据组合代码ID查询科目余额详情
2. 返回科目余额所有字段信息 

# 银行账号余额对账

## 1. 查询银行账号余额对账列表

### 接口描述
查询银行账号余额与科目余额的对账信息，支持多条件筛选和分页查询。

### 请求URL
```
GET /api/bank-account-balance/list
```

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| date | String | 是 | 查询日期，格式：yyyy-MM-dd，默认昨天 |
| bankName | String | 否 | 银行名称 |
| accountNumber | String | 否 | 银行账号 |
| currency | String | 否 | 币种 |
| amount | BigDecimal | 否 | 金额 |
| hasDifference | String | 否 | 对账差异：YES-有差异，NO-无差异 |
| pageNum | Integer | 否 | 页码，默认1 |
| pageSize | Integer | 否 | 每页大小，默认10 |

### 响应结果
```json
{
    "code": 000000,  // 响应码，000000表示成功
    "message": "success",  // 响应消息
    "data": {
        "total": 100,  // 总记录数
        "pageNum": 1,  // 当前页码
        "pageSize": 10,  // 每页大小
        "records": [  // 对账记录列表
            {
                "id": 1,  // 记录ID
                "date": "2024-04-11",  // 对账日期
                "bankName": "中国银行",  // 银行名称
                "accountName": "香港公司美元账户",  // 账户名称
                "accountNumber": "012345678",  // 银行账号
                "currency": "USD",  // 币种
                "accountBalance": 10000.00,  // 银行账户余额
                "subject": "1002",  // 会计科目
                "subSubject": "001",  // 会计子目
                "endBalance": 10000.00,  // 科目期末余额
                "differenceAmount": 0.00  // 差异金额（银行账户余额-科目期末余额）
            }
        ]
    }
}
```

### 处理逻辑
1. 参数校验：
   - 日期必填，默认取昨天
   - 其他参数选填

2. 对账处理：
   - 从付款账户表(tms_payment_account)获取银行账户信息
   - 从账户流水表(tms_account_stream)获取银行账户余额
   - 以上两个表通过account_number和currency关联起来
   - 从科目余额表(tms_subject_balance)获取对应科目和子目的期末余额
- 科目余额表通过科目，子目（账套默认是HK_SOB,公司段默认是800000）进行汇总获取end_balance值
   - 计算差异金额
   
3. 排序规则：
   - 按日期倒序
   - 按银行账户正序

4. 分页处理：
   - 默认每页10条记录
   - 支持自定义每页大小

## 2. 导出银行账号余额对账列表

### 接口描述
导出银行账号余额对账数据到Excel文件。

### 请求URL
```
GET /api/bank-account-balance/export
```

### 请求参数
同查询银行账号余额对账列表接口

### 响应结果
Excel文件流

### 处理逻辑
1. 根据查询条件导出数据
2. 文件名格式：银行账号余额对账列表_yyyyMMddHHmmss.xlsx
3. 按日期倒序、银行账户正序排序

## 3. 发送对账差异提醒邮件

### 接口描述
对差额不等于0的银行账户每日九点发送提醒邮件，邮件内容见需求文档
