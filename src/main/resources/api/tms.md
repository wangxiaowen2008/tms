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
        "payFlag":"Y"            //是否发起支付标识
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

   - 发起支付：满足以下条件 payFlag=‘Y'

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
   - Live：满足以下条件，返回 liveFlag=‘Y'
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
      "businessKey":"3f23323232fsdf23232refdsf23232"
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
      "succRecords":[
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
   - 根据上传的交易编号（tradeNumber）与表里交易编号进行比较，1.文件中交易编号在表里不存在，则新增2.表里存在，则更新（A-AA列存在变动才更新）3.表里交易编号在文件中不存在，则删除
   - N/A开头的默认当空处理
   - 列表也只展示Trade Blotter数据
   - 排序：按照updateType，item 倒序 
   - 失败记录记录行号和失败原因
   - 如果出现程序或网络问题，返回"上传失败，请检查是否网络不稳定原因，并请重试！"
   - 成功导入的记录，设置created_by为当前用户，created_time为当前时间
4. 以上传用户um为key，以businessKey值为value（Trade Blotter中有个字段来跟其它附件进行关联，32位的uuid），以map形式保存在内存中

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
```
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

## 11. ISIN查询接口

### 接口描述
查询所有ISIN数据。

### 请求URL
```
GET /api/trade-blotter/queryIsin
```

### 响应结果
```json
  {
    "code": 000000,
    "data": 
      "records":[
              "XS20211","XS2033","XS20111"
    		]
  	}
  }
```

### 业务逻辑
1. 筛选条件：
   - 从表tms_trade_blotter查询isin字段，筛选 isin 不为空，去重重复。


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
| remark | String | 否 | 公司行为类型，枚举值：Bond-债券、CD-存单、EFB-欧洲金融债券、UST-美国国债、Interbank-银行间、Fixed Deposit-定期存款、Repo-回购 |
| actionType | String | 否 | 到期/利息类型，枚举值：支付利息、支付本金&利息、收取利息、收取本金&利息 |
| tradeNumber | String | 否 | 交易编号，支持模糊搜索 |
| actionNumber | String | 否 | 公司行为编号，支持模糊搜索，格式：EFB+5位序号，如EFB00001 |
| valueDateStart | Date | 否 | 起息日开始日期，格式：yyyy-MM-dd |
| valueDateEnd | Date | 否 | 起息日结束日期，格式：yyyy-MM-dd |
| currency | String | 否 | 币种，如USD、HKD等 |
| amountMin | BigDecimal | 否 | 结算金额最小值 |
| amountMax | BigDecimal | 否 | 结算金额最大值 |
| isin | String | 否 | ISIN代码，国际证券识别编码 |
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
        "total": 100,  // 总记录数
        "pageNum": 1,  // 当前页码
        "pageSize": 10,  // 每页大小
        "records": [
            {
                "actionNo": "EFB00001",  // 公司行为编号
                "tradeNumbers": ["TRADE001", "TRADE002"],  // 关联的交易编号列表
                "entityId": "ENTITY001",  // 实体ID
                "counterpart": "COUNTERPART001",  // 交易对手方
                "actionType": "收取利息",  // 公司行为类型
                "currency": "USD",  // 币种
                "settlementAmount": 100000.00,  // 结算金额
                "valueDate": "2024-04-01",  // 起息日
                "remark": "EFB",  // 备注类型
                "debtSecurityName": "SECURITY001",  // 债务证券名称
                "isin": "US1234567890",  // ISIN代码
                "settlementAccount": "ACCOUNT001",  // 结算账户
                "broker": "BROKER001",  // 经纪商
                "paymentStatus": "未收款",  // 付款状态
                "journalStatus": "未制证",  // 制证状态
                "journalSequence": ""  // 凭证编号
            }
        ]
    }
}
```

## 2. 公司行为列表导出

### 接口描述
导出公司行为列表数据到Excel。

### 请求URL
`GET /api/corporate-action/export`

### 请求参数
同列表查询参数

### 响应结果
Excel文件流，文件名为"公司行为_yyyyMMddHHmmss.xlsx"

## 3. 批量制证

### 接口描述
批量处理公司行为的制证。

### 请求URL
`POST /api/corporate-action/batch-voucher`

### 请求参数
```json
{
    "actionNo": ["EFB00001", "EFB00002"]  // 公司行为编号列表
}
```

### 响应结果
```json
{
    "code": 000000,  // 响应码，000000表示成功
    "message": "已完成X条公司行为制证",  // 响应消息
    "data": {
    }
}
```

## 4. 公司行为详情

### 接口描述
查询公司行为详情。

### 请求URL
`GET /api/corporate-action/detail/{actionNo}`

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
        "actionNo": "EFB00001",  // 公司行为编号
        "tradeNumbers": ["TRADE001", "TRADE002"],  // 关联的交易编号列表
        "entityId": "ENTITY001",  // 实体ID
        "counterpart": "COUNTERPART001",  // 交易对手方
        "actionType": "收取利息",  // 公司行为类型
        "currency": "USD",  // 币种
        "settlementAmount": 100000.00,  // 结算金额
        "valueDate": "2024-04-01",  // 起息日
        "remark": "EFB",  // 备注类型
        "debtSecurityName": "SECURITY001",  // 债务证券名称
        "isin": "US1234567890",  // ISIN代码
        "settlementAccount": "ACCOUNT001",  // 结算账户
        "broker": "BROKER001",  // 经纪商
        "paymentStatus": "未收款",  // 付款状态
        "journalStatus": "未制证",  // 制证状态
        "journalSequence": "",  // 凭证编号
        "orderId": ""  // 订单号
    }
}
```

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

```
## 按照remark=EFB'/'Bond'/'CD'/'UST'/'Fixed Deposit'/'Interbank'/'Repo'分别生成公司行为数据，生成逻辑如下：  
### 当 remark="EFB" 
   1.查询tms_trade_blotter表字段，以下是筛选条件
   Value Date <= Next coupon date - HC，Next coupon date - HC = 当前日期，Next coupon date - HC < Maturity Date
   2.返回的结果再以多个tradeNumber为条件，且 buy_sell_borrow_lend取值等于PAOB Buy - buy_sell_borrow_lend取值等于PAOB Sell > 0
   3.查询出来的数据保存在List<TradeBlotter>对象中，对list对象按照currency,isin进行分组，保存在CorporateAction对象，值定义如下：
	   1. actionNo="EFB"+5位自增长数字（先从表里tms_corporate_action获取action_no数字部分最大值，按remark="EFB" 筛选，保存在一个数字变量中，如果存在值，则自增长一位，否则默认00000）
	   2. tradeNumbers存在多个不重复值，以逗号分开
	   3. entityId存在多个不重复值，以逗号分开
	   4. counterpart存在多个不重复值，以逗号分开
	   5. valueDate存在多个不重复值，任取一个
	   6. debtSecurityName存在多个不重复值，以逗号分开
	   7. settlementAccount存在多个不重复值，以逗号分开
	   8. broker存在多个不重复值，以逗号分开
	   9. paymentStatus=0
	   10.journalStatus=0
	   11.actionType=收取利息
	   12.settlementAmount=sum(buySellBorrowLend ='PAOB Buy' 取netInterest值 - buySellBorrowLend ='PAOB Sell' 取netInterest值)
