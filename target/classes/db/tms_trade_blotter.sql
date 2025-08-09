-- 1. TMS Trade Blotter表
CREATE TABLE tms_trade_blotter (
    trade_number VARCHAR(50) PRIMARY KEY,
    item INTEGER,
    entity_id VARCHAR(50),
    counterpart VARCHAR(100),
    buy_sell_borrow_lend VARCHAR(20),
    yield DECIMAL(10, 4),
    currency VARCHAR(10),
    settlement_amount DECIMAL(20, 2),
    trade_date DATE,
    value_date DATE,
    maturity_date DATE,
    remark TEXT,
    interest DECIMAL(20, 2),
    price DECIMAL(20, 4),
    debt_security_name VARCHAR(200),
    isin VARCHAR(20),
    face_amount DECIMAL(20, 2),
    amount_received_at_maturity DECIMAL(20, 2),
    interest_paid_upfront DECIMAL(20, 2),
    net_interest DECIMAL(20, 2),
    coupon_type VARCHAR(20),
    coupon_frequent VARCHAR(50),
    channel VARCHAR(50),
    settlement_account VARCHAR(100),
    broker VARCHAR(100),
    trade_time TIME,
    interest_accrual_date DATE,
    full_name VARCHAR(200),
    full_name_2 VARCHAR(200),
    branch VARCHAR(100),
    branch2 VARCHAR(100),
    name VARCHAR(200),
    series VARCHAR(50),
    match VARCHAR(50),
    maturity VARCHAR(50),
    group_parent VARCHAR(100),
    hkd_amount DECIMAL(20, 2),
    usd_amount DECIMAL(20, 2),
    trade_date_vs_value_date INTEGER,
    english_name VARCHAR(200),
    ring_fenced VARCHAR(50),
    days_to_last_month_end INTEGER,
    days_to_maturity INTEGER,
    tenor_bucket VARCHAR(50),
    ir_tenor_bucket VARCHAR(50),
    holdings_period VARCHAR(50),
    rank VARCHAR(50),
    interest_in_hkd DECIMAL(20, 2),
    notional_in_base_currency DECIMAL(20, 2),
    type VARCHAR(50),
    boc VARCHAR(50),
    scb VARCHAR(50),
    hsbc VARCHAR(50),
    ccba VARCHAR(50),
    des VARCHAR(50),
    max_diff DECIMAL(10, 4),
    auction_ave DECIMAL(20, 4),
    auction_high DECIMAL(20, 4),
    t_day DATE,
    trade_no_old VARCHAR(50),
    day_count_2 INTEGER,
    country_of_domicile_hd VARCHAR(100),
    country_of_domicile VARCHAR(100),
    bbg_issued_amount_hd DECIMAL(20, 2),
    bbg_issued_amount DECIMAL(20, 2),
    bbg_private_placement_hd VARCHAR(100),
    bbg_private_placement VARCHAR(100),
    next_coupon_date_hc DATE,
    next_coupon_date DATE,
    issuer_rating VARCHAR(50),
    parent_id VARCHAR(50),
    issuer_country VARCHAR(100),
    country_of_incorporation VARCHAR(100),
    report_date DATE,
    risk_report_date DATE,
    face_amount_in_hkd DECIMAL(20, 2),
    face_amount_in_usd DECIMAL(20, 2),
    country_rating VARCHAR(50),
    instrument VARCHAR(100),
    asset_type_rank VARCHAR(50),
    booking VARCHAR(100),
    month_end_booking_check VARCHAR(50),
    next_interest DECIMAL(20, 2),
	payment_status VARCHAR(20),
	journal_status VARCHAR(20),
	journal_sequence VARCHAR(50),
	order_id VARCHAR(50),
	stream_No  VARCHAR(50),
	exist_Company VARCHAR(1),
	exist_accrual VARCHAR(1),
    created_by VARCHAR(50),
    created_time TIMESTAMP,
    updated_by VARCHAR(50),
    updated_time TIMESTAMP
);

