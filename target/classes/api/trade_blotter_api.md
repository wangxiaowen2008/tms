# Trade Blotter 交易处理

## 1. 列表查询接口

### 接口描述
查询Trade Blotter列表数据，支持条件筛选和分页查询。

### 请求URL
```
GET /api/trade-blotter/list
```

### 请求参数
```json
{
  "remark": "Bond",                    // 备注类型，枚举值：Bond、CD、EFB、UST、Interbank、Fixed Deposit
  "buySellBorrowLend": "PAOB Buy",    // 交易类型，枚举值：PAOB Buy、PAOB Sell、PAOB Borrow、PAOB Lend
  "tradeNumber": "T202404060001",     // 交易编号，支持模糊搜索
  "tradeDateStart": "2024-04-01",     // 交易日期开始，格式：yyyy-MM-dd
  "tradeDateEnd": "2024-04-06",       // 交易日期结束，格式：yyyy-MM-dd
  "valueDateStart": "2024-04-01",     // 起息日期开始，格式：yyyy-MM-dd
  "valueDateEnd": "2024-04-06",       // 起息日期结束，格式：yyyy-MM-dd
  "maturityDateStart": "2024-04-01",  // 到期日期开始，格式：yyyy-MM-dd
  "maturityDateEnd": "2024-04-06",    // 到期日期结束，格式：yyyy-MM-dd
  "currency": "USD",                   // 币种
  "settlementAmountMin": 1000000,     // 结算金额最小值
  "settlementAmountMax": 2000000,     // 结算金额最大值
  "isin": "US123456AB12",             // ISIN码
  "paymentStatus": "未付款",           // 付款状态，枚举值：未付款、付款中、已付款、未收款、已收款
  "journalStatus": "未制证",           // 制证状态，枚举值：未制证、待复核、复核通过 & 待上传、复核通过 & 上传失败、复核通过 & 上传成功
  "journalSequence": "V202404060001",       // 凭证编号，支持模糊搜索
  "pageNum": 1,                        // 页码，默认1
  "pageSize": 10                       // 每页大小，默认10
}
```

### 响应结果
```json
{
  "code": 000000,                         // 状态码
  "message": "操作成功",                // 提示信息
  "data": {
    "total": 100,                      // 总记录数
    "pageNum": 1,                      // 当前页码
    "pageSize": 10,                    // 每页大小
    "records": [
      {
        "item": 1001,                  // 项目编号，倒序排序
        "tradeNumber": "T202404060001", // 交易编号
        "entityId": "ENT001",          // 实体ID
        "counterpart": "Bank A",       // 交易对手方
        "buySellBorrowLend": "PAOB Buy", // 买卖/借贷标识
        "yield": 3.5,                  // 收益率
        "currency": "USD",             // 币种
        "settlementAmount": 1000000.00, // 结算金额
        "tradeDate": "2024-04-06",    // 交易日期
        "valueDate": "2024-04-08",    // 起息日
        "maturityDate": "2025-04-06", // 到期日
        "remark": "Bond",              // 备注
        "dayCount": 360,               // 天数
        "interest": 35000.00,          // 利息
        "price": 100.00,               // 价格
        "debtSecurityName": "US Treasury Bond", // 债务证券名称
        "isin": "US123456AB12",        // ISIN码
        "faceAmount": 1000000.00,      // 面值
        "amountReceivedAtMaturity": 1035000.00, // 到期收到金额
        "interestPaidUpfront": 0.00,   // 预付利息
        "netInterest": 35000.00,       // 净利息
        "couponType": "Fixed",         // 息票类型
        "couponFrequent": "Semi-annual", // 息票频率
        "channel": "Direct",           // 渠道
        "settlementAccount": "HSBC-001", // 结算账户
        "broker": "Broker A",          // 经纪人
        "time": "10:30:00",            // 交易时间
        "countryOfDomicile": "US",     // 注册地国家
        "issuerCountry": "US",         // 发行人国家
        "issuerRating": "AAA",         // 发行人评级
        "paymentStatus": "未付款",      // 付款状态
        "journalStatus": "未制证",      // 制证状态
        "journalSequence": "",               // 凭证编号
        "orderId":"111",               //订单号
        "streamNo":"pkdsd232233",       //流水号
        "existCompany":"Y" ,           //存在公司行为数据标识
        "existAccrual":"Y"            //存在计提数据标识
        "updateBy": "admin",           // 更新人
        "updateTime": "2024-04-06 10:30:00" // 更新时间
      }
    ]
  }
}
```

### 业务逻辑
1. 列表默认按Item倒序排序
2. 操作栏根据条件显示不同按钮：

   - 发起支付：满足以下条件

     1. Trade blotter满足以下条件：

     - Remark值等于Bond并且Buy/Sell/Borrow/Lend字段值等于PAOB Buy
     - Remark值等于EFB并且Buy/Sell/Borrow/Lend字段值等于PAOB Buy
     - Remark值等于Interbank并且Buy/Sell/Borrow/Lend字段值等于PAOB Borrow
     - Remark值等于Fixed Deposit并且Buy/Sell/Borrow/Lend字段值等于PAOB Borrow
     - Remark值等于CD并且Buy/Sell/Borrow/Lend字段值等于PAOB Buy
     - Remark值等于UST并且Buy/Sell/Borrow/Lend字段值等于PAOB Buy

     2. "付/收款状态"值为"未付款"

   - 查看付款订单：订单号（orderId）不为空

   - 查看银行流水：流水号（streamNo）不为空

   - 查看公司行为：根据交易编号（tradeNumber）查看公司行为表，存在返回existCompany='Y'

   - 查看估值：根据交易编号（tradeNumber）查看计提数据表，存在返回existAccrual='Y'

## 2. 详情查询接口

### 接口描述
根据交易编号查询Trade Blotter详细信息。

