# 公司行为接口文档

## 1. 公司行为查询接口

### 接口名称
公司行为查询

### 请求URL
`/api/company/action/query`

### 请求方式
`POST`

### 请求参数
```json
{
  "remark": "EFB",                    // 公司行为类型，枚举值：Bond、CD、EFB、UST、Interbank、Fixed Deposit、Repo
  "paymentType": "收取利息",          // 到期/利息类型，枚举值：支付利息、支付本金&利息、收取利息、收取本金&利息
  "tradeNumber": "TR001",             // Trade number，支持模糊搜索
  "actionNumber": "EFB00001",         // 公司行为编号，支持模糊搜索
  "valueDateStart": "2024-01-01",     // Value Date开始日期
  "valueDateEnd": "2024-12-31",       // Value Date结束日期
  "currency": "USD",                  // 币种
  "settlementAmountMin": 1000.00,     // Settlement Amount最小值
  "settlementAmountMax": 10000.00,    // Settlement Amount最大值
  "isin": "US1234567890",             // ISIN代码
  "paymentStatus": "未付款",          // 付/收款状态，枚举值：未付款、付款中、已付款、未收款、已收款
  "voucherStatus": "未制证",          // 制证状态，枚举值：未制证、待复核、复核通过&待上传、复核通过&上传失败、复核通过&上传成功
  "voucherNumber": "V001",            // 凭证编号，支持模糊搜索
  "pageNum": 1,                       // 页码，从1开始
  "pageSize": 20                      // 每页大小
}
```

### 参数说明
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| remark | String | 否 | 公司行为类型，枚举值 |
| paymentType | String | 否 | 到期/利息类型，枚举值 |
| tradeNumber | String | 否 | Trade number，支持模糊搜索 |
| actionNumber | String | 否 | 公司行为编号，支持模糊搜索 |
| valueDateStart | String | 否 | Value Date开始日期，格式：yyyy-MM-dd |
| valueDateEnd | String | 否 | Value Date结束日期，格式：yyyy-MM-dd |
| currency | String | 否 | 币种 |
| settlementAmountMin | BigDecimal | 否 | Settlement Amount最小值 |
| settlementAmountMax | BigDecimal | 否 | Settlement Amount最大值 |
| isin | String | 否 | ISIN代码 |
| paymentStatus | String | 否 | 付/收款状态，枚举值 |
| voucherStatus | String | 否 | 制证状态，枚举值 |
| voucherNumber | String | 否 | 凭证编号，支持模糊搜索 |
| pageNum | Integer | 是 | 页码，从1开始 |
| pageSize | Integer | 是 | 每页大小 |

### 处理逻辑
1. 验证请求参数
2. 根据查询条件构建查询条件
3. 执行分页查询
4. 按Value Date倒序、Trade Number正序排序
5. 返回查询结果

### 返回参数
```json
{
  "code": 200,
  "message": "查询成功",
  "data": {
    "total": 100,
    "pages": 5,
    "current": 1,
    "size": 20,
    "records": [
      {
        "id": 1,
        "actionNumber": "EFB00001",           // 公司行为编号
        "tradeNumber": "TR001,TR002",         // Trade Number，多个以逗号隔开
        "entityId": "ENTITY001",              // Entity ID
        "counterpart": "COUNTERPART001",      // Counterpart
        "paymentType": "收取利息",            // 到期/利息
        "currency": "USD",                    // Currency
        "settlementAmount": 5000.00,          // Settlement Amount
        "valueDate": "2024-03-20",            // Value Date
        "remark": "EFB",                      // Remark
        "debtSecurityName": "Security Name",  // Debt Security Name
        "isin": "US1234567890",               // ISIN
        "settlementAc": "SETTLEMENT001",      // Settlement A/C
        "broker": "BROKER001",                // Broker
        "paymentStatus": "未收款",            // 付/收款状态
        "voucherStatus": "未制证",            // 制证状态
        "voucherNumber": "V001",              // 凭证编号
        "principalAmount": 4500.00,           // 本金金额
        "interestAmount": 500.00              // 利息金额
      }
    ]
  }
}
```

### 返回参数说明
| 参数名 | 类型 | 说明 |
|--------|------|------|
| code | Integer | 状态码 |
| message | String | 提示信息 |
| data | Object | 返回数据 |
| - total | Integer | 总记录数 |
| - pages | Integer | 总页数 |
| - current | Integer | 当前页 |
| - size | Integer | 每页大小 |
| - records | Array | 记录列表 |
| -- id | Long | 主键ID |
| -- actionNumber | String | 公司行为编号 |
| -- tradeNumber | String | Trade Number |
| -- entityId | String | Entity ID |
| -- counterpart | String | Counterpart |
| -- paymentType | String | 到期/利息 |
| -- currency | String | Currency |
| -- settlementAmount | BigDecimal | Settlement Amount |
| -- valueDate | String | Value Date |
| -- remark | String | Remark |
| -- debtSecurityName | String | Debt Security Name |
| -- isin | String | ISIN |
| -- settlementAc | String | Settlement A/C |
| -- broker | String | Broker |
| -- paymentStatus | String | 付/收款状态 |
| -- voucherStatus | String | 制证状态 |
| -- voucherNumber | String | 凭证编号 |
| -- principalAmount | BigDecimal | 本金金额 |
| -- interestAmount | BigDecimal | 利息金额 |

