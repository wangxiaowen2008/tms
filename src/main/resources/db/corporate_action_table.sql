-- 创建公司行为表
CREATE TABLE tms_corporate_action (
    id bigserial PRIMARY KEY,
    action_no varchar(20) NOT NULL,
    trade_numbers text NOT NULL,
    entity_id varchar(50) NOT NULL,
    counterpart varchar(100) NOT NULL,
    action_type varchar(20) NOT NULL,
    currency varchar(10) NOT NULL,
    settlement_amount numeric(20,4) NOT NULL,
    value_date date NOT NULL,
    remark varchar(50) NOT NULL,
    debt_security_name varchar(200) NOT NULL,
    isin varchar(20) NOT NULL,
    settlement_account varchar(50) NOT NULL,
    broker varchar(100) NOT NULL,
    payment_status varchar(20) NOT NULL DEFAULT '未收款',
    journal_status varchar(20) NOT NULL DEFAULT '未制证',
    journal_Sequence varchar(50),
    order_id VARCHAR(50),
    stream_No  VARCHAR(50),
    principal_amount numeric(20,4) NOT NULL DEFAULT 0,
    interest_amount numeric(20,4) NOT NULL DEFAULT 0,
    total_amount numeric(20,4) NOT NULL DEFAULT 0,
    alloc_prem_disc  NUMERIC(20,4) ,
    accrued_interest NUMERIC(20,4) ,
    accrued_valuation_change NUMERIC(20,4) ,
    create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    create_by varchar(50),
    update_by varchar(50)
);

-- 添加表注释
COMMENT ON TABLE tms_corporate_action IS '公司行为表';
COMMENT ON COLUMN tms_corporate_action.id IS '主键ID';
COMMENT ON COLUMN tms_corporate_action.action_no IS '公司行为编号，格式：EFB+5位序号，如EFB00001';
COMMENT ON COLUMN tms_corporate_action.trade_numbers IS 'Trade Number，多条记录用换行符分隔';
COMMENT ON COLUMN tms_corporate_action.entity_id IS 'Entity ID';
COMMENT ON COLUMN tms_corporate_action.counterpart IS 'Counterpart';
COMMENT ON COLUMN tms_corporate_action.action_type IS '公司行为类型：到期/利息';
COMMENT ON COLUMN tms_corporate_action.currency IS '货币';
COMMENT ON COLUMN tms_corporate_action.settlement_amount IS '结算金额';
COMMENT ON COLUMN tms_corporate_action.value_date IS '起息日';
COMMENT ON COLUMN tms_corporate_action.remark IS '备注，如EFB';
COMMENT ON COLUMN tms_corporate_action.debt_security_name IS '债券名称';
COMMENT ON COLUMN tms_corporate_action.isin IS 'ISIN代码';
COMMENT ON COLUMN tms_corporate_action.settlement_account IS '结算账户';
COMMENT ON COLUMN tms_corporate_action.broker IS '经纪商';
COMMENT ON COLUMN tms_corporate_action.payment_status IS '付款状态：未收款/已收款';
COMMENT ON COLUMN tms_corporate_action.journal_status IS '制证状态：未制证/已制证';
COMMENT ON COLUMN tms_corporate_action.journal_Sequence IS '凭证编号';
COMMENT ON COLUMN tms_corporate_action.principal_amount IS '本金金额';
COMMENT ON COLUMN tms_corporate_action.interest_amount IS '利息金额';
COMMENT ON COLUMN tms_corporate_action.total_amount IS '本金+利息总金额';
COMMENT ON COLUMN tms_corporate_action.accrued_interest IS '已计提利息';
COMMENT ON COLUMN tms_corporate_action.alloc_prem_disc IS '已分摊溢折价';
COMMENT ON COLUMN tms_corporate_action.accrued_valuation_change IS '已计提估值变动';
COMMENT ON COLUMN tms_corporate_action.order_id IS '订单号';
COMMENT ON COLUMN tms_corporate_action.stream_No IS '流水号';
COMMENT ON COLUMN tms_corporate_action.create_time IS '创建时间';
COMMENT ON COLUMN tms_corporate_action.update_time IS '更新时间';
COMMENT ON COLUMN tms_corporate_action.create_by IS '创建人';
COMMENT ON COLUMN tms_corporate_action.update_by IS '更新人';

-- 创建唯一索引
CREATE UNIQUE INDEX uk_action_no ON tms_corporate_action (action_no);

-- 创建普通索引
CREATE INDEX idx_trade_numbers ON tms_corporate_action (trade_numbers);
CREATE INDEX idx_entity_id ON tms_corporate_action (entity_id);
CREATE INDEX idx_value_date ON tms_corporate_action (value_date);
CREATE INDEX idx_payment_status ON tms_corporate_action (payment_status);
CREATE INDEX idx_voucher_status ON tms_corporate_action (voucher_status);

-- 创建公司行为编号序列表
CREATE TABLE tms_corporate_action_seq (
    id bigserial PRIMARY KEY,
    action_type varchar(20) NOT NULL,
    current_value integer NOT NULL DEFAULT 0,
    create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 添加表注释
COMMENT ON TABLE tms_corporate_action_seq IS '公司行为编号序列表';
COMMENT ON COLUMN tms_corporate_action_seq.id IS '主键ID';
COMMENT ON COLUMN tms_corporate_action_seq.action_type IS '公司行为类型';
COMMENT ON COLUMN tms_corporate_action_seq.current_value IS '当前序号';
COMMENT ON COLUMN tms_corporate_action_seq.create_time IS '创建时间';
COMMENT ON COLUMN tms_corporate_action_seq.update_time IS '更新时间';

-- 创建唯一索引
CREATE UNIQUE INDEX uk_action_type ON tms_corporate_action_seq (action_type);

-- 初始化EFB序列
INSERT INTO tms_corporate_action_seq (action_type, current_value) VALUES ('EFB', 0);

-- 创建更新时间的触发器函数
CREATE OR REPLACE FUNCTION update_modified_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.update_time = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- 为两个表创建触发器
CREATE TRIGGER update_tms_corporate_action_modtime
    BEFORE UPDATE ON tms_corporate_action
    FOR EACH ROW
    EXECUTE FUNCTION update_modified_column();

CREATE TRIGGER update_tms_corporate_action_seq_modtime
    BEFORE UPDATE ON tms_corporate_action_seq
    FOR EACH ROW
    EXECUTE FUNCTION update_modified_column(); 