### 请求URL
```
GET /api/trade-blotter/{tradeNumber}
```

### 请求参数
| 参数名 | 类型 | 是否必须 | 说明 |
|--------|------|----------|------|
| tradeNumber | string | 是 | 交易编号 |

### 响应结果
```json
{
  "code": 000000,                         // 状态码
  "message": "操作成功",                // 提示信息
  "data": {
    "item": 1001,                      // 项目编号
    "tradeNumber": "T202404060001",   // 交易编号
    "entityId": "ENT001",             // 实体ID
    "counterpart": "Bank A",          // 交易对手方
    "buySellBorrowLend": "PAOB Buy",  // 买卖/借贷标识
    "yield": 3.5,                     // 收益率
    "currency": "USD",                // 币种
    "settlementAmount": 1000000.00,   // 结算金额
    "tradeDate": "2024-04-06",       // 交易日期
    "valueDate": "2024-04-08",       // 起息日
    "maturityDate": "2025-04-06",    // 到期日
    "remark": "Bond",                 // 备注
    "dayCount": 360,                  // 天数
    "interest": 35000.00,             // 利息
    "price": 100.00,                  // 价格
    "debtSecurityName": "US Treasury Bond", // 债务证券名称
    "isin": "US123456AB12",           // ISIN码
    "faceAmount": 1000000.00,         // 面值
    "amountReceivedAtMaturity": 1035000.00, // 到期收到金额
    "interestPaidUpfront": 0.00,      // 预付利息
    "netInterest": 35000.00,          // 净利息
    "couponType": "Fixed",            // 息票类型
    "couponFrequent": "Semi-annual",  // 息票频率
    "channel": "Direct",              // 渠道
    "settlementAccount": "HSBC-001",  // 结算账户
    "broker": "Broker A",             // 经纪人
    "time": "10:30:00",               // 交易时间
    "countryOfDomicile": "US",        // 注册地国家
    "issuerCountry": "US",            // 发行人国家
    "issuerRating": "AAA",            // 发行人评级
    "paymentStatus": "未付款",         // 付款状态
    "journalStatus": "未制证",         // 制证状态
    "journalSequence": "",                  // 凭证编号
    "liveFlag"："Y",                  //LIVE标识
    "updateBy": "admin",              // 更新人
    "updateTime": "2024-04-06 10:30:00" // 更新时间
  }
}
```

### 业务逻辑
1. 标识字段显示逻辑：
   - Live：满足以下条件，返回 liveFlag=‘Y’
     - Remark=EFB/CD/UST/Bond，取Trade blotter数据中(ISIN相同，Currency相同)，"Face Amount"数量（Buy/Sell/Borrow/Lend中PAOB Buy - PAOB Sell>0）>0，系统日期<=Maturity Date
     - Remark=Fixed Deposit/interbank，系统日期<=Maturity Date
   - End：不满足Live条件,返回 liveFlag=‘N'

## 3. 上传Trade Blotter文件接口

### 接口描述
上传《All Trade Blotter》文件。

### 请求URL
```
POST /api/trade-blotter/upload
```

### 请求参数
| 参数名 | 类型 | 是否必须 | 说明 |
|--------|------|----------|------|
| file | File | 是 | Excel文件，支持.xlsm格式 |

### 响应结果
```json
{
  "code": 000000,                         // 状态码
  "message": "操作成功",                // 提示信息
  "data": {
      "tradeBlotter": {
        "total": 100,                     //总条数
     	"addCount": 100,                     //新增条数
        "upateCount": 100,                     //更新条数
        "deleteCount": 100,                     //删除条数
      },
      "repo": {
        "total": 100,                     //总条数
     	"addCount": 100,                     //新增条数
        "upateCount": 100,                     //更新条数
        "deleteCount": 100,                     //删除条数
      },
      "fx": {
        "total": 100,                     //总条数
     	"addCount": 100,                     //新增条数
        "upateCount": 100,                     //更新条数
        "deleteCount": 100,                     //删除条数
      },  
      "list":[
          {
            "item": 1001,                      // 项目编号
            "tradeNumber": "T202404060001",   // 交易编号
            "entityId": "ENT001",             // 实体ID
            "counterpart": "Bank A",          // 交易对手方
            "buySellBorrowLend": "PAOB Buy",  // 买卖/借贷标识
            "yield": 3.5,                     // 收益率
            "currency": "USD",                // 币种
            "settlementAmount": 1000000.00,   // 结算金额
            "tradeDate": "2024-04-06",       // 交易日期
            "valueDate": "2024-04-08",       // 起息日
            "maturityDate": "2025-04-06",    // 到期日
            "remark": "Bond",                 // 备注
            "dayCount": 360,                  // 天数
            "interest": 35000.00,             // 利息
            "price": 100.00,                  // 价格
            "debtSecurityName": "US Treasury Bond", // 债务证券名称
            "isin": "US123456AB12",           // ISIN码
            "faceAmount": 1000000.00,         // 面值
            "amountReceivedAtMaturity": 1035000.00, // 到期收到金额
            "interestPaidUpfront": 0.00,      // 预付利息
            "netInterest": 35000.00,          // 净利息
            "couponType": "Fixed",            // 息票类型
            "couponFrequent": "Semi-annual",  // 息票频率
            "channel": "Direct",              // 渠道
            "settlementAccount": "HSBC-001",  // 结算账户
            "broker": "Broker A",             // 经纪人
            "time": "10:30:00",               // 交易时间
            "countryOfDomicile": "US",        // 注册地国家
            "issuerCountry": "US",            // 发行人国家
            "issuerRating": "AAA",            // 发行人评级
            "paymentStatus": "未付款",         // 付款状态
            "journalStatus": "未制证",         // 制证状态
            "journalSequence": "",                  // 凭证编号
            "liveFlag"："Y",                  //LIVE标识
            "updateBy": "admin",              // 更新人
             "updateType":"2",                //1新增2更新3删除
             "updateTypeName":"更新"           
            "updateTime": "2024-04-06 10:30:00" // 更新时间
          }
      ]
     "failRecords": [                   // 失败记录
      {
        "rowNum": 3,                   // Excel行号
        "reason": "交易编号重复"         // 失败原因
      },
      {
        "rowNum": 5,                   // Excel行号
        "reason": "必填字段'交易日期'为空" // 失败原因
      }
    ]
  }
}
```