## 2. 公司行为重置接口

### 接口名称
公司行为查询条件重置

### 请求URL
`/api/company/action/reset`

### 请求方式
`POST`

### 请求参数
```json
{}
```

### 处理逻辑
1. 清空当前查询条件
2. 返回默认查询结果

### 返回参数
```json
{
  "code": 200,
  "message": "重置成功",
  "data": {
    "total": 0,
    "pages": 0,
    "current": 1,
    "size": 20,
    "records": []
  }
}
```

## 3. 公司行为批量支付接口

### 接口名称
公司行为批量支付

### 请求URL
`/api/company/action/batch-payment`

### 请求方式
`POST`

### 请求参数
```json
{
  "actionIds": [1, 2, 3],             // 公司行为ID列表
  "paymentType": "批量支付",          // 支付类型
  "remark": "批量支付操作"            // 备注
}
```

### 参数说明
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| actionIds | Array | 是 | 公司行为ID列表 |
| paymentType | String | 是 | 支付类型 |
| remark | String | 否 | 备注 |

### 处理逻辑
1. 验证选中的公司行为记录
2. 检查支付状态（只能对未付款的记录进行支付）
3. 生成支付订单
4. 更新公司行为支付状态
5. 记录操作日志

### 返回参数
```json
{
  "code": 200,
  "message": "批量支付成功",
  "data": {
    "successCount": 3,                // 成功数量
    "failCount": 0,                   // 失败数量
    "paymentOrderIds": ["PO001", "PO002", "PO003"],  // 支付订单ID列表
    "details": [
      {
        "actionId": 1,
        "actionNumber": "EFB00001",
        "status": "成功",
        "message": "支付订单已生成"
      }
    ]
  }
}
```

### 返回参数说明
| 参数名 | 类型 | 说明 |
|--------|------|------|
| code | Integer | 状态码 |
| message | String | 提示信息 |
| data | Object | 返回数据 |
| - successCount | Integer | 成功数量 |
| - failCount | Integer | 失败数量 |
| - paymentOrderIds | Array | 支付订单ID列表 |
| - details | Array | 详细信息 |
| -- actionId | Long | 公司行为ID |
| -- actionNumber | String | 公司行为编号 |
| -- status | String | 处理状态 |
| -- message | String | 处理消息 |

## 4. 公司行为批量制证接口

### 接口名称
公司行为批量制证

### 请求URL
`/api/company/action/batch-voucher`

### 请求方式
`POST`

### 请求参数
```json
{
  "actionIds": [1, 2, 3],             // 公司行为ID列表
  "voucherType": "批量制证",          // 制证类型
  "remark": "批量制证操作"            // 备注
}
```

### 参数说明
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| actionIds | Array | 是 | 公司行为ID列表 |
| voucherType | String | 是 | 制证类型 |
| remark | String | 否 | 备注 |

### 处理逻辑
1. 验证选中的公司行为记录
2. 检查制证状态（只能对未制证的记录进行制证）
3. 生成凭证
4. 更新公司行为制证状态
5. 记录操作日志

### 返回参数
```json
{
  "code": 200,
  "message": "批量制证成功",
  "data": {
    "successCount": 3,                // 成功数量
    "failCount": 0,                   // 失败数量
    "voucherNumbers": ["V001", "V002", "V003"],  // 凭证编号列表
    "details": [
      {
        "actionId": 1,
        "actionNumber": "EFB00001",
        "status": "成功",
        "message": "凭证已生成",
        "voucherNumber": "V001"
      }
    ]
  }
}
```

### 返回参数说明
| 参数名 | 类型 | 说明 |
|--------|------|------|
| code | Integer | 状态码 |
| message | String | 提示信息 |
| data | Object | 返回数据 |
| - successCount | Integer | 成功数量 |
| - failCount | Integer | 失败数量 |
| - voucherNumbers | Array | 凭证编号列表 |
| - details | Array | 详细信息 |
| -- actionId | Long | 公司行为ID |
| -- actionNumber | String | 公司行为编号 |
| -- status | String | 处理状态 |
| -- message | String | 处理消息 |
| -- voucherNumber | String | 凭证编号 |

## 5. 公司行为下载接口

### 接口名称
公司行为列表下载

### 请求URL
`/api/company/action/download`

### 请求方式
`POST`