### 当 remark="REPO" 
      1.查询tms_fps_dw_repo表字段，筛选条件：repurchase_date = 当前日期
   2.查询出来的数据保存在List<FpsDwRepo>对象中，循环list，保存在CorporateAction对象，值定义如下：
	   1. actionNo="REPO"+5位自增长数字（先从表里tms_corporate_action获取action_no数字部分最大值，按remark="EFB" 筛选，保存在一个数字变量中，如果存在值，则自增长一位，否则默认00000）
	   2. tradeNumbers=tradeNumber
	   3. entityId=null
	   4. counterpart=counterpart
	   5. valueDate=当前日期
	   6. debtSecurityName=null
	   7. settlementAccount=
	   8. broker=null
	   9. paymentStatus=0
	   10.journalStatus=0
	   11.actionType=收取本金&利息
### 批量保存到tms_corporate_action表
```



# 计提数据

## 1. 计提数据列表查询

### 接口描述
查询计提数据列表。

### 请求URL
`GET /api/accrual/list`

### 请求参数
```json
{
    "remark": "Bond",  // 备注类型：Bond-债券、CD-存单、EFB-欧洲金融债券、UST-美国国债、Interbank-银行间、Fixed Deposit-定期存款
    "tradeNumber": "TRADE001",  // 交易编号，支持模糊搜索
    "dateStart": "2024-04-01",  // 日期范围开始，格式：yyyy-MM-dd
    "dateEnd": "2024-04-30",  // 日期范围结束，格式：yyyy-MM-dd
    "currency": "USD",  // 币种，如USD、HKD等
    "marketValueMin": 1000,  // 市场价值最小值
    "marketValueMax": 2000,  // 市场价值最大值
    "isin": "US1234567890",  // ISIN代码，国际证券识别编码
    "interestJournalStatus": "未制证",  // 计提利息制证状态，枚举值：未制证、待复核、复核通过&待上传、复核通过&上传失败、复核通过&上传成功
    "valuationJournalStatus": "未制证",  // 估值变动制证状态，枚举值：未制证、待复核、复核通过&待上传、复核通过&上传失败、复核通过&上传成功
    "allocPremDiscJournalStatus ": "未制证",  // 分摊溢折价制证状态，枚举值：未制证、待复核、复核通过&待上传、复核通过&上传失败、复核通过&上传成功
    "journalSequence": "J202404010001",  // 凭证编号，支持模糊搜索
    "pageNum": 1,  // 页码，从1开始
    "pageSize": 10  // 每页大小
}
```

### 响应结果
```json
{
    "code": 0,
    "message": "success",
    "data": {
        "total": 100,  // 总记录数
        "pageNum": 1,  // 当前页码
        "pageSize": 10,  // 每页大小
        "records": [
            {
                "date": "2024-04-01",  // 日期
                "remark": "Bond",  // 备注类型
                "tradeNumber": "TRADE001",  // 交易编号
                "entityId": "ENTITY001",  // 实体ID
                "counterpart": "COUNTERPART001",  // 交易对手方
                "currency": "USD",  // 币种
                "price": 1000.00,  // 价格
                "sumOfHkdAmount": 7800.00,  // HKD金额汇总
                "sumOfNotionalInBaseCurrency": 1000.00,  // 基础货币面值汇总
                "marketValue": 1000.00,  // 市场价值
                "debtSecurityName": "BOND001",  // 债务证券名称
                "isin": "US1234567890",  // ISIN代码
                "dailyAccruedInterest": 10.00,  // 当日应计利息
                "accruedInterest": 100.00,  // 已计提利息
                "interestJournalStatus": "未制证",  // 计提利息制证状态
                "interestJournalNo": "J202404010001",  // 计提利息凭证编号
                "dailyValuationChange": 20.00,  // 当日估值变动
                "accruedValuationChange": 200.00,  // 已计提估值变动
                "valuationJournalStatus": "未制证",  // 估值变动制证状态
                "valuationJournalNo": "J202404010002",  // 估值变动凭证编号
                "allocPremDiscJournalStatus": "未制证",  // 分摊溢折价制证状态
                "allocPremDiscJournalNo": "J202404010003",  // 分摊溢折价凭证编号
                "dailyAllocPremDisc": 30.00,  // 当日分摊溢折价
                "allocPremDisc": 300.00  // 已分摊溢折价
            }
        ]
    }
}
```

## 2. 计提数据导出

### 接口描述
导出计提数据列表。

### 请求URL
`GET /api/accrual/export`

### 请求参数
同列表查询参数

### 响应结果
Excel文件流，文件名为"accrual_yyyyMMddHHmmss.xlsx"

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
    "code": 000000,  // 响应码，000000表示成功
    "message": "已完成X条计提数据制证",  // 响应消息
    "data": {
    }
}
```

## 4. 计提数据生成

### 接口描述
生成计提数据。

### 处理逻辑


## 按照remark=EFB'/'Bond'/'CD'/'UST'/'Fixed Deposit'/'Interbank'/'Repo'分别生成公司行为数据，生成逻辑如下：  
### 当 remark="EFB"



  1.查询tms_trade_blotter表字段，以下是筛选条件
   buy_sell_borrow_loan字段值为"BOND Buy"和"BOND Sell"类型的交易， `Value Date <= 当前日期 < Maturity Date`
   2.返回的结果再以多个tradeNumber为条件，且 buy_sell_borrow_lend取值等于PAOB Buy - buy_sell_borrow_lend取值等于PAOB Sell > 0
   3.查询出来的数据保存在List<TradeBlotter>对象中，对list对象按照currency,isin进行分组，保存在Accrual对象，值定义如下：

     1.  remark = remark，取第一个
       2. tradeNumbers存在多个不重复值，以逗号分开
       3. entityId = entityId，取第一个
       4. counterpart = counterpart，取第一个
       5. currency = currency，取第一个
       6. price = sum(settlementAmount)/sum(faceAmount)
       7. sumOfHkdAmount = sum(hkdAmount),买入 - 卖出
       8. sumOfNotionalInBaseCurrency = sum(faceAmount),买入 - 卖出
       9. debtSecurityName = debtSecurityName
               10. isin = isin
             11.interestJournalStatus = 0
             12.allocPremDiscJournalStatus = 0
               13.valuationJournalStatus = 0
               14. accrualDate = 当前时间

 ### 根据remark，currency，isin，accrualDate 判断表里是否有值，有值则更新，否则插入 
     批量保存到tms_accrual表   

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