### 业务逻辑
1. 文件格式校验：
   - 仅支持.xlsm格式
2. 数据校验：
   - 交易编号唯一性校验
   - 日期格式校验：yyyy-MM-dd
   - 数值字段格式校验
3. 导入处理：
   - 读取Trade Blotter，FPS DW Repo，FX 三个sheet页内容
   - 根据上传的交易编号（tradeNumber）与表里交易编号进行比较，1.文件中交易编号在表里不存在，则新增2.表里存在，则更新3.表里交易编号在文件中不存在，则删除
   - 列表也只展示Trade Blotter数据
   - 排序：按照updateType，item 倒序 
   - 失败记录记录行号和失败原因
   - 如果出现程序或网络问题，返回"上传失败，请检查是否网络不稳定原因，并请重试！"
   - 成功导入的记录，设置created_by为当前用户，created_time为当前时间
4. 以上传用户um为key，以Trade Blotter对象为value（Trade Blotter中有个字段来跟其它附件进行关联，32位的uuid），以map形式保存在内存中

## 4.上传其它附件接口

### 接口描述

上传其它附件文件。

### 请求URL

```
POST /api/trade-blotter/uploadOther
```

### 请求参数

| 参数名 | 类型 | 是否必须 | 说明                                                         |
| ------ | ---- | -------- | ------------------------------------------------------------ |
| file   | File | 是       | 限制上传.exe, .bat, .cmd, .sh, .ps1, .js, .vbs, .php, .py, .pl, .jar, .msi, .dll, .scr, .com, .pif, .reg, .apk, .dmg, .iso后缀的文件 |

### 响应结果

```json
{
  "code": 000000,                         // 状态码
  "message": "操作成功",                // 提示信息
  "data": null
  }
}
```

### 业务逻辑

1. 文件格式校验：
   - 限制上传.exe, .bat, .cmd, .sh, .ps1, .js, .vbs, .php, .py, .pl, .jar, .msi, .dll, .scr, .com, .pif, .reg, .apk, .dmg, .iso后缀的文件
2. 导入处理：
   - 判断以key=um的value值是否存在，存在，则获取Trade Blotter中某个字段的uuid，包括附件名，地址，保存在attachment 表
   - 不存在，则以key=um，value =uuid，以map的形式保存在内存中
3. 导入完成后，《All Trade Blotter》文件与其它附件通过attachment 表的business_key字段进行关联了

## 5.确认上传接口

### 接口描述

上传经过确认后的Trade Blotter数据。

### 请求URL

```
GET /api/trade-blotter/confirm
```

### 请求参数

无

### 响应结果

```json
{
  "code": 000000,                         // 状态码
  "message": "操作成功",                // 提示信息
  "data": null
}
```

### 业务逻辑

1. 根据key=um号，获取Trade Blotter对象值，保存在tms_trade_blotter表
2. 导入完成后，付款状态默认未付款，制证状态默认未制作,启动新线程生成公司行为数据（调用后面公司行为数据生成逻辑）
4. 启动新线程生成公司行为数据（调用后面公司行为生成逻辑）

## 6. 司库业务邮件提醒

### 接口描述

1. 对于Trade Blotter 有 新增的Trade Number，生成当天  Trade Blotter-YYYY-MM-DD 文件，发送邮件提醒，邮件内容见需求文档
2. 对于FPS DW Repo有新增的Trade Number，生成当天  Repo-YYYY-MM-DD 文件，发送邮件提醒，邮件内容见需求文档
3. 对于FX 有新增的Trade Number，生成当天  FX-YYYY-MM-DD 文件，发送邮件提醒，邮件内容见需求文档

## 7. 上传risk monitoring文件接口

### 接口描述

风险监控文件上传

### 请求URL

`/api/risk/monitoring/upload`

### 请求方式

`POST`

### 请求参数

```json
{
  "file": "文件对象"  // Excel文件，支持xlx,xlxs,.xlsm格式
}
```

### 参数说明

| 参数名 | 类型 | 必填 | 说明                              |
| ------ | ---- | ---- | --------------------------------- |
| file   | File | 是   | Excel文件，支持xlx,xlxs,.xlsm格式 |

### 处理逻辑

1. 文件格式校验：
   - 仅支持xlx,xlxs,.xlsm格式
2. 数据校验：
   - 日期格式校验：yyyy-MM-dd
   - 数值字段格式校验
3. 导入处理：
   - 无效数据处理：以#N/A或#NAME?开头的数据将当空处理
   - 不论以下两种情况是否存在，都返回前端信息"《risk monitoring》表导入Y条估值信息"，存在以下信息，则添加在此后面
   - 以tms_trade_blotter为主表数据，关联tms_risk_monitoring表，以remark，isin，currency相同，maturity date >= reporting_date >=value date为条件，筛选tms_trade_blotter表有isin，但tms_risk_monitoring表不存在的，返回给前端："与交易数据核对缺少Y1条估值（ISIN1,ISIN2）"
   - tms_risk_monitoring为主表数据，筛选tms_risk_monitoring表有isin，但tms_trade_blotter表不存在的Isin，返回给前端："与交易数据核对有多余Y1条估值（ISIN1,ISIN2）"
   - 列表只展示文件数据
   - 排序：按照文件内容 
   - 失败记录记录行号和失败原因
   - 如果出现程序或网络问题，返回"上传失败，请检查是否网络不稳定原因，并请重试！"
   - 成功导入的记录，设置created_by为当前用户，created_time为当前时间