### 请求参数
```json
{
  "remark": "EFB",                    // 公司行为类型
  "paymentType": "收取利息",          // 到期/利息类型
  "tradeNumber": "TR001",             // Trade number
  "actionNumber": "EFB00001",         // 公司行为编号
  "valueDateStart": "2024-01-01",     // Value Date开始日期
  "valueDateEnd": "2024-12-31",       // Value Date结束日期
  "currency": "USD",                  // 币种
  "settlementAmountMin": 1000.00,     // Settlement Amount最小值
  "settlementAmountMax": 10000.00,    // Settlement Amount最大值
  "isin": "US1234567890",             // ISIN代码
  "paymentStatus": "未付款",          // 付/收款状态
  "voucherStatus": "未制证",          // 制证状态
  "voucherNumber": "V001"             // 凭证编号
}
```

### 参数说明
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| remark | String | 否 | 公司行为类型 |
| paymentType | String | 否 | 到期/利息类型 |
| tradeNumber | String | 否 | Trade number |
| actionNumber | String | 否 | 公司行为编号 |
| valueDateStart | String | 否 | Value Date开始日期 |
| valueDateEnd | String | 否 | Value Date结束日期 |
| currency | String | 否 | 币种 |
| settlementAmountMin | BigDecimal | 否 | Settlement Amount最小值 |
| settlementAmountMax | BigDecimal | 否 | Settlement Amount最大值 |
| isin | String | 否 | ISIN代码 |
| paymentStatus | String | 否 | 付/收款状态 |
| voucherStatus | String | 否 | 制证状态 |
| voucherNumber | String | 否 | 凭证编号 |

### 处理逻辑
1. 根据查询条件获取数据
2. 生成Excel文件（文件名为"公司行为.xlsx"）
3. 包含所有列表字段
4. 按Value Date倒序、Trade Number正序排序
5. 返回文件下载链接

### 返回参数
```json
{
  "code": 200,
  "message": "下载成功",
  "data": {
    "downloadUrl": "/api/company/action/download/file/20240320100000_公司行为.xlsx",
    "fileName": "公司行为.xlsx",
    "fileSize": 1024000,
    "expireTime": "2024-03-21 10:00:00"
  }
}
```

### 返回参数说明
| 参数名 | 类型 | 说明 |
|--------|------|------|
| code | Integer | 状态码 |
| message | String | 提示信息 |
| data | Object | 返回数据 |
| - downloadUrl | String | 下载链接 |
| - fileName | String | 文件名 |
| - fileSize | Long | 文件大小（字节） |
| - expireTime | String | 链接过期时间 |

## 6. 公司行为重新生成接口

### 接口名称
公司行为重新生成

### 请求URL
`/api/company/action/regenerate`

### 请求方式
`POST`

### 请求参数
```json
{
  "actionIds": [1, 2, 3],             // 公司行为ID列表
  "regenerateType": "全部重新生成",   // 重新生成类型
  "remark": "重新生成操作"            // 备注
}
```

### 参数说明
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| actionIds | Array | 是 | 公司行为ID列表 |
| regenerateType | String | 是 | 重新生成类型 |
| remark | String | 否 | 备注 |

### 处理逻辑
1. 验证选中的公司行为记录
2. 检查重新生成条件（只能对未制证、未付款/收款的公司行为重新生成）
3. 根据Trade blotter重新计算数据
4. 更新公司行为数据
5. 记录操作日志

### 返回参数
```json
{
  "code": 200,
  "message": "重新生成成功",
  "data": {
    "successCount": 3,                // 成功数量
    "failCount": 0,                   // 失败数量
    "details": [
      {
        "actionId": 1,
        "actionNumber": "EFB00001",
        "status": "成功",
        "message": "数据已重新生成",
        "oldAmount": 5000.00,
        "newAmount": 5200.00
      }
    ]
  }
}
```

### 返回参数说明
| 参数名 | 类型 | 说明 |
|--------|------|------|
| code | Integer | 状态码 |
| message | String | 提示信息 |
| data | Object | 返回数据 |
| - successCount | Integer | 成功数量 |
| - failCount | Integer | 失败数量 |
| - details | Array | 详细信息 |
| -- actionId | Long | 公司行为ID |
| -- actionNumber | String | 公司行为编号 |
| -- status | String | 处理状态 |
| -- message | String | 处理消息 |
| -- oldAmount | BigDecimal | 原金额 |
| -- newAmount | BigDecimal | 新金额 |

## 错误码说明
| 错误码 | 说明 |
|--------|------|
| 400 | 请求参数错误 |
| 401 | 未授权 |
| 403 | 禁止访问 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

## 注意事项
1. 查询接口支持分页，默认每页20条记录
2. 批量操作接口支持最多100条记录同时处理
3. 下载文件有效期为24小时
4. 重新生成操作会覆盖原有数据，请谨慎操作
5. 所有接口都需要进行权限验证
6. 操作日志会自动记录用户操作行为 