以 UFXBond/CUSTBOND 为例的计算逻辑
计算当日应计利息和已计利息
数据筛选：从trade_broker表获取buy_sell_borrow_loan字段值为"BOND Buy"和"BOND Sell"类型的交易，且需满足当前日期>= value_date 以及当前日期< maturity_date,
查询结果按ISIN、currency相同进行分组汇总,分别对buy_sell_borrow_lend为'PAOB Buy'汇总face_amount的值，以及'PAOB Sell'汇总face_amount的值 ，随后两值相减取值netFaceAmount大于0，
才计算下面应计利息，分摊溢折价，估值变动

每日应计利息计算：按照buy_sell_borrow_lend分别为'PAOB Buy'汇总interest的值，以及'PAOB Sell'汇总interest的值，计算公式为：
dailyAccruedInterest = (汇总的买(interest) - 汇总的卖(interest)) / (maturity_date - value_date)

已计利息计算：
已计利息 = 上一天已计利息 + 当日计利息
若netFaceAmount > 0 ，则 accruedInterest = dailyAccruedInterest + 上一天已计利息
若netFaceAmount < 0 ，则 accruedInterest = dailyAccruedInterest + 上一天已计利息 *（类型为'BOND Sell'的face_amount / 类型为'BOND Buy'的face_amount）

计算当日分摊溢价、已分摊溢价、未分摊溢价

每日分摊溢价计算：从value_date开始计算,按buy_sell_borrow_loan为"BOND Buy"汇总sum(settlement_amount + face_amount) + sum(face_amount*price/100)的值 ，以及buy_sell_borrow_loan为"BOND Sell"汇总sum(settlement_amount + face_amount) + sum(face_amount*price/100)的值，相减得到netAllocPremDisc,除以maturity_date - value_date得到 dailyAllocPremDisc。

已分摊溢价计算：
已分摊溢价 = 上一天已分摊溢价 + 当日分摊溢价
若netFaceAmount > 0 ，则 allocPremDisc = dailyAllocPremDisc + 上一天已分摊溢价
若netFaceAmount < 0 ，则 allocPremDisc = dailyAllocPremDisc + 上一天已分摊溢价 *（类型为"BOND Sell"的face_amount / 类型为"BOND Buy"的face_amount）

未分摊溢价计算：未分摊溢价 = 总溢价 - 已分摊溢价，即netAllocPremDisc - allocPremDisc

计算当日估值变动和已计估值变动

当日估值计算：
如果当日 = value_date，根据cm_trade_broker表，按ISIN、currency进行分组汇总，分别汇总buy_sell_borrow_loan为"BOND Buy"时的Settlement_amount值和"BOND Sell"时的Settlement_amount值，相减得到netSettlementAmount；根据cm_risk_monitoring表，按ISIN、currency进行分组汇总，算出Market_value的汇总值等于sumMarketValue 。
当日估值 dailyValuationChange = sumMarketValue - netSettlementAmount
 如果当日 > value_date,则 dailyValuationChange = sumMarketValue - 上一日的sumMarketValue

已计估值计算：
若netFaceAmount > 0 ，则 accruedValuationChange = 上一日的计提估值 + dailyValuationChange
若netFaceAmount < 0 ，则 accruedValuationChange = 上一日的计提估值 * （类型为"BOND Sell"的face_amount / 类型为"BOND Buy"的face_amount） + dailyValuationChange

以 Fixed Deposit/Interbank 债券为例的计算逻辑
计算当日应计利息和已计利息
数据筛选：从trade_broker表获取buy_sell_borrow_loan字段值为"BOND Lend"类型的交易，且需满足当前日期 >= value_date 以及当前日期< maturity_date 。
每日应计利息计算：dailyAccruedInterest = sum(Interest) / (maturity_date - value_date) 
已计利息计算：accruedInterest = 上一天已计利息 + dailyAccruedInterest

  


# 凭证模板

## 1. 凭证模板列表查询

**接口描述**：查询凭证模板列表

**请求URL**：`GET /api/journal-template/list`