4. 导入完成后，启动新线程生成计提数据（调用后面计提数据生成逻辑）

### 返回参数

```json
{
  "code": 000000,                         // 状态码
  "message": "操作成功",                // 提示信息
  "data": "《risk monitoring》表导入Y条估值信息,与交易数据核对缺少Y1条估值（ISIN1,ISIN2）"
  }
}
```



## 8. 制证接口

### 接口描述
对选中的Trade Blotter记录进行制证操作。

### 请求URL
```
POST /api/trade-blotter/make-voucher
```

### 请求参数
```json
{
  "tradeNumbers": ["T202404060001", "T202404060002", "T202404060003"]  // 交易编号列表
}
```

### 响应结果
```json
{
  "code": 000000,                         // 状态码
  "message": "已完成X条指令单制证",                // 提示信息
  "data": {
  }
}
```

### 业务逻辑
1. 前端筛选未制证的交易编号，组装成数组的形式传给后端
2. 凭证规则匹配逻辑，通过以下两个条件去匹配凭证规则
   - Trade blotter的remark = 凭证规则的remark
   - Trade blotter的Buy/Sell/Borrow/Lend = 凭证规则的模板类型
3. 制证处理，抽取一个公共生成凭证的方法，处理来自以下三个点
   - 自动制证：定时任务（每日9点）对满足条件的Trade blotter自动制证
     - 条件：TradeDate<=当天且"制证状态"为"未制证"
   - 批量制证：手动触发凭证规则制证
     - 条件："制证状态"为"未制证"或"付/收款状态"值为"未付款"
   - 手工制证：未制证的指令单，点击"会计凭证"进入编辑会计凭证弹窗

## 9. 导入日志

### 1. 查询列表

- URL: /api/attachment/list

- 方法: POST

- 请求参数

  ```json
  {
    "pageNum": 1,
    "pageSize": 10
  }
  ```

- 响应结果

  ```json
  {
    "code": 000000,
    "data": 
      "records":[
              {
                "id": 1,
      		  "list":[
      				 "documentName": "文件名",
                		 "id": "主键",
      			],
                "businessKey":"业务主键"
                "uploadBy": "上传人",
                "uploadTime": "2023-01-01 12:00:00"
              }
    		]
  	}
  }
  ```

  

- 功能: 返回分页的附件列表，按创建时间倒序返回

### 2. 打包文件下载

- URL: /api/attachment/{businessKey}

- 方法: GET

- 处理结果: 多个文件打包成7z格式文件流

### 3.下载文件

- URL: /api/attachment/download/{id}

- 方法: GET

- 处理结果: 单个文件文件流

## 10. 下载列表接口

### 接口描述
导出Trade Blotter列表数据。

### 请求URL
```
GET /api/trade-blotter/export
```

### 请求参数
与列表查询接口的查询参数相同。

### 响应结果
返回Excel文件流。

### 业务逻辑
1. 导出条件：
   - 支持按查询条件筛选导出数据
2. 导出格式：
   - 文件格式：.xlsx
   - 包含列表展示的所有字段
   - 按Item倒序排序
3. 文件命名：
   - 格式：Trade_Blotter_yyyyMMddHHmmss.xlsx

# REPO 交易管理

## 1. 列表查询接口

### 接口描述
查询 REPO 交易列表数据，支持条件筛选和分页查询。

### 请求URL
```
GET /api/repo/list
```

### 请求参数
```json
{
  "tradeNumber": "T202404060001",     // 交易编号，支持模糊搜索
  "currency": "USD",                   // 币种
  "amountMin": 1000000,               // 金额最小值
  "amountMax": 2000000,               // 金额最大值
  "tradeDateStart": "2024-04-01",     // 交易日期开始，格式：yyyy-MM-dd
  "tradeDateEnd": "2024-04-06",       // 交易日期结束，格式：yyyy-MM-dd
  "valueDateStart": "2024-04-01",     // 起息日期开始，格式：yyyy-MM-dd
  "valueDateEnd": "2024-04-06",       // 起息日期结束，格式：yyyy-MM-dd
  "repurchaseDateStart": "2024-04-01", // 回购日期开始，格式：yyyy-MM-dd
  "repurchaseDateEnd": "2024-04-06",   // 回购日期结束，格式：yyyy-MM-dd
  "paymentStatus": "未收款",           // 收款状态，枚举值：未收款、已收款
  "journalStatus": "未制证",           // 制证状态，枚举值：未制证、待复核、复核通过 & 待上传、复核通过 & 上传失败、复核通过 & 上传成功
  "journalSequence": "V202404060001",       // 凭证编号，支持模糊搜索
  "pageNum": 1,                        // 页码，默认1
  "pageSize": 10                       // 每页大小，默认10
}
```

### 响应结果
```json
{
  "code": 000000,                         // 状态码
  "message": "操作成功",                // 提示信息
  "data": {
    "total": 100,                      // 总记录数
    "pageNum": 1,                      // 当前页码
    "pageSize": 10,                    // 每页大小
    "records": [
      {
        "tradeNumber": "T202404060001", // 交易编号
        "counterpart": "Bank A",        // 交易对手方
        "typeOfFacility": 3.5,          // 融资工具类型
        "repoRate": 3.5,                // 回购利率
        "currency": "USD",              // 币种
        "amount": 1000000.00,           // 金额
        "tradeDate": "2024-04-06",     // 交易日期
        "valueDate": "2024-04-08",     // 起息日
        "repurchaseDate": "2024-04-20", // 回购日期
        "collateral": "US Treasury Bond", // 抵押物
        "interest": 35000.00,           // 利息
        "channel": "Direct",            // 渠道
        "time": "10:30:00",             // 交易时间
        "paymentStatus": "未收款",       // 收款状态
        "journalStatus": "未制证",       // 制证状态
        "journalSequence": "",                // 凭证编号
        "updateBy": "admin",            // 更新人
        "updateTime": "2024-04-06 10:30:00" // 更新时间
      }
    ]
  }
}
```

