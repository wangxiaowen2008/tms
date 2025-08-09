-- 4. TMS Risk Monitoring表
CREATE TABLE tms_risk_monitoring (
    reporting_date DATE,
    remark TEXT,
    isin VARCHAR(20),
    currency VARCHAR(10),
    price DECIMAL(20, 4),
    sum_of_hkd_amount DECIMAL(20, 2),
    sum_of_notional_in_base_currency DECIMAL(20, 2),
    maturity_date DATE,
    price_bgn DECIMAL(20, 4),
    price_bval DECIMAL(20, 4),
    price_bchk DECIMAL(20, 4),
    final_price DECIMAL(20, 4),
    market_value DECIMAL(20, 2),
    modified_duration DECIMAL(10, 4),
    oas_spread_dur_mid DECIMAL(10, 4),
    dv01 DECIMAL(10, 4),
    cs01 DECIMAL(10, 4),
    dtd_percent_change DECIMAL(10, 4),
    mtd_percent_change DECIMAL(10, 4),
    ytd_percent_change DECIMAL(10, 4),
    created_by VARCHAR(50),
    created_time TIMESTAMP,
    updated_by VARCHAR(50),
    updated_time TIMESTAMP,
    PRIMARY KEY (isin, reporting_date)
);

-- 添加表注释
COMMENT ON TABLE tms_risk_monitoring IS '风险监控表，记录交易风险监控相关信息';

-- 添加字段注释
COMMENT ON COLUMN tms_risk_monitoring.reporting_date IS '报告日期，相关数据生成报告的日期';
COMMENT ON COLUMN tms_risk_monitoring.remark IS '备注，用于记录相关的额外说明或信息';
COMMENT ON COLUMN tms_risk_monitoring.isin IS '国际证券识别码，用于唯一标识证券';
COMMENT ON COLUMN tms_risk_monitoring.currency IS '货币种类，交易或数据涉及的货币类型';
COMMENT ON COLUMN tms_risk_monitoring.price IS '价格，证券或交易资产的价格';
COMMENT ON COLUMN tms_risk_monitoring.sum_of_hkd_amount IS '港元金额总和，以港元计量的相关金额总计';
COMMENT ON COLUMN tms_risk_monitoring.sum_of_notional_in_base_currency IS '基础货币名义金额总和，以基础货币计量的名义金额总计';
COMMENT ON COLUMN tms_risk_monitoring.maturity_date IS '到期日，金融工具或交易结束的日期';
COMMENT ON COLUMN tms_risk_monitoring.price_bgn IS 'BGN计价的价格，可能是按保加利亚列弗（BGN）计算的价格';
COMMENT ON COLUMN tms_risk_monitoring.price_bval IS 'BVAL计价的价格，具体含义需结合业务背景确定计价方式';
COMMENT ON COLUMN tms_risk_monitoring.price_bchk IS 'BCHK计价的价格，具体计价相关的含义需结合业务背景';
COMMENT ON COLUMN tms_risk_monitoring.final_price IS '最终价格，交易或资产确定的最终成交价格';
COMMENT ON COLUMN tms_risk_monitoring.market_value IS '市场价值，资产在市场上的估值';
COMMENT ON COLUMN tms_risk_monitoring.modified_duration IS '修正久期，衡量债券价格对利率变动敏感性的指标';
COMMENT ON COLUMN tms_risk_monitoring.oas_spread_dur_mid IS '期权调整利差久期（中间值），用于分析含权债券利率风险相关指标';
COMMENT ON COLUMN tms_risk_monitoring.dv01 IS '基点价值，利率变动一个基点时债券价值的变动量';
COMMENT ON COLUMN tms_risk_monitoring.cs01 IS '可能是某种风险敏感度指标，具体需结合业务场景确定';
COMMENT ON COLUMN tms_risk_monitoring.dtd_percent_change IS '当日至今（Day-To-Date）百分比变动，从当天开始到当前的变动比例';
COMMENT ON COLUMN tms_risk_monitoring.mtd_percent_change IS '当月至今（Month-To-Date）百分比变动，从本月开始到当前的变动比例';
COMMENT ON COLUMN tms_risk_monitoring.ytd_percent_change IS '年初至今（Year-To-Date）百分比变动，从年初开始到当前的变动比例';
COMMENT ON COLUMN tms_risk_monitoring.created_by IS '创建人';
COMMENT ON COLUMN tms_risk_monitoring.created_time IS '创建时间';
COMMENT ON COLUMN tms_risk_monitoring.updated_by IS '更新人';
COMMENT ON COLUMN tms_risk_monitoring.updated_time IS '更新时间';

-- 添加索引
CREATE INDEX idx_tms_risk_monitoring_reporting_date ON tms_risk_monitoring(reporting_date);
CREATE INDEX idx_tms_risk_monitoring_isin ON tms_risk_monitoring(isin);
CREATE INDEX idx_tms_risk_monitoring_currency ON tms_risk_monitoring(currency);
CREATE INDEX idx_tms_risk_monitoring_maturity_date ON tms_risk_monitoring(maturity_date);

-- 创建自动更新updated_time的触发器函数（如果尚未创建）
CREATE OR REPLACE FUNCTION update_updated_time_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_time = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 为tms_risk_monitoring表创建触发器
CREATE TRIGGER update_tms_risk_monitoring_updated_time
BEFORE UPDATE ON tms_risk_monitoring
FOR EACH ROW
EXECUTE FUNCTION update_updated_time_column(); 