**请求参数**：
```json
{
    "remark": "Bond",  // 备注类型：Bond-债券、CD-存单、EFB-欧洲金融债券、UST-美国国债、Interbank-银行间、Fixed Deposit-定期存款、Repo-回购、FX-外汇
    "tempType": "PAOB Buy",  // 模板类型：PAOB Buy-PAOB买入、PAOB Sell-PAOB卖出、PAOB Borrow-PAOB借入、PAOB Lend-PAOB借出、支付利息、支付本金&利息、收取本金、收取本金&利息、Repo-回购、FX-外汇、计提利息、计提估值变动
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
                            "accountedCr": 0,  // 账户贷方金额
                            "accountedDr": 0,  // 账户借方金额
                            "booksNo": "JT_SOB",  // 账套编号
                            "citJournalTempId": "329C168E3EA2A98BE063B739210A81",  // 凭证模板ID
                            "createdBy": "INVESTOP",  // 创建人
                            "createdDate": 1744523603000,  // 创建时间
                            "dataState": "0",  // 数据状态
                            "direction": "D",  // 方向：D-贷方，G-借方
                            "effectiveDate": 1744523603000,  // 生效日期
                            "enteredCr": 0,  // 贷方金额
                            "enteredDr": 0,  // 借方金额
                            "journalLineDescription": "111",  // 分录描述
                            "payDirection": "B",  // 支付方向
                            "pEnteredCr": 0,  // 报告币贷方金额
                            "pEnteredDr": 0,  // 报告币借方金额
                            "segment1": "000105 合并调整_保险业（HFM）",  // 公司段
                            "segment2": "0000 缺省",  // 业务段
                            "segment3": "0000 缺省",  // 成本中心
                            "segment4": "000000 缺省",  // 产品段
                            "segment5": "100101 现金1（pos机）",  // 科目
                            "segment6": "000000 缺省",  // 子目
                            "segment7": "000000",  // 备用段1
                            "segment8": "0000 缺省",  // 备用段2（关联方）
                            "streamType": "银行借款利息",  // 流水类型
                            "tempCategory": "4",  // 模板类别
                            "tempName": "rteswt23223",  // 模板名称
                            "tempNo": 4271,  // 模板编号
                            "updatedBy": "INVESTOP",  // 更新人
                            "updatedDate": 1744523603000  // 更新时间
                        }
                ],
                 "remark": "Bond",  // 备注类型
                "tempType": "PAOB Buy",  // 模板类型
                 "streamType": "付款",  // 流水类型
                 "tempName": "rteswt23223",  // 模板名称
                 "updatedBy": "INVESTOP",  // 更新人
                 "updatedDate": 1744523603000  // 更新时间
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
            "booksNo": "HK_SOB",  // 账套编号
            "remark": "Bond",  // 备注类型
   			    "tempType": "PAOB Buy",  // 模板类型
    		    "streamType": "付款",  // 流水类型
            "direction": "G",  // 方向：G-借方，D-贷方
            "journalLineDescription": "",  // 分录描述
            "segment1": "800000",  // 公司段
            "segment1Type": "",  // 公司段类型
            "segment2": "0000",  // 业务段
            "segment3": "0000",  // 成本中心
            "segment4": "000000",  // 产品段
            "segment5": "1001000000",  // 科目
            "segment6": "000000",  // 子目
            "segment8": "0000",  // 备用段2（关联方）
            "tempName": "test",  // 模板名称
            "effectiveDate": "2024-04-19",  // 制证日期
            "curNo": "USD",  // 币种
            "enteredDr": "0.00",  // 借方金额
            "enteredCr": "0.00",  // 贷方金额
            "accountedDr": "0.00",  // 帐户借方金额
            "accountedCr": "0.00"  // 帐户贷方金额
            "groupFlag" : 0 //凭证分组
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
2. 保存凭证模板信息
3. 保存凭证分录信息
4. 设置初始状态为"初始状态"

## 3. 编辑凭证模板

**接口描述**：获取凭证模板

**请求URL**：`Get /api/journal-template/{tempName}`

**响应结果**：

```json
{
    "code": "000000",
    "data": [
        {
            "booksNo": "JT_SOB",  // 账套编号
             "remark": "Bond",  // 备注类型
   			    "tempType": "PAOB Buy",  // 模板类型
    		    "streamType": "付款",  // 流水类型
            "payDirection": "B",  // 支付方向
            "segment1": "800000",  // 公司段
            "subList": [
                {
                   "accountedCr": 0,  // 账户贷方金额
                    "accountedDr": 0,  // 账户借方金额
                    "booksNo": "JT_SOB",  // 账套编号
                    "citJournalTempId": "329C168E3EA2A98BE063B739210A81",  // 凭证模板ID
                    "createdBy": "INVESTOP",  // 创建人
                    "createdDate": 1744523603000,  // 创建时间
                    "dataState": "0",  // 数据状态
                    "direction": "D",  // 方向：D-贷方，G-借方
                    "effectiveDate": 1744523603000,  // 生效日期
                    "enteredCr": 0,  // 贷方金额
                    "enteredDr": 0,  // 借方金额
                    "journalLineDescription": "111",  // 分录描述
                    "payDirection": "B",  // 支付方向
                    "pEnteredCr": 0,  // 报告币贷方金额
                    "pEnteredDr": 0,  // 报告币借方金额
                    "segment1": "000105 合并调整_保险业（HFM）",  // 公司段
                    "segment2": "0000 缺省",  // 业务段
                    "segment3": "0000 缺省",  // 成本中心
                    "segment4": "000000 缺省",  // 产品段
                    "segment5": "100101 现金1（pos机）",  // 科目
                    "segment6": "000000 缺省",  // 子目
                    "segment7": "000000",  // 备用段1
                    "segment8": "0000 缺省",  // 备用段2（关联方）
                    "streamType": "银行借款利息",  // 流水类型
                    "tempCategory": "4",  // 模板类别
                    "tempName": "rteswt23223",  // 模板名称
                    "tempNo": 4271,  // 模板编号
                    "updatedBy": "INVESTOP",  // 更新人
                    "updatedDate": 1744523603000  // 更新时间
                }
            ],
            "tempName": "ddd"  // 模板名称
        }
    ],
    "message": "成功"
}
```

**处理逻辑**：

**接口描述**：更新凭证模板

**请求URL**：`POST /api/journal-template/update

**请求参数**：

```json
{
    "JournalTempList": [
        {
            "booksNo": "HK_SOB",  // 账套编号
            "remark": "Bond",  // 备注类型
   			    "tempType": "PAOB Buy",  // 模板类型
    		    "streamType": "付款",  // 流水类型
            "direction": "G",  // 方向：G-借方，D-贷方
            "journalLineDescription": "",  // 分录描述
            "segment1": "800000",  // 公司段
            "segment1Type": "",  // 公司段类型
            "segment2": "0000",  // 业务段
            "segment3": "0000",  // 成本中心
            "segment4": "000000",  // 产品段
            "segment5": "1001000000",  // 科目
            "segment6": "000000",  // 子目
            "segment8": "0000",  // 备用段2（关联方）
            "tempName": "test",  // 模板名称
            "effectiveDate": "2024-04-19",  // 制证日期
            "curNo": "USD",  // 币种
            "enteredDr": "0.00",  // 借方金额
            "enteredCr": "0.00",  // 贷方金额
            "accountedDr": "0.00",  // 帐户借方金额
            "accountedCr": "0.00"  // 帐户贷方金额
            "groupFlag" : 0 //凭证分组
            "tempNo" : 10001 //模板编号
            "tmsCitJournalTempId" : "329C168E3EA2A98BE063B739210A81" //凭证模板ID
            
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
2. 保存凭证模板信息
3. 保存凭证分录信息

## 4. 提交凭证模板

**接口描述**：提交凭证模板

**请求URL**：`POST /api/journal-template/{tempName}/submit`

**响应结果**：
```json
{
    "code": 000000,
    "message": "success",
    "data": {}
}
```

**处理逻辑**：
1. 校验模板是否存在
2. 校验状态是否为"未提交"
3. 更新状态为"待复核"

## 5. 复核凭证模板

**接口描述**：复核凭证模板

**请求URL**：`POST /api/journal-template/{tempName}/review`

**响应结果**：
```json
{
    "code": 000000,
    "message": "success",
    "data": {
    }
}
```

**处理逻辑**：
1. 校验模板是否存在
2. 校验状态是否为"待复核"
3. 根据复核动作更新状态
4. 复合通过的凭证，触发对trade blotter，公司行为，计提数据的匹配进行制证

## 6. 复核拒绝凭证模板

**接口描述**：复核凭证模板

**请求URL**：`POST /api/journal-template/{tempName}/reject

**响应结果**：

```json
{
    "code": 000000,
    "message": "success",
    "data": {
    }
}
```

**处理逻辑**：

## 7. 获取凭证模板详情

**接口描述**：获取凭证模板详情

**请求URL**：`GET /api/journal-template/{tempName}`

