CREATE TABLE tms_fps_dw_repo (
    trade_number VARCHAR(50) PRIMARY KEY,
    counterpart VARCHAR(100),
    type_of_facility DECIMAL(10, 4),
    repo_rate DECIMAL(10, 4),
    currency VARCHAR(10),
    amount DECIMAL(20, 2),
    trade_date DATE,
    value_date DATE,
    repurchase_date DATE,
    collateral VARCHAR(200),
    interest DECIMAL(20, 2),
    channel VARCHAR(50),
    trade_time TIME,
    sort_id INTEGER REFERENCES, 
    payment_status VARCHAR(20),
	journal_status VARCHAR(20),
	journal_sequence VARCHAR(50),
    created_by VARCHAR(50),
    created_time TIMESTAMP,
    updated_by VARCHAR(50),
    updated_time TIMESTAMP
);

-- 添加表注释
COMMENT ON TABLE tms_fps_dw_repo IS '回购交易表，记录回购交易相关信息';

-- 添加字段注释
COMMENT ON COLUMN tms_fps_dw_repo.trade_number IS '交易编号，用于唯一标识每笔回购交易';
COMMENT ON COLUMN tms_fps_dw_repo.counterpart IS '交易对手方，进行回购交易的另一方';
COMMENT ON COLUMN tms_fps_dw_repo.type_of_facility IS '融资工具类型，回购交易中融资安排的类型';
COMMENT ON COLUMN tms_fps_dw_repo.repo_rate IS '回购利率，回购协议中资金的利率';
COMMENT ON COLUMN tms_fps_dw_repo.currency IS '货币种类，回购交易涉及的货币';
COMMENT ON COLUMN tms_fps_dw_repo.amount IS '交易金额，回购交易的资金数额';
COMMENT ON COLUMN tms_fps_dw_repo.trade_date IS '交易日期，达成回购协议的日期';
COMMENT ON COLUMN tms_fps_dw_repo.value_date IS '起息日，回购交易资金开始计算利息的日期';
COMMENT ON COLUMN tms_fps_dw_repo.repurchase_date IS '回购日期，在回购协议中约定的资金偿还、证券赎回日期';
COMMENT ON COLUMN tms_fps_dw_repo.collateral IS '抵押物，进行回购交易时提供的担保资产';
COMMENT ON COLUMN tms_fps_dw_repo.interest IS '利息金额，根据交易金额、利率等计算得出的利息';
COMMENT ON COLUMN tms_fps_dw_repo.channel IS '交易渠道，通过何种途径进行的回购交易';
COMMENT ON COLUMN tms_fps_dw_repo.trade_time IS '交易时间，回购交易发生的具体时刻';
COMMENT ON COLUMN tms_fps_dw_repo.payment_status IS '付款状态：未付款、付款中、已付款、未收款、已收款';
COMMENT ON COLUMN tms_fps_dw_repo.journal_status IS '制证状态：未制证、待复核、复核通过 & 待上传、复核通过 & 上传失败、复核通过 & 上传成功';
COMMENT ON COLUMN tms_fps_dw_repo.journal_sequence IS '凭证编号';
COMMENT ON COLUMN tms_fps_dw_repo.created_by IS '创建人';
COMMENT ON COLUMN tms_fps_dw_repo.created_time IS '创建时间';
COMMENT ON COLUMN tms_fps_dw_repo.updated_by IS '更新人';
COMMENT ON COLUMN tms_fps_dw_repo.updated_time IS '更新时间';

-- 添加索引
CREATE INDEX idx_tms_fps_dw_repo_trade_date ON tms_fps_dw_repo(trade_date);
CREATE INDEX idx_tms_fps_dw_repo_counterpart ON tms_fps_dw_repo(counterpart);
CREATE INDEX idx_tms_fps_dw_repo_currency ON tms_fps_dw_repo(currency);

