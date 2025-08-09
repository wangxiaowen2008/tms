# 附件管理接口文档

## 1. 查询附件列表

### 接口信息
- **接口名称**: 查询附件列表
- **请求URL**: `/api/attachment/list`
- **请求方式**: POST

### 请求参数
```json
{
    "attachmentType": "string",  // 附件类型
    "businessKey": "string",     // 业务主键
    "uploadBy": "string",        // 上传人
    "createTimeStart": "string", // 创建时间开始
    "createTimeEnd": "string",   // 创建时间结束
    "current": "long",           // 当前页码
    "size": "long"              // 每页条数
}
```

### 返回参数
```json
{
    "code": "string",    // 响应码
    "message": "string", // 响应消息
    "data": {
        "records": [
            {
                "id": "long",           // 附件ID
                "fileName": "string",   // 文件名称
                "fileType": "string",   // 文件类型
                "fileSize": "long",     // 文件大小
                "attachmentType": "string", // 附件类型
                "businessKey": "string",    // 业务主键
                "uploadBy": "string",       // 上传人
                "createTime": "string",     // 创建时间
                "updateTime": "string"      // 更新时间
            }
        ],
        "total": "long",         // 总记录数
        "size": "long",          // 每页显示条数
        "current": "long",       // 当前页
        "pages": "long"          // 总页数
    }
}
```

## 2. 上传附件

### 接口信息
- **接口名称**: 上传附件
- **请求URL**: `/api/attachment/upload`
- **请求方式**: POST

### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| file | MultipartFile | 是 | 上传的文件 |
| attachmentType | String | 是 | 附件类型 |
| businessKey | String | 是 | 业务主键 |
| uploadBy | String | 是 | 上传人 |

### 返回参数
```json
{
    "code": "string",    // 响应码
    "message": "string", // 响应消息
    "data": {
        "id": "long",           // 附件ID
        "fileName": "string",   // 文件名称
        "fileType": "string",   // 文件类型
        "fileSize": "long",     // 文件大小
        "attachmentType": "string", // 附件类型
        "businessKey": "string",    // 业务主键
        "uploadBy": "string",       // 上传人
        "createTime": "string",     // 创建时间
        "updateTime": "string"      // 更新时间
    }
}
```

## 3. 删除附件

### 接口信息
- **接口名称**: 删除附件
- **请求URL**: `/api/attachment/{id}`
- **请求方式**: DELETE

### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 附件ID（路径参数） |
| updatedBy | String | 是 | 更新人 |

### 返回参数
```json
{
    "code": "string",    // 响应码
    "message": "string", // 响应消息
    "data": null
}
```

## 4. 下载附件

### 接口信息
- **接口名称**: 下载附件
- **请求URL**: `/api/attachment/download/{id}`
- **请求方式**: GET

### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 附件ID（路径参数） |

### 返回参数
- 直接返回文件流 







## 状态码说明

| 状态码 | 说明         |
| ------ | ------------ |
| 000000 | 操作成功     |
| 400    | 请求参数错误 |
| 401    | 未授权       |
| 403    | 无权限访问   |
| 404    | 资源不存在   |
| 500    | 系统错误     |

## 9. 枚举值说明

### Remark类型

- Bond：债券
- CD：存单
- EFB：电子银行承兑汇票
- UST：美国国债
- Interbank：同业拆借
- Fixed Deposit：定期存款

### 交易类型

- PAOB Buy：PAOB买入
- PAOB Sell：PAOB卖出
- PAOB Borrow：PAOB借入
- PAOB Lend：PAOB借出

### 付款状态

- 未付款  0
- 付款中  1
- 已付款  2
- 未收款  3
- 已收款  4

### 制证状态

- 未制证  ：凭证状态 0
- 待复核   ： 凭证状态1  数据状态 1
- 复核通过 & 待上传 ：凭证状态1  数据状态 2
- 复核通过 & 上传失败：凭证状态3 数据状态 2
- 复核通过 & 上传成功：凭证状态2  数据状态 2