**响应结果**：
```json
{
    "code": "000000",
    "data": [
        {
            "booksNo": "JT_SOB",  // 账套编号
             "remark": "Bond",  // 备注类型
   			 "tempType": "PAOB Buy",  // 模板类型
    		"streamType": "付款",  // 流水类型
            "payDirection": "B",  // 支付方向
            "segment1": "000104",  // 公司段
            "streamType": "15",  // 流水类型
            "subList": [
                {
                   "accountedCr": 0,  // 账户贷方金额
                    "accountedDr": 0,  // 账户借方金额
                    "booksNo": "JT_SOB",  // 账套编号
                    "citJournalTempId": "329C168E3EA2A98BE063B739210A81",  // 凭证模板ID
                    "createdBy": "INVESTOP",  // 创建人
                    "createdDate": 1744523603000,  // 创建时间
                    "dataState": "0",  // 数据状态
                    "direction": "D",  // 方向：D-贷方，G-借方
                    "effectiveDate": 1744523603000,  // 生效日期
                    "enteredCr": 0,  // 贷方金额
                    "enteredDr": 0,  // 借方金额
                    "journalLineDescription": "111",  // 分录描述
                    "payDirection": "B",  // 支付方向
                    "pEnteredCr": 0,  // 报告币贷方金额
                    "pEnteredDr": 0,  // 报告币借方金额
                    "segment1": "000105 合并调整_保险业（HFM）",  // 公司段
                    "segment2": "0000 缺省",  // 业务段
                    "segment3": "0000 缺省",  // 成本中心
                    "segment4": "000000 缺省",  // 产品段
                    "segment5": "100101 现金1（pos机）",  // 科目
                    "segment6": "000000 缺省",  // 子目
                    "segment7": "000000",  // 备用段1
                    "segment8": "0000 缺省",  // 备用段2（关联方）
                    "streamType": "银行借款利息",  // 流水类型
                    "tempCategory": "4",  // 模板类别
                    "tempName": "rteswt23223",  // 模板名称
                    "tempNo": 4271,  // 模板编号
                    "updatedBy": "INVESTOP",  // 更新人
                    "updatedDate": 1744523603000  // 更新时间
                }
            ],
            "tempName": "ddd"  // 模板名称
        }
    ],
    "message": "成功"
}
```

**处理逻辑**：
1. 校验模板是否存在
2. 返回模板详细信息
3. 返回凭证分录信息 

