# 支付功能使用说明

## 功能概述

本支付功能模块为TMS系统提供了完整的支付流程，包括订单创建、支付方式选择、支付验证和支付完成等步骤。

## 文件结构

```
src/main/resources/static/
├── payment.html          # 支付页面
├── payment.css           # 支付页面样式
├── payment.js            # 支付页面脚本
└── README.md             # 使用说明

src/main/java/com/paob/tms/
├── model/
│   └── PaymentOrder.java           # 支付订单实体类
├── dto/
│   ├── req/
│   │   └── PaymentOrderReq.java    # 支付订单请求DTO
│   └── resp/
│       └── PaymentOrderResp.java   # 支付订单响应DTO
├── mapper/
│   └── PaymentOrderMapper.java     # 支付订单Mapper接口
├── service/
│   ├── PaymentOrderService.java    # 支付订单服务接口
│   └── impl/
│       └── PaymentOrderServiceImpl.java  # 支付订单服务实现
├── mapping/
│   └── PaymentOrderMapping.java    # 支付订单映射类
└── web/controller/
    └── PaymentController.java      # 支付控制器

src/main/resources/
├── mapper/
│   └── PaymentOrderMapper.xml      # MyBatis映射文件
└── db/
    └── payment_order.sql           # 数据库表结构
```

## 支付流程

### 1. 订单确认
- 显示订单详细信息
- 确认订单金额和商品信息
- 点击"确认订单"进入下一步

### 2. 选择支付方式
- 支持支付宝、微信支付、银行转账
- 选择支付方式后点击"确认支付方式"

### 3. 支付验证
- 生成支付二维码
- 显示支付倒计时（5分钟）
- 自动检查支付状态
- 支持手动检查支付状态

### 4. 支付完成
- 显示支付成功信息
- 提供收据下载功能
- 可返回首页或查看订单详情

## API接口

### 1. 创建支付订单
```
POST /api/payment/order
Content-Type: application/json

{
    "userId": 1,
    "productName": "运输管理系统服务",
    "servicePeriod": "1个月",
    "unitPrice": 1000.00,
    "totalAmount": 1000.00,
    "paymentMethod": "alipay",
    "remark": "订单备注"
}
```

### 2. 查询支付订单
```
GET /api/payment/order/{orderNumber}
```

### 3. 分页查询用户订单
```
GET /api/payment/orders?current=1&size=10&userId=1
```

### 4. 生成支付二维码
```
POST /api/payment/qrcode/{orderNumber}
```

### 5. 检查支付状态
```
GET /api/payment/status/{orderNumber}
```

### 6. 支付回调处理
```
POST /api/payment/callback?orderNumber=xxx&thirdPartyOrderNo=xxx&paymentStatus=paid
```

### 7. 取消支付订单
```
POST /api/payment/cancel/{orderNumber}
```

### 8. 生成订单号
```
GET /api/payment/order-number
```

## 数据库表结构

### t_payment_order（支付订单表）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | BIGINT | 主键ID |
| order_number | VARCHAR(50) | 订单号（唯一） |
| user_id | BIGINT | 用户ID |
| product_name | VARCHAR(200) | 商品名称 |
| service_period | VARCHAR(50) | 服务周期 |
| unit_price | DECIMAL(10,2) | 单价 |
| total_amount | DECIMAL(10,2) | 总金额 |
| payment_method | VARCHAR(20) | 支付方式 |
| payment_status | VARCHAR(20) | 支付状态 |
| payment_time | TIMESTAMP | 支付时间 |
| third_party_order_no | VARCHAR(100) | 第三方支付订单号 |
| qr_code_url | VARCHAR(500) | 支付二维码URL |
| remark | TEXT | 订单备注 |
| create_time | TIMESTAMP | 创建时间 |
| update_time | TIMESTAMP | 更新时间 |
| is_deleted | INTEGER | 是否删除 |

## 支付状态说明

- `pending`: 待支付
- `paid`: 已支付
- `failed`: 支付失败
- `cancelled`: 已取消

## 支付方式说明

- `alipay`: 支付宝
- `wechat`: 微信支付
- `bank`: 银行转账

## 使用方法

### 1. 启动应用
确保Spring Boot应用正常启动，数据库连接正常。

### 2. 访问支付页面
在浏览器中访问：`http://localhost:8080/payment.html`

### 3. 测试支付流程
1. 确认订单信息
2. 选择支付方式
3. 等待支付验证（模拟）
4. 查看支付结果

## 注意事项

1. 当前版本为演示版本，支付功能为模拟实现
2. 实际使用时需要集成真实的第三方支付平台API
3. 支付回调需要配置正确的回调地址
4. 建议在生产环境中添加支付安全验证
5. 支付超时时间可根据业务需求调整

## 扩展功能

1. 支持更多支付方式（银联、PayPal等）
2. 添加支付手续费计算
3. 支持分期付款
4. 添加支付风控检测
5. 支持退款功能
6. 添加支付统计报表

## 技术栈

- 前端：HTML5 + CSS3 + JavaScript + Font Awesome
- 后端：Spring Boot 3 + MyBatis Plus + PostgreSQL
- 数据库：PostgreSQL
- 构建工具：Maven
- API文档：Swagger 2 