-- 添加表注释
COMMENT ON TABLE tms_trade_blotter IS '交易记录表，记录所有交易相关的详细信息';

-- 添加字段注释
COMMENT ON COLUMN tms_trade_blotter.trade_number IS '交易编号，唯一标识每笔交易';
COMMENT ON COLUMN tms_trade_blotter.item IS '项目编号，用于标识具体交易项目';
COMMENT ON COLUMN tms_trade_blotter.entity_id IS '实体编号，代表交易主体的编号';
COMMENT ON COLUMN tms_trade_blotter.counterpart IS '交易对手方，即与本机构进行交易的另一方';
COMMENT ON COLUMN tms_trade_blotter.buy_sell_borrow_lend IS '买卖/借贷标识，表明交易行为是买入、卖出、借入还是贷出';
COMMENT ON COLUMN tms_trade_blotter.yield IS '收益率，投资或交易产生的收益比例';
COMMENT ON COLUMN tms_trade_blotter.currency IS '货币种类，交易涉及的货币';
COMMENT ON COLUMN tms_trade_blotter.settlement_amount IS '结算金额，交易最终结算的资金数额';
COMMENT ON COLUMN tms_trade_blotter.trade_date IS '交易日期，达成交易的日期';
COMMENT ON COLUMN tms_trade_blotter.value_date IS '起息日，资金开始计算利息的日期';
COMMENT ON COLUMN tms_trade_blotter.maturity_date IS '到期日，金融工具或交易结束的日期';
COMMENT ON COLUMN tms_trade_blotter.remark IS '备注，记录交易相关的额外说明信息';
COMMENT ON COLUMN tms_trade_blotter.day_count IS '天数计算，用于计算利息等的天数计量';
COMMENT ON COLUMN tms_trade_blotter.interest IS '利息金额，交易产生的利息数额';
COMMENT ON COLUMN tms_trade_blotter.price IS '价格，交易资产的价格';
COMMENT ON COLUMN tms_trade_blotter.debt_security_name IS '债务证券名称，交易涉及的债务证券具体名称';
COMMENT ON COLUMN tms_trade_blotter.isin IS '国际证券识别码，用于唯一标识证券';
COMMENT ON COLUMN tms_trade_blotter.face_amount IS '面值，证券的票面金额';
COMMENT ON COLUMN tms_trade_blotter.amount_received_at_maturity IS '到期收到金额，到期时获得的资金数额';
COMMENT ON COLUMN tms_trade_blotter.interest_paid_upfront IS '预付利息，交易前期支付的利息';
COMMENT ON COLUMN tms_trade_blotter.net_interest IS '净利息，扣除相关费用后的利息净额';
COMMENT ON COLUMN tms_trade_blotter.coupon_type IS '息票类型：零息/固定，证券利息支付的类型';
COMMENT ON COLUMN tms_trade_blotter.coupon_frequent IS '息票支付频率，利息支付的时间间隔';
COMMENT ON COLUMN tms_trade_blotter.channel IS '交易渠道，通过何种途径进行的交易';
COMMENT ON COLUMN tms_trade_blotter.settlement_account IS '结算账户，用于资金结算的账户';
COMMENT ON COLUMN tms_trade_blotter.broker IS '经纪人，促成交易的中介机构或个人';
COMMENT ON COLUMN tms_trade_blotter.trade_time IS '交易时间，精确到具体时刻';
COMMENT ON COLUMN tms_trade_blotter.interest_accrual_date IS '利息accrual日期，计算利息的起始日期';
COMMENT ON COLUMN tms_trade_blotter.full_name IS '全称，交易主体或证券等的完整名称';
COMMENT ON COLUMN tms_trade_blotter.full_name_2 IS '第二全称，可能用于补充或关联的另一主体名称';
COMMENT ON COLUMN tms_trade_blotter.branch IS '分支机构，交易主体的分支机构信息';
COMMENT ON COLUMN tms_trade_blotter.branch2 IS '第二分支机构，可能用于补充的另一分支机构信息';
COMMENT ON COLUMN tms_trade_blotter.name IS '名称，交易相关主体或项目名称';
COMMENT ON COLUMN tms_trade_blotter.series IS '系列，证券或交易的系列编号';
COMMENT ON COLUMN tms_trade_blotter.match IS '匹配标识，可能用于交易匹配等相关操作';
COMMENT ON COLUMN tms_trade_blotter.maturity IS '到期期限，金融工具距离到期的时间';
COMMENT ON COLUMN tms_trade_blotter.group_parent IS '集团母公司，交易主体所属集团的母公司';
COMMENT ON COLUMN tms_trade_blotter.hkd_amount IS '港元金额，以港元计量的交易金额';
COMMENT ON COLUMN tms_trade_blotter.usd_amount IS '美元金额，以美元计量的交易金额';
COMMENT ON COLUMN tms_trade_blotter.trade_date_vs_value_date IS '交易日期与起息日对比，用于分析两者时间差等情况';
COMMENT ON COLUMN tms_trade_blotter.english_name IS '英文名称，交易主体或证券等的英文名称';
COMMENT ON COLUMN tms_trade_blotter.ring_fenced IS '隔离标识，可能用于标识资金或资产的隔离状态';
COMMENT ON COLUMN tms_trade_blotter.days_to_last_month_end IS '距离上月末天数，距离上个月月底的天数';
COMMENT ON COLUMN tms_trade_blotter.days_to_maturity IS '距离到期天数，距离金融工具到期的天数';
COMMENT ON COLUMN tms_trade_blotter.tenor_bucket IS '期限区间，金融工具按期限划分的区间';
COMMENT ON COLUMN tms_trade_blotter.ir_tenor_bucket IS '利率期限区间，按利率期限划分的区间';
COMMENT ON COLUMN tms_trade_blotter.holdings_period IS '持有期限，持有金融工具的时间长度';
COMMENT ON COLUMN tms_trade_blotter.rank IS '排名，交易或资产的某种排名情况';
COMMENT ON COLUMN tms_trade_blotter.interest_in_hkd IS '港元利息，以港元计算的利息金额';
COMMENT ON COLUMN tms_trade_blotter.notional_in_base_currency IS '基础货币名义金额，以基础货币计量的名义金额';
COMMENT ON COLUMN tms_trade_blotter.type IS '类型，交易或资产的类型';
COMMENT ON COLUMN tms_trade_blotter.boc IS '中国银行（Bank of China）相关标识或信息';
COMMENT ON COLUMN tms_trade_blotter.scb IS '可能是某银行或金融机构缩写，具体需结合业务背景';
COMMENT ON COLUMN tms_trade_blotter.hsbc IS '汇丰银行（The Hongkong and Shanghai Banking Corporation Limited）相关标识或信息';
COMMENT ON COLUMN tms_trade_blotter.ccba IS '中国银行业协会（China Banking Association）相关标识或信息';
COMMENT ON COLUMN tms_trade_blotter.des IS '可能是某特定业务或系统缩写，需结合业务背景';
COMMENT ON COLUMN tms_trade_blotter.max_diff IS '最大差异，两个数值间的最大差异比例';
COMMENT ON COLUMN tms_trade_blotter.auction_ave IS '拍卖平均价，拍卖交易中的平均价格';
COMMENT ON COLUMN tms_trade_blotter.auction_high IS '拍卖最高价，拍卖交易中的最高价格';
COMMENT ON COLUMN tms_trade_blotter.t_day IS '交易日（通常指交易执行当天）';
COMMENT ON COLUMN tms_trade_blotter.trade_no_old IS '旧交易编号，可能用于历史交易关联';
COMMENT ON COLUMN tms_trade_blotter.day_count_2 IS '天数计算，用于利息等计算的天数计量';
COMMENT ON COLUMN tms_trade_blotter.country_of_domicile_hd IS '注册地国家（特定业务相关，HD可能为业务标识）';
COMMENT ON COLUMN tms_trade_blotter.country_of_domicile IS '注册地国家，交易主体或证券的注册所在国家';
COMMENT ON COLUMN tms_trade_blotter.bbg_issued_amount_hd IS '彭博（BBG）统计的发行金额（特定业务相关，HD可能为业务标识）';
COMMENT ON COLUMN tms_trade_blotter.bbg_issued_amount IS '彭博统计的发行金额';
COMMENT ON COLUMN tms_trade_blotter.bbg_private_placement_hd IS '彭博统计的私募金额（特定业务相关，HD可能为业务标识）';
COMMENT ON COLUMN tms_trade_blotter.bbg_private_placement IS '彭博统计的私募金额';
COMMENT ON COLUMN tms_trade_blotter.next_coupon_date_hc IS '下次息票支付日期（特定业务相关，HC可能为业务标识）';
COMMENT ON COLUMN tms_trade_blotter.next_coupon_date IS '下次息票支付日期';
COMMENT ON COLUMN tms_trade_blotter.issuer_rating IS '发行人评级，对证券发行人信用等的评级';
COMMENT ON COLUMN tms_trade_blotter.parent_id IS '母公司编号，交易主体母公司的编号';
COMMENT ON COLUMN tms_trade_blotter.issuer_country IS '发行人国家，证券发行人所在国家';
COMMENT ON COLUMN tms_trade_blotter.country_of_incorporation IS '成立国家，交易主体或证券成立的国家';
COMMENT ON COLUMN tms_trade_blotter.report_date IS '报告日期，相关报告生成的日期';
COMMENT ON COLUMN tms_trade_blotter.risk_report_date IS '风险报告日期，风险相关报告的日期';
COMMENT ON COLUMN tms_trade_blotter.face_amount_in_hkd IS '港元面值，以港元计量的证券票面金额';
COMMENT ON COLUMN tms_trade_blotter.face_amount_in_usd IS '美元面值，以美元计量的证券票面金额';
COMMENT ON COLUMN tms_trade_blotter.country_rating IS '国家评级，对相关国家信用等的评级';
COMMENT ON COLUMN tms_trade_blotter.instrument IS '金融工具，交易涉及的具体金融工具类型';
COMMENT ON COLUMN tms_trade_blotter.asset_type_rank IS '资产类型排名，按资产类型划分的排名';
COMMENT ON COLUMN tms_trade_blotter.booking IS '记账，交易记账相关信息';
COMMENT ON COLUMN tms_trade_blotter.month_end_booking_check IS '月末记账检查，月末对交易记账的检查操作';
COMMENT ON COLUMN tms_trade_blotter.next_interest IS '下次利息，下一次支付或计算的利息金额';
COMMENT ON COLUMN tms_trade_blotter.payment_status IS '付款状态：未付款、付款中、已付款、未收款、已收款';
COMMENT ON COLUMN tms_trade_blotter.journal_status IS '制证状态：未制证、待复核、复核通过 & 待上传、复核通过 & 上传失败、复核通过 & 上传成功';
COMMENT ON COLUMN tms_trade_blotter.journal_sequence IS '凭证编号';
COMMENT ON COLUMN tms_trade_blotter.order_id IS '订单号';
COMMENT ON COLUMN tms_trade_blotter.stream_No IS '流水号';
COMMENT ON COLUMN tms_trade_blotter.exist_Company IS '是否存在公司行为数据标识';
COMMENT ON COLUMN tms_trade_blotter.exist_accrual IS '是否存在计提数据标识';
COMMENT ON COLUMN tms_trade_blotter.created_by IS '创建人';
COMMENT ON COLUMN tms_trade_blotter.created_time IS '创建时间';
COMMENT ON COLUMN tms_trade_blotter.updated_by IS '更新人';
COMMENT ON COLUMN tms_trade_blotter.updated_time IS '更新时间';

-- 添加索引
CREATE INDEX idx_tms_trade_blotter_trade_date ON tms_trade_blotter(trade_date);
CREATE INDEX idx_tms_trade_blotter_counterpart ON tms_trade_blotter(counterpart);
CREATE INDEX idx_tms_trade_blotter_currency ON tms_trade_blotter(currency);




