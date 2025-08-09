-- 创建计提数据表
CREATE TABLE tms_accrual (
    id BIGSERIAL PRIMARY KEY,
    accrual_date DATE NOT NULL,
    remark VARCHAR(50) NOT NULL,
    trade_number VARCHAR(50) NOT NULL,
    entity_id VARCHAR(50) ,
    counterpart VARCHAR(100) ,
    currency VARCHAR(10) ,
    price NUMERIC(20,6) ,
    sum_of_hkd_amount NUMERIC(20,2) ,
    sum_of_notional_in_base_currency NUMERIC(20,2) ,
    market_Value  NUMERIC(20,2) ,
    debt_security_name VARCHAR(100),
    isin VARCHAR(50),
    daily_accrued_interest NUMERIC(20,4) ,
    daily_alloc_prem_disc  NUMERIC(20,4) ,
    alloc_prem_disc  NUMERIC(20,4) ,
    no_alloc_prem_disc  NUMERIC(20,4) ,
    accrued_interest NUMERIC(20,4) ,
    interest_journal_status VARCHAR(50)  DEFAULT '0',
    interest_journal_no VARCHAR(50),
    alloc_prem_disc_journal_status  VARCHAR(50)  DEFAULT '0',
    alloc_prem_disc_journal_no  VARCHAR(50),
    daily_valuation_change NUMERIC(20,4) ,
    accrued_valuation_change NUMERIC(20,4) ,
    valuation_journal_status VARCHAR(50)  DEFAULT '0',
    valuation_journal_no VARCHAR(50),
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50) NOT NULL,
    updated_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 添加注释
COMMENT ON TABLE tms_accrual IS '计提数据表';
COMMENT ON COLUMN tms_accrual.id IS '主键ID';
COMMENT ON COLUMN tms_accrual.accrual_date IS '日期';
COMMENT ON COLUMN tms_accrual.remark IS '备注类型：Bond、CD、EFB、UST、Interbank、Fixed Deposit';
COMMENT ON COLUMN tms_accrual.trade_number IS '交易编号';
COMMENT ON COLUMN tms_accrual.entity_id IS 'Entity ID';
COMMENT ON COLUMN tms_accrual.counterpart IS '交易对手方';
COMMENT ON COLUMN tms_accrual.currency IS '币种';
COMMENT ON COLUMN tms_accrual.price IS '价格';
COMMENT ON COLUMN tms_accrual.sum_of_hkd_amount IS 'HKD金额汇总';
COMMENT ON COLUMN tms_accrual.sum_of_notional_in_base_currency IS '基础货币面值汇总';
COMMENT ON COLUMN tms_accrual.market_Value IS '最终价格';
COMMENT ON COLUMN tms_accrual.debt_security_name IS '债务证券名称';
COMMENT ON COLUMN tms_accrual.isin IS 'ISIN代码';
COMMENT ON COLUMN tms_accrual.daily_accrued_interest IS '当日应计利息';
COMMENT ON COLUMN tms_accrual.accrued_interest IS '已计提利息';
COMMENT ON COLUMN tms_accrual.interest_journal_status IS '计提利息制证状态';
COMMENT ON COLUMN tms_accrual.interest_journal_no IS '计提利息凭证编号';
COMMENT ON COLUMN tms_accrual.daily_alloc_prem_disc IS '当日分摊溢折价';
COMMENT ON COLUMN tms_accrual.alloc_prem_disc IS '已分摊溢折价';
COMMENT ON COLUMN tms_accrual.no_alloc_prem_disc IS '未分摊溢折价';
COMMENT ON COLUMN tms_accrual.alloc_prem_disc_journal_status IS '分摊溢折价制证状态';
COMMENT ON COLUMN tms_accrual.alloc_prem_disc_journal_no IS '分摊溢折价凭证编号';
COMMENT ON COLUMN tms_accrual.daily_valuation_change IS '当日估值变动';
COMMENT ON COLUMN tms_accrual.accrued_valuation_change IS '已计提估值变动';
COMMENT ON COLUMN tms_accrual.valuation_journal_status IS '估值变动制证状态';
COMMENT ON COLUMN tms_accrual.valuation_journal_no IS '估值变动凭证编号';
COMMENT ON COLUMN tms_accrual.created_by IS '创建人';
COMMENT ON COLUMN tms_accrual.created_time IS '创建时间';
COMMENT ON COLUMN tms_accrual.updated_by IS '更新人';
COMMENT ON COLUMN tms_accrual.updated_time IS '更新时间';