## 8. 未匹配凭证模板邮件提醒

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
    "code": 000000,  // 响应码，000000表示成功
    "message": "success",  // 响应消息
    "data": {
        "total": 100,  // 总记录数
        "pageNum": 1,  // 当前页码
        "pageSize": 10,  // 每页大小
        "records": [
            {
                "booksNo": "HK_SOB",  // 账套
                "categoryName": "导入凭证",  // 凭证类型：明晰凭证、手工凭证、导入凭证、其它凭证
                "checkBy": "ZHENGDACHENG976",  // 复核人
                "createdBy": "HUYONG552",  // 创建人
                "createdDate": 1743061774000,  // 创建时间
                "curNo": "HKD",  // 币种
                "dataState": "15",  // 数据状态：Y进入接口表 N还没进入接口表
                "effectiveDate": 1742486400000,  // 制证日期
                "journalName" : "模板名称"
                "flexibleStatus": "Y",  // 弹性域校验状态：Y-通过，N-不通过
                "journalDetail": [  // 凭证明细
                    {
                        "accountedCr": 0,  // 本位币贷方金额
                        "accountedDr": 0,  // 本位币借方金额
                        "balanceStatus": "N",  // 是否进行了科目余额处理
                        "booksNo": "HK_SOB",  // 账套
                        "categoryName": "3",  // 凭证类型
                        "checkBy": "ZHENGDACHENG976",  // 复核人
                        "checkDate": 1744013267000,  // 复核时间
                        "citJournalId": "314F27AA8E914A1FE0639522B51EBCA8",  // 凭证ID
                        "createdBy": "HUYONG552",  // 创建人
                        "createdDate": 1743061774000,  // 创建时间
                        "curNo": "HKD",  // 币种
                        "dataState": "15",  // 数据状态
                        "dealNumber": 0,  // 交易数量
                        "effectiveDate": 1742486400000,  // 制证日期
                        "enteredCr": 0,  // 贷方金额
                        "enteredDr": 6978.32,  // 借方金额
                        "exihibitionCount": 0,  // 展示次数
                        "journalLineDescription": "3.21支付宝BY VU LIMITED借款利",  // 凭证摘要
                        "journalSequence": "617600-0000-202503-JV01-0029",  // 凭证编号
                        "journalStatus": 1,  // 凭证状态：1-待上传，2-上传成功
                        "pEnteredCr": 0,  // 报告币贷金额
                        "pEnteredDr": 0,  // 报告币借金额
                        "readyState": "Y",  // 就绪凭证编号
                        "recordPerson": "HUYONG552",  // 记录人
                        "segment1": "617600",  // 公司段
                        "segment13": "HK_SOB",  // 备用段7
                        "segment2": "0000",  // 业务段
                        "segment3": "0000",  // 成本中心
                        "segment4": "000000",  // 产品段
                        "segment5": "2251000000",  // 科目
                        "segment5Description": "Currency Exchange - Temp",  // 科目描述
                        "segment6": "000000",  // 子目
                        "segment7": "000000",  // 备用段1
                        "segment8": "0000 缺省",  // 备用段2（关联方）
                        "updatedBy": "SYSTEM",  // 修改人
                        "updatedDate": 1743062048000,  // 修改时间
                        "writeOffs": "N"  // 冲销状态：Y已冲销
                    }
                ],
                "journalDetailCount": 3,  // 凭证明细数量
                "journalSequence": "617600-0000-202503-JV01-0029",  // 凭证编号
                "journalStatus": 1,  // 凭证状态
                "key": "",  // 键值
                "name": "",  // 名称
                "operator": "HUYONG552",  // 操作人
                "readyState": "Y",  // 就绪凭证编号
                "segment1": "617600_Wisesome Investment Limited",  // 公司段
                "segment13": "HK_SOB",  // 备用段7
                "segment14": "0000",  // 备用段8
                "segment2": "0000",  // 业务段
                "updatedBy": "SYSTEM",  // 修改人
                "updatedDate": 1743062048000  // 修改时间
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
    "journalSequence": ["xxx", "yyy"]  // 凭证编号列表
}
```

### 响应结果

```json
{
    "code": 000000,  // 响应码，000000表示成功
    "message": "已复核通过2条会计凭证"  // 响应消息
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
    "journalSequence": "111111"  // 凭证编号
}
```

### 响应结果

```json
{
    "code": 000000,  // 响应码，000000表示成功
    "message": "复合通过成功"  // 响应消息
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
    "journalSequence": "111111"  // 凭证编号
}
```

### 响应结果

```json
{
    "code": 000000,  // 响应码，000000表示成功
    "message": "复合不通过成功"  // 响应消息
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
    "journalSequence": ["xxx", "yyy"]  // 凭证编号列表
}
```

### 响应结果

```json
{
    "code": 000000,  // 响应码，000000表示成功
    "message": "已复核拒绝2条会计凭证"  // 响应消息
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

excel文件中包括以下字段： 凭证编号，账套，公司段，凭证日期，币种，科目，子目，业务段，成本中心，产品段，关联方，原币借方金额，原币贷方金额，本位币借方金额，本位币贷方金额，凭证摘要 。

### 响应结果

```json
{
    "code": 000000,  // 响应码，000000表示成功
    "message": "已成功上传2条会计凭证，请联系复核同事进行复核处理！"  // 响应消息
}
```

### 处理逻辑

1. 校验文件格式必须为xlsx，提示文件格式不符合校验文件！
2. 校验文件内容不可读，提示文件不可读，请检查文件后重试！
3. 校验会计凭证数据
   1. [X行Y列]科目不存在
   2. 凭证编号已存在表里，提示 [凭证编号]已存在
   3. 凭证编号相同，借贷不相同，提示 [凭证编号] 借贷不平衡
   4. 读取每一行：本位币借方金额，本位币贷方金额非必填，原币借方金额，原币贷方金额其中一个有值就行，其它所有的列字段必填
4. 保存凭证数据，状态为"待复核"
5. 记录创建人和创建时间

## 9. 编辑凭证

### 请求URL

```
Get /api/journal/update
```

### 响应结果

```json
 [
        {
            "accountedCr": 0,  // 本位币贷方金额
            "accountedDr": 0,  // 本位币借方金额
            "booksNo": "HK_SOB",  // 账套
            "containsFlexible": false,  // 是否包含弹性域
            "createdBy": "WANGYE645",  // 创建人
            "createdDate": 1744622179000,  // 创建时间
            "curNo": "USD",  // 币种
            "dataState": "1",  // 数据状态：Y进入接口表 N还没进入接口表
            "dealNumber": 0,  // 交易数量
            "journalName" : "模板名称"
            "effectiveDate": 1744300800000,  // 制证日期
            "enteredCr": 0,  // 贷方金额
            "enteredDr": 0,  // 借方金额
            "exhibitionCount": 0,  // 展示次数
            "journalSequence": "010000-0000-202504-JV-0013",  // 凭证编号
            "journalStatus": 1,  // 凭证状态：1-待上传，2-上传成功
            "operator": "PCMS_WANGYE645",  // 操作人
            "pEnteredCr": 0,  // 报告币贷金额
            "pEnteredDr": 0,  // 报告币借金额
            "segment1": "010000_China Ping An Insurance Overseas (Holdings) Ltd",  // 公司段
            "segment13": "HK_SOB 海外控股公司总帐帐套",  // 备用段7
            "segment2": "0000_Default",  // 业务段
            "segmentList": [
                {
                    "accountedCr": 0,  // 本位币贷方金额
                    "accountedDr": 0,  // 本位币借方金额
                    "creditMoney": 0,  // 贷方金额
                    "debitMoney": 342,  // 借方金额
                    "description": "项目相关支付-税金支付",  // 凭证摘要
                    "flexibleList": [ ],  // 弹性域列表
                    "pcmsCitJournalId": "32B91491EE3C4E55E0639522B51EEBE1",  // 凭证ID
                    "pEnteredCr": 0,  // 报告币贷金额
                    "pEnteredDr": 0,  // 报告币借金额
                    "segment3": "0000_Default",  // 成本中心
                    "segment4": "000000_Default",  // 产品段
                    "segment5": "445127A_Conference",  // 科目
                    "segment6": "000000_Default1",  // 子目
                    "segment8": "0000_缺省"  // 备用段2（关联方）
                }, 
                {
                    "accountedCr": 0,  // 本位币贷方金额
                    "accountedDr": 0,  // 本位币借方金额
                    "creditMoney": 342,  // 贷方金额
                    "debitMoney": 0,  // 借方金额
                    "description": "YFX20250414016PCMS支付",  // 凭证摘要
                    "flexibleList": [
                        {
                            "bookNo": "HK_SOB",  // 账套
                            "contentType": "DATE",  // 内容类型
                            "enableFlag": "Y",  // 是否启用
                            "flexValueSetId": "103623",  // 弹性域值集ID
                            "flexibleCode": "ATTRIBUTE17",  // 弹性域代码
                            "flexibleDesc": "银行记帐日期",  // 弹性域描述
                            "requiredFlag": "N",  // 是否必填
                            "segment5": "1002030100",  // 科目
                            "segment5Desc": "Cash In Bank",  // 科目描述
                            "summaryFlag": "Y",  // 是否汇总
                            "updateDate": "2017-11-19 09:46:00"  // 更新时间
                        }, 
                        {
                            "bookNo": "HK_SOB",  // 账套
                            "contentType": "STRING",  // 内容类型
                            "enableFlag": "Y",  // 是否启用
                            "flexValueSetId": "1002544",  // 弹性域值集ID
                            "flexibleCode": "ATTRIBUTE20",  // 弹性域代码
                            "flexibleDesc": "银企对帐识别号",  // 弹性域描述
                            "requiredFlag": "N",  // 是否必填
                            "segment5": "1002030100",  // 科目
                            "segment5Desc": "Cash In Bank",  // 科目描述
                            "summaryFlag": "Y",  // 是否汇总
                            "updateDate": "2017-11-19 09:46:00"  // 更新时间
                        }
                    ], 
                    "pcmsCitJournalId": "32B91491EE3B4E55E0639522B51EEBE1",  // 凭证ID
                    "pEnteredCr": 0,  // 报告币贷金额
                    "pEnteredDr": 0,  // 报告币借金额
                    "segment3": "0000_Default",  // 成本中心
                    "segment4": "000000_Default",  // 产品段
                    "segment5": "1002030100 Cash in Bank-USD-HSBC",  // 科目
                    "segment6": "010093_808096010201/The Hongkong and Shanghai Banking Corporation Limited/China Ping An Insurance Overseas (Holdings) Ltd",  // 子目
                    "segment8": "0000_缺省"  // 备用段2（关联方）
                }
            ], 
            "updatedBy": "WANGYE645",  // 修改人
            "updatedDate": 1744622179000  // 修改时间
        }
    ]
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
    "code": "000000",  // 响应码，000000表示成功
    "data": [
        {
            "accountedCr": 0,  // 本位币贷方金额
            "accountedDr": 0,  // 本位币借方金额
            "booksNo": "HK_SOB",  // 账套
            "containsFlexible": false,  // 是否包含弹性域
            "createdBy": "WANGYE645",  // 创建人
            "createdDate": 1744622179000,  // 创建时间
            "curNo": "USD",  // 币种
            "journalName" : "模板名称"
            "dataState": "1",  // 数据状态：Y进入接口表 N还没进入接口表
            "dealNumber": 0,  // 交易数量
            "effectiveDate": 1744300800000,  // 制证日期
            "enteredCr": 0,  // 贷方金额
            "enteredDr": 0,  // 借方金额
            "exhibitionCount": 0,  // 展示次数
            "journalSequence": "010000-0000-202504-JV-0013",  // 凭证编号
            "journalStatus": 1,  // 凭证状态：1-待上传，2-上传成功
            "operator": "PCMS_WANGYE645",  // 操作人
            "pEnteredCr": 0,  // 报告币贷金额
            "pEnteredDr": 0,  // 报告币借金额
            "segment1": "010000_China Ping An Insurance Overseas (Holdings) Ltd",  // 公司段
            "segment13": "HK_SOB 海外控股公司总帐帐套",  // 备用段7
            "segment2": "0000_Default",  // 业务段
            "segmentList": [
                {
                    "accountedCr": 0,  // 本位币贷方金额
                    "accountedDr": 0,  // 本位币借方金额
                    "creditMoney": 0,  // 贷方金额
                    "debitMoney": 342,  // 借方金额
                    "description": "项目相关支付-税金支付",  // 凭证摘要
                    "flexibleList": [ ],  // 弹性域列表
                    "pcmsCitJournalId": "32B91491EE3C4E55E0639522B51EEBE1",  // 凭证ID
                    "pEnteredCr": 0,  // 报告币贷金额
                    "pEnteredDr": 0,  // 报告币借金额
                    "segment3": "0000_Default",  // 成本中心
                    "segment4": "000000_Default",  // 产品段
                    "segment5": "445127A_Conference",  // 科目
                    "segment6": "000000_Default1",  // 子目
                    "segment8": "0000_缺省"  // 备用段2（关联方）
                }, 
                {
                    "accountedCr": 0,  // 本位币贷方金额
                    "accountedDr": 0,  // 本位币借方金额
                    "creditMoney": 342,  // 贷方金额
                    "debitMoney": 0,  // 借方金额
                    "description": "YFX20250414016PCMS支付",  // 凭证摘要
                    "flexibleList": [
                        {
                            "bookNo": "HK_SOB",  // 账套
                            "contentType": "DATE",  // 内容类型
                            "enableFlag": "Y",  // 是否启用
                            "flexValueSetId": "103623",  // 弹性域值集ID
                            "flexibleCode": "ATTRIBUTE17",  // 弹性域代码
                            "flexibleDesc": "银行记帐日期",  // 弹性域描述
                            "requiredFlag": "N",  // 是否必填
                            "segment5": "1002030100",  // 科目
                            "segment5Desc": "Cash In Bank",  // 科目描述
                            "summaryFlag": "Y",  // 是否汇总
                            "updateDate": "2017-11-19 09:46:00"  // 更新时间
                        }, 
                        {
                            "bookNo": "HK_SOB",  // 账套
                            "contentType": "STRING",  // 内容类型
                            "enableFlag": "Y",  // 是否启用
                            "flexValueSetId": "1002544",  // 弹性域值集ID
                            "flexibleCode": "ATTRIBUTE20",  // 弹性域代码
                            "flexibleDesc": "银企对帐识别号",  // 弹性域描述
                            "requiredFlag": "N",  // 是否必填
                            "segment5": "1002030100",  // 科目
                            "segment5Desc": "Cash In Bank",  // 科目描述
                            "summaryFlag": "Y",  // 是否汇总
                            "updateDate": "2017-11-19 09:46:00"  // 更新时间
                        }
                    ], 
                    "pcmsCitJournalId": "32B91491EE3B4E55E0639522B51EEBE1",  // 凭证ID
                    "pEnteredCr": 0,  // 报告币贷金额
                    "pEnteredDr": 0,  // 报告币借金额
                    "segment3": "0000_Default",  // 成本中心
                    "segment4": "000000_Default",  // 产品段
                    "segment5": "1002030100 Cash in Bank-USD-HSBC",  // 科目
                    "segment6": "010093_808096010201/The Hongkong and Shanghai Banking Corporation Limited/China Ping An Insurance Overseas (Holdings) Ltd",  // 子目
                    "segment8": "0000_缺省"  // 备用段2（关联方）
                }
            ], 
            "updatedBy": "WANGYE645",  // 修改人
            "updatedDate": 1744622179000  // 修改时间
        }
    ], 
    "message": "成功"  // 响应消息
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

```json
{
    "setOfBooksId": "HK SOB",  // 账套代码
    "periodName": "2024-04",  // 会计期间
    "segment1": "800000",  // 公司段
    "segment2": "0000",  // 业务段
    "segment3": "0000",  // 成本中心
    "segment4": "000000",  // 产品段
    "segment5": "1001",  // 科目
    "segment6": "000000",  // 子目
    "segment7": "0000",  // 备用段1
    "segment8": "0000",  // 备用段2（关联方）
    "segment9": "0000",  // 备用段3
    "segment10": "0000",  // 备用段4
    "segment11": "0000",  // 备用段5
    "segment12": "0000",  // 备用段6
    "segment13": "0000",  // 备用段7
    "segment14": "0000",  // 备用段8
    "curNo": "HKD",  // 币种
    "pageNum": 1,  // 页码
    "pageSize": 10  // 每页条数
}
```

### 响应结果

```json
{
    "code": 000000,  // 响应码，000000表示成功
    "message": "success",  // 响应消息
    "data": {
        "total": 100,  // 总记录数
        "pageNum": 1,  // 当前页码
        "pageSize": 10,  // 每页大小
        "records": [
            {
                "codeCombinationId": "123456",  // 组合代码ID
                "setOfBooksId": "HK SOB",  // 账套代码
                "booksName": "香港账套",  // 账套名称
                "periodName": "2024-04",  // 会计期间
                "currencyCode": "HKD",  // 币种
                "actualFlag": "Y",  // 实际数标记：Y-实际数
                "segment1": "800000",  // 公司段
                "segment1Description": "平安海外控股",  // 公司段描述
                "segment2": "0000",  // 业务段
                "segment2Description": "默认业务",  // 业务段描述
                "segment3": "0000",  // 成本中心
                "segment3Description": "默认成本中心",  // 成本中心描述
                "segment4": "000000",  // 产品段
                "segment4Description": "默认产品",  // 产品段描述
                "accCode1": "1001",  // 一级会计科目
                "accCode1Name": "现金",  // 一级会计科目名称
                "accCode2": "1002",  // 二级会计科目
                "accCode2Name": "银行存款",  // 二级会计科目名称
                "accCode3": "100203",  // 三级会计科目
                "accCode3Name": "HSBC银行",  // 三级会计科目名称
                "segment6": "000000",  // 子目段
                "segment6Description": "默认子目",  // 子目段描述
                "segment7": "0000",  // 备用段1
                "segment7Description": "备用段1描述",  // 备用段1描述
                "segment8": "0000",  // 关联方
                "segment8Description": "默认关联方",  // 关联方描述
                "beginBalanceDr": 1000.00,  // 期初借方余额
                "beginBalanceCr": 0.00,  // 期初贷方余额
                "periodNetDr": 500.00,  // 本期借方发生额
                "periodNetCr": 200.00,  // 本期贷方发生额
                "endBalanceDr": 1300.00,  // 期末借方余额
                "endBalanceCr": 0.00,  // 期末贷方余额
                "endBalance": 1300.00,  // 期末余额
                "createdTime": "2024-04-01 00:00:00",  // 创建时间
                "updatedTime": "2024-04-01 00:00:00",  // 更新时间
                "journalList": [  // 凭证列表
                    {
                        "journalSequence": "617600-0000-202503-JV01-0029",  // 凭证编号
                        "effectiveDate": "2024-03-21",  // 制证日期
                        "journalLineDescription": "项目相关支付-税金支付",  // 凭证摘要
                        "enteredDr": 342.00,  // 借方金额
                        "enteredCr": 0.00,  // 贷方金额
                        "accountedDr": 342.00,  // 本位币借方金额
                        "accountedCr": 0.00,  // 本位币贷方金额
                        "pEnteredDr": 342.00,  // 报告币借方金额
                        "pEnteredCr": 0.00,  // 报告币贷方金额
                        "createdBy": "WANGYE645",  // 创建人
                        "createdDate": "2024-03-21 10:00:00",  // 创建时间
                        "checkBy": "ZHENGDACHENG976",  // 复核人
                        "checkDate": "2024-03-22 09:00:00"  // 复核时间
                    }
                ]
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
        "accCode1": "1000",  // 一级会计科目
        "accCode1Name": "资产类",  // 一级会计科目名称
        "accCode2": "1001",  // 二级会计科目
        "accCode2Name": "流动资产",  // 二级会计科目名称
        "accCode3": "100101",  // 三级会计科目
        "accCode3Name": "现金",  // 三级会计科目名称
        "segment6": "S001",  // 子目段
        "segment6Description": "子目A",  // 子目段描述
        "segment7": "R001",  // 备用段1
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

## 12. 查询科目余额详情

### 请求URL

```
GET /api/subject-balance/detail
```

### 请求参数

```json
{
    "setOfBooksId": "HK SOB",  // 账套代码
    "periodName": "2024-04",  // 会计期间
    "segment1": "800000",  // 公司段
    "segment2": "0000",  // 业务段
    "segment3": "0000",  // 成本中心
    "segment4": "000000",  // 产品段
    "segment5": "1001",  // 科目
    "segment6": "000000",  // 子目
    "segment7": "0000",  // 备用段1
    "segment8": "0000",  // 备用段2（关联方）
    "segment9": "0000",  // 备用段3
    "segment10": "0000",  // 备用段4
    "segment11": "0000",  // 备用段5
    "segment12": "0000",  // 备用段6
    "segment13": "0000",  // 备用段7
    "segment14": "0000",  // 备用段8
    "curNo": "HKD",  // 币种
    "pageNum": 1,  // 页码
    "pageSize": 10  // 每页条数
}
```

### 响应结果

```json
{
    "code": 000000,  // 响应码，000000表示成功
    "message": "success",  // 响应消息
    "data": {
        "total": 100,  // 总记录数
        "pageNum": 1,  // 当前页码
        "pageSize": 10,  // 每页大小
        "records": [
            {
                "codeCombinationId": "123456",  // 组合代码ID
                "setOfBooksId": "HK SOB",  // 账套代码
                "booksName": "香港账套",  // 账套名称
                "periodName": "2024-04",  // 会计期间
                "currencyCode": "HKD",  // 币种
                "actualFlag": "Y",  // 实际数标记：Y-实际数
                "segment1": "800000",  // 公司段
                "segment1Description": "平安海外控股",  // 公司段描述
                "segment2": "0000",  // 业务段
                "segment2Description": "默认业务",  // 业务段描述
                "segment3": "0000",  // 成本中心
                "segment3Description": "默认成本中心",  // 成本中心描述
                "segment4": "000000",  // 产品段
                "segment4Description": "默认产品",  // 产品段描述
                "accCode1": "1001",  // 一级会计科目
                "accCode1Name": "现金",  // 一级会计科目名称
                "accCode2": "1002",  // 二级会计科目
                "accCode2Name": "银行存款",  // 二级会计科目名称
                "accCode3": "100203",  // 三级会计科目
                "accCode3Name": "HSBC银行",  // 三级会计科目名称
                "segment6": "000000",  // 子目段
                "segment6Description": "默认子目",  // 子目段描述
                "segment7": "0000",  // 备用段1
                "segment7Description": "备用段1描述",  // 备用段1描述
                "segment8": "0000",  // 关联方
                "segment8Description": "默认关联方",  // 关联方描述
                "beginBalanceDr": 1000.00,  // 期初借方余额
                "beginBalanceCr": 0.00,  // 期初贷方余额
                "periodNetDr": 500.00,  // 本期借方发生额
                "periodNetCr": 200.00,  // 本期贷方发生额
                "endBalanceDr": 1300.00,  // 期末借方余额
                "endBalanceCr": 0.00,  // 期末贷方余额
                "endBalance": 1300.00,  // 期末余额
                "createdTime": "2024-04-01 00:00:00",  // 创建时间
                "updatedTime": "2024-04-01 00:00:00",  // 更新时间
                "journalList": [  // 凭证列表
                    {
                        "journalSequence": "617600-0000-202503-JV01-0029",  // 凭证编号
                        "effectiveDate": "2024-03-21",  // 制证日期
                        "journalLineDescription": "项目相关支付-税金支付",  // 凭证摘要
                        "enteredDr": 342.00,  // 借方金额
                        "enteredCr": 0.00,  // 贷方金额
                        "accountedDr": 342.00,  // 本位币借方金额
                        "accountedCr": 0.00,  // 本位币贷方金额
                        "pEnteredDr": 342.00,  // 报告币借方金额
                        "pEnteredCr": 0.00,  // 报告币贷方金额
                        "createdBy": "WANGYE645",  // 创建人
                        "createdDate": "2024-03-21 10:00:00",  // 创建时间
                        "checkBy": "ZHENGDACHENG976",  // 复核人
                        "checkDate": "2024-03-22 09:00:00"  // 复核时间
                    }
                ]
            }
        ]
    }
}
```
