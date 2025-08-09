-- 支付订单表
CREATE TABLE IF NOT EXISTS t_payment_order (
    id BIGSERIAL PRIMARY KEY,
    order_number VARCHAR(50) NOT NULL UNIQUE COMMENT '订单号',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    product_name VARCHAR(200) NOT NULL COMMENT '商品名称',
    service_period VARCHAR(50) NOT NULL COMMENT '服务周期',
    unit_price DECIMAL(10,2) NOT NULL COMMENT '单价',
    total_amount DECIMAL(10,2) NOT NULL COMMENT '总金额',
    payment_method VARCHAR(20) NOT NULL COMMENT '支付方式（alipay-支付宝，wechat-微信支付，bank-银行转账）',
    payment_status VARCHAR(20) NOT NULL DEFAULT 'pending' COMMENT '支付状态（pending-待支付，paid-已支付，failed-支付失败，cancelled-已取消）',
    payment_time TIMESTAMP NULL COMMENT '支付时间',
    third_party_order_no VARCHAR(100) NULL COMMENT '第三方支付订单号',
    qr_code_url VARCHAR(500) NULL COMMENT '支付二维码URL',
    remark TEXT NULL COMMENT '订单备注',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted INTEGER NOT NULL DEFAULT 0 COMMENT '是否删除（0-未删除，1-已删除）'
);

-- 创建索引
CREATE INDEX idx_payment_order_user_id ON t_payment_order(user_id);
CREATE INDEX idx_payment_order_payment_status ON t_payment_order(payment_status);
CREATE INDEX idx_payment_order_create_time ON t_payment_order(create_time);
CREATE INDEX idx_payment_order_third_party_order_no ON t_payment_order(third_party_order_no);

-- 添加注释
COMMENT ON TABLE t_payment_order IS '支付订单表';
COMMENT ON COLUMN t_payment_order.id IS '主键ID';
COMMENT ON COLUMN t_payment_order.order_number IS '订单号';
COMMENT ON COLUMN t_payment_order.user_id IS '用户ID';
COMMENT ON COLUMN t_payment_order.product_name IS '商品名称';
COMMENT ON COLUMN t_payment_order.service_period IS '服务周期';
COMMENT ON COLUMN t_payment_order.unit_price IS '单价';
COMMENT ON COLUMN t_payment_order.total_amount IS '总金额';
COMMENT ON COLUMN t_payment_order.payment_method IS '支付方式（alipay-支付宝，wechat-微信支付，bank-银行转账）';
COMMENT ON COLUMN t_payment_order.payment_status IS '支付状态（pending-待支付，paid-已支付，failed-支付失败，cancelled-已取消）';
COMMENT ON COLUMN t_payment_order.payment_time IS '支付时间';
COMMENT ON COLUMN t_payment_order.third_party_order_no IS '第三方支付订单号';
COMMENT ON COLUMN t_payment_order.qr_code_url IS '支付二维码URL';
COMMENT ON COLUMN t_payment_order.remark IS '订单备注';
COMMENT ON COLUMN t_payment_order.create_time IS '创建时间';
COMMENT ON COLUMN t_payment_order.update_time IS '更新时间';
COMMENT ON COLUMN t_payment_order.is_deleted IS '是否删除（0-未删除，1-已删除）';

-- 插入测试数据
INSERT INTO t_payment_order (
    order_number, user_id, product_name, service_period, unit_price, total_amount,
    payment_method, payment_status, remark
) VALUES (
    'TMS20241201001', 1, '运输管理系统服务', '1个月', 1000.00, 1000.00,
    'alipay', 'pending', '测试订单'
) ON CONFLICT (order_number) DO NOTHING; 