### 业务逻辑
1. 列表以sortId倒序输出
2. 查看公司行为：根据交易编号查询公司行为数据
3. 凭证编号支持点击查看凭证详情，多条记录隔行显示

## 2. 批量制证接口

### 接口描述
对选中的 REPO 交易记录进行批量制证操作。

### 请求URL
```
POST /api/repo/make-voucher
```

### 请求参数
```json
{
  "tradeNumbers": ["T202404060001", "T202404060002", "T202404060003"]  // 交易编号列表
}
```

### 响应结果
```json
{
  "code": 000000,                         // 状态码
  "message": "已完成X条REPO交易制证",                // 提示信息
  "data": {
  }
}
```

### 业务逻辑
1. 前端筛选未制证的交易编号，组装成数组的形式传给后端
2. 凭证规则匹配逻辑，通过以下两个条件去匹配凭证规则
   - Trade blotter的remark = 凭证规则的remark
   - Trade blotter的Buy/Sell/Borrow/Lend = 凭证规则的模板类型
3. 制证处理，抽取一个公共生成凭证的方法，处理来自以下三个点
   - 自动制证：定时任务（每日9点）对满足条件的Trade blotter自动制证
     - 条件：TradeDate<=当天且"制证状态"为"未制证"
   - 批量制证：手动触发凭证规则制证
     - 条件："制证状态"为"未制证"或"付/收款状态"值为"未付款"
   - 手工制证：未制证的指令单，点击"会计凭证"进入编辑会计凭证弹窗

## 3. 下载列表接口

### 接口描述
导出 REPO 交易列表数据。

### 请求URL
```
GET /api/repo/export
```

### 请求参数
与 REPO 列表查询接口的查询参数相同。

### 响应结果
返回 Excel 文件流。

### 业务逻辑
1. 导出条件：
   - 支持按查询条件筛选导出数据
   
   - 文件格式：.xlsx
   - 包含列表展示的所有字段
   - 文件命名：
   
   - 格式：Repo_yyyyMMddHHmmss.xlsx

# FX 交易管理

## 1. 列表查询接口

### 接口描述
查询外汇交易列表数据，支持条件筛选和分页查询。

### 请求URL
```
GET /api/fx/list
```

### 请求参数
```json
{
  "tradeNumber": "T202404060001",     // 交易编号，支持模糊搜索
  "currency": "USD",                   // 币种
  "amountMin": 1000000,               // 金额最小值
  "amountMax": 2000000,               // 金额最大值
  "valueDateStart": "2024-04-01",     // 起息日期开始，格式：yyyy-MM-dd
  "valueDateEnd": "2024-04-06",       // 起息日期结束，格式：yyyy-MM-dd
  "paymentStatus": "未付款",           // 付款状态，枚举值：未付款、付款中、已付款
  "pageNum": 1,                        // 页码，默认1
  "pageSize": 10                       // 每页大小，默认10
}
```

### 响应结果
```json
{
  "code": 000000,                         // 状态码
  "message": "操作成功",                // 提示信息
  "data": {
    "total": 100,                      // 总记录数
    "pageNum": 1,                      // 当前页码
    "pageSize": 10,                    // 每页大小
    "records": [
      {
        "tradeNumber": "T202404060001", // 交易编号
        "counterpart": "Bank A",        // 交易对手方
        "currencyPair": "USD/EUR",      // 货币对
        "valueDate": "2024-04-08",     // 起息日
        "bsCcy1": "Buy",               // 货币1买卖标识
        "ccy1": "USD",                 // 货币1
        "ccy1Amount": 1000000.00,      // 货币1金额
        "bsCcy2": "Sell",              // 货币2买卖标识
        "ccy2": "EUR",                 // 货币2
        "ccy2Amount": 850000.00,       // 货币2金额
        "rate": 0.85,                  // 汇率
        "channel": "Direct",           // 渠道
        "time": "10:30:00",            // 交易时间
        "paymentStatus": "未付款",      // 付款状态
        "journalStatus": "未制证",      // 制证状态
        "journalSequence": "",         // 凭证编号
        "orderId":"111",               //订单号
        "streamNo":"pkdsd232233",       //流水号
        "updateBy": "admin",           // 更新人
        "updateTime": "2024-04-06 10:30:00" // 更新时间
      }
    ]
  }
}
```

### 业务逻辑
1. 列表以sortId倒序输出
2. 操作栏根据条件显示不同按钮：
   - 发起支付：付款状态为"未付款"时显示
   - 查看付款订单：有付款订单时显示

## 2. 下载列表接口

### 接口描述
导出外汇交易列表数据。

### 请求URL
```
GET /api/fx/export
```

### 请求参数
与 FX 列表查询接口的查询参数相同。

### 响应结果
返回 Excel 文件流。

### 业务逻辑
1. 导出条件：
   - 支持按查询条件筛选导出数据导出格式：
   
   - 文件格式：.xlsx
   - 包含列表展示的所有字段
   - 文件命名：
   
   - 格式：FX_yyyyMMddHHmmss.xlsx

# 公司行为

## 1. 公司行为列表查询

### 接口描述
查询公司行为列表，支持多条件筛选和分页。

