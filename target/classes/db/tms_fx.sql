-- 3. TMS FX表
CREATE TABLE tms_fx (
    trade_number VARCHAR(50) PRIMARY KEY,
    counterpart VARCHAR(100),
    currency_pair VARCHAR(20),
    value_date DATE,
    bs_ccy1 VARCHAR(10),
    ccy1 VARCHAR(10),
    ccy1_amount DECIMAL(20, 2),
    bs_ccy2 VARCHAR(10),
    ccy2 VARCHAR(10),
    ccy2_amount DECIMAL(20, 2),
    rate DECIMAL(10, 6),
    channel VARCHAR(50),
    trade_time TIME,
    sort_id INTEGER REFERENCES, 
	payment_status VARCHAR(20),
	journal_status VARCHAR(20),
	journal_sequence VARCHAR(50),
	order_id VARCHAR(50),
	stream_No  VARCHAR(50),
    created_by VARCHAR(50),
    created_time TIMESTAMP,
    updated_by VARCHAR(50),
    updated_time TIMESTAMP
);

-- 添加表注释
COMMENT ON TABLE tms_fx IS '外汇交易表，记录外汇交易相关信息';

-- 添加字段注释
COMMENT ON COLUMN tms_fx.trade_number IS '交易编号，用于唯一标识每笔外汇交易';
COMMENT ON COLUMN tms_fx.counterpart IS '交易对手方，外汇交易中的另一方';
COMMENT ON COLUMN tms_fx.currency_pair IS '货币对，外汇交易涉及的两种货币组合，如美元兑欧元（USD/EUR）';
COMMENT ON COLUMN tms_fx.value_date IS '起息日，外汇交易资金实际生效日期';
COMMENT ON COLUMN tms_fx.bs_ccy1 IS '货币1的买卖标识，表明对第一种货币是买入还是卖出操作';
COMMENT ON COLUMN tms_fx.ccy1 IS '第一种货币，外汇交易中的基础货币';
COMMENT ON COLUMN tms_fx.ccy1_amount IS '第一种货币的交易金额';
COMMENT ON COLUMN tms_fx.bs_ccy2 IS '货币2的买卖标识，表明对第二种货币是买入还是卖出操作';
COMMENT ON COLUMN tms_fx.ccy2 IS '第二种货币，外汇交易中的报价货币';
COMMENT ON COLUMN tms_fx.ccy2_amount IS '第二种货币的交易金额';
COMMENT ON COLUMN tms_fx.rate IS '汇率，两种货币之间的兑换比率';
COMMENT ON COLUMN tms_fx.channel IS '交易渠道，外汇交易通过的途径';
COMMENT ON COLUMN tms_fx.trade_time IS '交易时间，外汇交易发生的具体时刻';
COMMENT ON COLUMN tms_fx.payment_status IS '付款状态：未付款、付款中、已付款、未收款、已收款';
COMMENT ON COLUMN tms_fx.journal_status IS '制证状态：未制证、待复核、复核通过 & 待上传、复核通过 & 上传失败、复核通过 & 上传成功';
COMMENT ON COLUMN tms_fx.order_id IS '订单号';
COMMENT ON COLUMN tms_fx.stream_No IS '流水号';
COMMENT ON COLUMN tms_fx.journal_sequence IS '凭证编号';
COMMENT ON COLUMN tms_fx.created_by IS '创建人';
COMMENT ON COLUMN tms_fx.created_time IS '创建时间';
COMMENT ON COLUMN tms_fx.updated_by IS '更新人';
COMMENT ON COLUMN tms_fx.updated_time IS '更新时间';

-- 添加索引
CREATE INDEX idx_tms_fx_value_date ON tms_fx(value_date);
CREATE INDEX idx_tms_fx_counterpart ON tms_fx(counterpart);
CREATE INDEX idx_tms_fx_currency_pair ON tms_fx(currency_pair);