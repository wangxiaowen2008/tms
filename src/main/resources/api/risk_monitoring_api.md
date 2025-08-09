# 风险监控文件上传接口

## 接口名称
风险监控文件上传

## 请求URL
`/api/risk/monitoring/upload`

## 请求方式
`POST`

## 请求参数
```json
{
  "file": "文件对象"  // Excel文件，支持.xlsm格式
}
```

### 参数说明
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| file | File | 是 | Excel文件，支持.xlsm格式 |

## 处理逻辑
1. 接收上传的文件
2. 验证文件格式（仅支持.xlsm格式）
3. 解析Excel文件内容
4. 过滤无效数据（以#N/A或#NAME?开头的数据）
5. 将有效数据保存到数据库
6. 记录操作日志

## 返回参数
```json
{
  "code": 200,
  "message": "上传成功",
  "data": {
    "totalCount": 100,  // 总记录数
    "validCount": 95,   // 有效记录数
    "invalidCount": 5,  // 无效记录数
    "uploadTime": "2024-03-20 10:00:00"  // 上传时间
  }
}
```

### 返回参数说明
| 参数名 | 类型 | 说明 |
|--------|------|------|
| code | Integer | 状态码 |
| message | String | 提示信息 |
| data | Object | 返回数据 |
| - totalCount | Integer | 总记录数 |
| - validCount | Integer | 有效记录数 |
| - invalidCount | Integer | 无效记录数 |
| - uploadTime | String | 上传时间 |

## 错误码说明
| 错误码 | 说明 |
|--------|------|
| 400 | 请求参数错误 |
| 401 | 未授权 |
| 403 | 禁止访问 |
| 500 | 服务器内部错误 |

## 示例
### 请求示例
```http
POST /api/risk/monitoring/upload
Content-Type: multipart/form-data

file: [文件内容]
```

### 响应示例
```json
{
  "code": 200,
  "message": "上传成功",
  "data": {
    "totalCount": 100,
    "validCount": 95,
    "invalidCount": 5,
    "uploadTime": "2024-03-20 10:00:00"
  }
}
```

## 注意事项
1. 文件格式要求：仅支持.xlsm格式的Excel文件
2. 文件大小限制：最大10MB
3. 无效数据处理：以#N/A或#NAME?开头的数据将被视为无效数据
4. 数据验证：上传前会进行数据格式和有效性验证
5. 并发处理：支持多用户同时上传 