### 请求URL
`GET /api/corporate-action/list`

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| remark | String | 否 | 公司行为类型，枚举值：Bond、CD、EFB、UST、Interbank、Fixed Deposit、Repo |
| actionType | String | 否 | 到期/利息类型，枚举值：支付利息、支付本金&利息、收取利息、收取本金&利息 |
| tradeNumber | String | 否 | 交易编号，支持模糊搜索 |
| actionNumber | String | 否 | 公司行为编号，支持模糊搜索 |
| valueDateStart | Date | 否 | 起息日开始日期 |
| valueDateEnd | Date | 否 | 起息日结束日期 |
| currency | String | 否 | 币种 |
| amountMin | BigDecimal | 否 | 结算金额最小值 |
| amountMax | BigDecimal | 否 | 结算金额最大值 |
| isin | String | 否 | ISIN代码 |
| paymentStatus | String | 否 | 付/收款状态，枚举值：未付款、付款中、已付款、未收款、已收款 |
| journalStatus | String | 否 | 制证状态，枚举值：未制证、待复核、复核通过&待上传、复核通过&上传失败、复核通过&上传成功 |
| journalSequence | String | 否 | 凭证编号，支持模糊搜索 |
| pageNum | Integer | 是 | 页码，从1开始 |
| pageSize | Integer | 是 | 每页记录数 |

### 响应结果
```json
{
    "code": 0,
    "message": "success",
    "data": {
        "total": 100,
        "pageNum": 1,
        "pageSize": 10,
        "records": [
            {
                "actionNumber": "EFB00001",
                "tradeNumbers": ["TRADE001", "TRADE002"],
                "entityId": "ENTITY001",
                "counterpart": "COUNTERPART001",
                "actionType": "收取利息",
                "currency": "USD",
                "settlementAmount": 100000.00,
                "valueDate": "2024-04-01",
                "remark": "EFB",
                "debtSecurityName": "SECURITY001",
                "isin": "US1234567890",
                "settlementAccount": "ACCOUNT001",
                "broker": "BROKER001",
                "paymentStatus": "未收款",
                "journalStatus": "未制证",
                "journalSequence": []
            }
        ]
    }
}
```

### 处理逻辑
1. 根据查询条件构建查询语句
2. 按 Value Date 倒序、Trade Number 正序排序

## 2. 公司行为列表导出

### 接口描述
导出公司行为列表数据到Excel。

### 请求URL
`GET /api/corporate-action/export`

### 请求参数
同列表查询参数

### 响应结果
Excel文件流，文件名为"公司行为.xlsx"

### 处理逻辑
1. 根据查询条件查询所有符合条件的记录
2. 返回Excel文件流

## 3. 批量制证

### 接口描述
批量处理公司行为的制证。

### 请求URL
`POST /api/corporate-action/batch-voucher`

### 请求参数
```json
{
    "actionNumbers": ["EFB00001", "EFB00002"]
}
```

### 响应结果
```json
{
  "code": 000000,                         // 状态码
  "message": "已完成X条公司行为制证",                // 提示信息
  "data": {
  }
}
```

### 处理逻辑
1. 前端筛选未制证的公司行为编号，组装成数组的形式传给后端
2. 凭证规则匹配逻辑，通过以下两个条件去匹配凭证规则
   - Trade blotter的remark = 凭证规则的remark
   - Trade blotter的Buy/Sell/Borrow/Lend = 凭证规则的模板类型
3. 制证处理，抽取一个公共生成凭证的方法，处理来自以下三个点
   - 自动制证：定时任务（每日9点）对满足条件的Trade blotter自动制证
     - 条件：TradeDate<=当天且"制证状态"为"未制证"
   - 批量制证：手动触发凭证规则制证
     - 条件："制证状态"为"未制证"或"付/收款状态"值为"未付款"
   - 手工制证：未制证的指令单，点击"会计凭证"进入编辑会计凭证弹窗

## 4. 公司行为详情

### 接口描述
查询公司行为详情。

### 请求URL
`GET /api/corporate-action/detail/{actionNumber}`

### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| actionNumber | String | 是 | 公司行为编号 |

### 响应结果
```json
{
    "code": 0,
    "message": "success",
    "data": {
        "actionNumber": "EFB00001",
        "tradeNumbers": ["TRADE001", "TRADE002"],
        "entityId": "ENTITY001",
        "counterpart": "COUNTERPART001",
        "actionType": "收取利息",
        "currency": "USD",
        "settlementAmount": 100000.00,
        "valueDate": "2024-04-01",
        "remark": "EFB",
        "debtSecurityName": "SECURITY001",
        "isin": "US1234567890",
        "settlementAccount": "ACCOUNT001",
        "broker": "BROKER001",
        "paymentStatus": "未收款",
        "voucherStatus": "未制证",
        "voucherNos": [],
        "paymentRecords": [],
        "voucherRecords": []
    }
}
```

### 处理逻辑
1. 根据公司行为编号查询基本信息
2. 查询相关的支付记录
3. 查询相关的凭证记录
4. 返回完整信息

## 5. 公司行为数据生成

### 接口描述
根据业务规则生成公司行为数据。

### 处理逻辑

1. EFB数据生成逻辑：
   - 条件1：`Remark = 'EFB'` 且 `Value Date <= Next coupon date - HC = 当前日期 < Maturity Date` 且 `ISIN`相同，`Currency`相同，`Face Amount`数量（`Buy/Sell/Borrow/Lend`中`PAOB Buy` - `PAOB Sell`>0）
   - 到期/利息  = 收取利息
- SettlementAmount = 计算利息
   - 条件2：`Remark = 'EFB'` 且 当前日期 = `Maturity Date` 且 `ISIN`相同，`Currency`相同，`Face Amount`数量（`Buy/Sell/Borrow/Lend`中`PAOB Buy` - `PAOB Sell`>0）
   - 到期/利息  = 收取本金&利息
   - SettlementAmount = 计算本金+利息
   
2. Bond数据生成逻辑：
   - 条件1：`Remark = 'Bond'` 且 `Value Date <= Next coupon date - HC = 当前日期 < Maturity Date` 且 `ISIN`相同，`Currency`相同，`Face Amount`数量（`Buy/Sell/Borrow/Lend`中`PAOB Buy` - `PAOB Sell`>0）
   - 到期/利息  = 收取利息
- SettlementAmount = 计算利息
   - 条件2：`Remark = 'Bond'` 且 当前日期 = `Maturity Date` 且 `ISIN`相同，`Currency`相同，`Face Amount`数量（`Buy/Sell/Borrow/Lend`中`PAOB Buy` - `PAOB Sell`>0）
   - 到期/利息  = 收取本金&利息
   - SettlementAmount = 计算本金+利息
   
3. CD数据生成逻辑：
   - 条件1：`Remark = 'CD'` 且 `Value Date <= Next coupon date - HC = 当前日期 < Maturity Date` 且 `ISIN`相同，`Currency`相同，`Face Amount`数量（`Buy/Sell/Borrow/Lend`中`PAOB Buy` - `PAOB Sell`>0）
   - 到期/利息  = 收取利息
- SettlementAmount = 计算利息
   - 条件2：`Remark = 'CD'` 且 当前日期 = `Maturity Date` 且 `ISIN`相同，`Currency`相同，`Face Amount`数量（`Buy/Sell/Borrow/Lend`中`PAOB Buy` - `PAOB Sell`>0）
   - 到期/利息  = 收取本金&利息
   - SettlementAmount = 计算本金+利息
   
4. UST数据生成逻辑：
   - 条件1：`Remark = 'UST'` 且 `Value Date <= Next coupon date - HC = 当前日期 < Maturity Date` 且 `ISIN`相同，`Currency`相同，`Face Amount`数量（`Buy/Sell/Borrow/Lend`中`PAOB Buy` - `PAOB Sell`>0）
   - 到期/利息  = 收取利息
- SettlementAmount = 计算利息
   - 条件2：`Remark = 'UST'` 且 当前日期 = `Maturity Date` 且 `ISIN`相同，`Currency`相同，`Face Amount`数量（`Buy/Sell/Borrow/Lend`中`PAOB Buy` - `PAOB Sell`>0）
   - 到期/利息  = 收取本金&利息
   - SettlementAmount = 计算本金+利息
   
5. Fixed Deposit数据生成逻辑：
   - 条件1：`Remark = 'Fixed Deposit'` 且 `Value Date <= Next coupon date - HC = 当前日期 < Maturity Date` 且 `Buy/Sell/Borrow/Lend` = 'PAOB Lend'
   - 到期/利息  = 收取利息
- SettlementAmount = 计算利息
   - 条件2：`Remark = 'Fixed Deposit'` 且 当前日期 = `Maturity Date` 且 `Buy/Sell/Borrow/Lend` = 'PAOB Lend'
   - 到期/利息  = 收取本金&利息
   - SettlementAmount = 计算本金+利息
   
6. Interbank数据生成逻辑：
   - 条件1：`Remark = 'Interbank'` 且 `Value Date <= Next coupon date - HC = 当前日期 < Maturity Date` 且 `Buy/Sell/Borrow/Lend` = 'PAOB Lend'
   - 到期/利息  = 收取利息
   - SettlementAmount = 计算利息
   - 条件2：`Remark = 'Interbank'` 且 当前日期 = `Maturity Date` 且 `Buy/Sell/Borrow/Lend` = 'PAOB Lend'
- 到期/利息  = 收取本金&利息
   - SettlementAmount = 计算本金+利息
   - 条件3：`Remark = 'Interbank'` 且 `Value Date <= Next coupon date - HC = 当前日期 < Maturity Date` 且 `Buy/Sell/Borrow/Lend` = 'PAOB Borrow'
   - 到期/利息  = 支付利息
   - SettlementAmount = 计算利息
   - 条件4：`Remark = 'Fixed Deposit'` 且 当前日期 = `Maturity Date` 且 `Buy/Sell/Borrow/Lend` = 'PAOB Borrow'
   - 到期/利息  = 收取本金&利息
   - SettlementAmount = 支付本金+利息
   
7. Repo数据生成逻辑：
   - 条件：`Remark = 'Repo'` 且 `Repurchase Date` = 当前日期
   - 到期/利息  = 支付本金&利息
   - SettlementAmount = 计算本金+利息

**利息计算逻辑**：
1. `Remark` = 'EFB'/'Bond'/'CD'/'UST'：
   - 计算（`Buy/Sell/Borrow/Lend`中`PAOB Buy`取`Next interest`汇总，减去`Buy/Sell/Borrow/Lend`中`PAOB Sell`取`Next interest`汇总）

2. `Remark` = 'Fixed Deposit'/'Interbank'：
   - 取`Next interest`

**本金&利息计算逻辑**：
1. `Remark` = 'EFB'/'Bond'/'CD'/'UST'：
   - （`Buy/Sell/Borrow/Lend`中`PAOB Buy`取`Face Amount` + `Next interest`汇总，减去`Buy/Sell/Borrow/Lend`中`PAOB Sell`取`Face Amount` + `Next interest`汇总）
   
2. `Remark` = 'Fixed Deposit'/'Interbank'：
   - 取`Amount received at maturity` - `已计提利息`
   - 使用`Trade Number`关联【计提数据】取得`已计提利息`
   
3. `Remark` = 'Repo'：
   - 取`Amount` + 'Interest'

# 计提数据

## 1. 计提数据列表查询

### 接口描述
查询计提数据列表。

### 请求URL
`GET /api/accrual/list`

### 请求参数
```json
{
    "remark": "Bond",  // 备注类型：Bond、CD、EFB、UST、Interbank、Fixed Deposit
    "tradeNumber": "TRADE001",  // 交易编号，支持模糊搜索
    "dateStart": "2024-04-01",  // 日期范围开始
    "dateEnd": "2024-04-30",  // 日期范围结束
    "currency": "USD",  // 币种
    "finalPriceMin": 1000,  // 最终价格最小值
    "finalPriceMax": 2000,  // 最终价格最大值
    "isin": "US1234567890",  // ISIN代码
    "interestJournalStatus": "未制证",  // 计提利息制证状态
    "valuationJournalStatus": "未制证",  // 估值变动制证状态
    "journalNo": "J202404010001",  // 凭证编号，支持模糊搜索
    "pageNum": 1,  // 页码
    "pageSize": 10  // 每页大小
}
```

### 响应结果
```json
{
    "code": 0,
    "message": "success",
    "data": {
        "total": 100,
        "pageNum": 1,
        "pageSize": 10,
        "records": [
            {
                "date": "2024-04-01",
                "remark": "Bond",
                "tradeNumber": "TRADE001",
                "entityId": "ENTITY001",
                "counterpart": "COUNTERPART001",
                "currency": "USD",
                "price": 1000.00,
                "sumOfHkdAmount": 7800.00,
                "sumOfNotionalInBaseCurrency": 1000.00,
                "finalPrice": 1000.00,
                "debtSecurityName": "BOND001",
                "isin": "US1234567890",
                "dailyAccruedInterest": 10.00,
                "accruedInterest": 100.00,
                "interestJournalStatus": "未制证",
                "interestJournalNo": "J202404010001",
                "dailyValuationChange": 20.00,
                "accruedValuationChange": 200.00,
                "valuationJournalStatus": "未制证",
                "valuationJournalNo": "J202404010002"
            }
        ]
    }
}
```

### 处理逻辑
1. 按日期倒序、Trade Number正序排序
2. 支持多条件组合查询
3. 支持分页查询

## 2. 计提数据导出

### 接口描述
导出计提数据列表。

### 请求URL
`GET /api/accrual/export`

### 请求参数
同列表查询参数

### 响应结果
Excel文件流

### 处理逻辑
1. 导出文件名为"计提数据.xlsx"
2. 导出内容与列表展示内容相同
3. 按日期倒序、Trade Number正序排序

## 3. 批量计提利息制证

### 接口描述
批量生成计提利息凭证。

### 请求URL
`POST /api/accrual/batch-interest-journal`

### 请求参数
```json
{
    "tradeNumbers": ["TRADE001", "TRADE002"]  // 交易编号列表
}
```

### 响应结果
```json
{
  "code": 000000,                         // 状态码
  "message": "已完成X条计提数据制证",                // 提示信息
  "data": {
  }
}
```

### 处理逻辑

1. 前端筛选未制证的公司行为编号，组装成数组的形式传给后端
2. 凭证规则匹配逻辑，通过以下两个条件去匹配凭证规则
   - Trade blotter的remark = 凭证规则的remark
   - Trade blotter的Buy/Sell/Borrow/Lend = 凭证规则的模板类型
3. 制证处理，抽取一个公共生成凭证的方法，处理来自以下三个点
   - 自动制证：定时任务（每日9点）对满足条件的Trade blotter自动制证
     - 条件：TradeDate<=当天且"制证状态"为"未制证"
   - 批量制证：手动触发凭证规则制证
     - 条件："制证状态"为"未制证"或"付/收款状态"值为"未付款"
   - 手工制证：未制证的指令单，点击"会计凭证"进入编辑会计凭证弹窗

## 4. 计提数据生成

### 接口描述
生成计提数据。

### 处理逻辑

- 未制证的计提数据，支持重新生成。
- 根据 Trade blotter 生成；并关联《risk monitoring》表数据。
- 当日应计利息生成逻辑：从 Value Date 开始，到 Maturity Date - 1 结束，期间天数算为 X，每日计提 "Interest"/X，轧差放最后一天

1. EFB数据生成逻辑：
   - 条件：`Remark = 'EFB'` 且 `Value Date <= 当前日期 <= Maturity Date` 且 `ISIN`相同，`Currency`相同
   - 计算当日应计利息：从Value Date开始，到Maturity Date - 1结束，期间天数算为X，每日计提"Interest"/X，轧差放最后一天

2. Bond数据生成逻辑：
   - 条件：`Remark = 'Bond'` 且 `Value Date <= 当前日期 <= Maturity Date` 且 `ISIN`相同，`Currency`相同
   - 计算当日应计利息：从Value Date开始，到Maturity Date - 1结束，期间天数算为X，每日计提"Interest"/X，轧差放最后一天

3. CD数据生成逻辑：
   - 条件：`Remark = 'CD'` 且 `Value Date <= 当前日期 <= Maturity Date` 且 `ISIN`相同，`Currency`相同
   - 计算当日应计利息：从Value Date开始，到Maturity Date - 1结束，期间天数算为X，每日计提"Interest"/X，轧差放最后一天

4. UST数据生成逻辑：
   - 条件：`Remark = 'UST'` 且 `Value Date <= 当前日期 <= Maturity Date` 且 `ISIN`相同，`Currency`相同
   - 计算当日应计利息：从Value Date开始，到Maturity Date - 1结束，期间天数算为X，每日计提"Interest"/X，轧差放最后一天

5. Interbank数据生成逻辑：
   - 条件：`Remark = 'Interbank'` 且 `Value Date <= 当前日期 <= Maturity Date`
   - 计算当日应计利息：从Value Date开始，到Maturity Date - 1结束，期间天数算为X，每日计提"Interest"/X，轧差放最后一天

6. Fixed Deposit数据生成逻辑：
   - 条件：`Remark = 'Fixed Deposit'` 且 `Value Date <= 当前日期 <= Maturity Date`
   - 计算当日应计利息：从Value Date开始，到Maturity Date - 1结束，期间天数算为X，每日计提"Interest"/X，轧差放最后一天

