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
    day_count INTEGER,
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
    sort_id serial, 
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
    sort_id serial, 
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
	
-- 创建计提数据表
CREATE TABLE tms_accrual (
    id BIGSERIAL PRIMARY KEY,
    date DATE NOT NULL,
    remark VARCHAR(50) NOT NULL,
    trade_number VARCHAR(50) NOT NULL,
    entity_id VARCHAR(50) NOT NULL,
    counterpart VARCHAR(100) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    price NUMERIC(20,2) NOT NULL,
    sum_of_hkd_amount NUMERIC(20,2) NOT NULL,
    sum_of_notional_in_base_currency NUMERIC(20,2) NOT NULL,
    final_price NUMERIC(20,2) NOT NULL,
    debt_security_name VARCHAR(100),
    isin VARCHAR(50),
    daily_accrued_interest NUMERIC(20,2) NOT NULL,
    accrued_interest NUMERIC(20,2) NOT NULL,
    interest_journal_status VARCHAR(50) NOT NULL DEFAULT '未制证',
    interest_journal_no VARCHAR(50),
    daily_valuation_change NUMERIC(20,2) NOT NULL,
    accrued_valuation_change NUMERIC(20,2) NOT NULL,
    valuation_journal_status VARCHAR(50) NOT NULL DEFAULT '未制证',
    valuation_journal_no VARCHAR(50),
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50) NOT NULL,
    updated_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 创建索引
CREATE INDEX idx_accrual_date ON tms_accrual(date);
CREATE INDEX idx_accrual_trade_number ON tms_accrual(trade_number);
CREATE INDEX idx_accrual_remark ON tms_accrual(remark);
CREATE INDEX idx_accrual_currency ON tms_accrual(currency);
CREATE INDEX idx_accrual_isin ON tms_accrual(isin);
CREATE INDEX idx_accrual_interest_journal_status ON tms_accrual(interest_journal_status);
CREATE INDEX idx_accrual_valuation_journal_status ON tms_accrual(valuation_journal_status);

-- 添加注释
COMMENT ON TABLE tms_accrual IS '计提数据表';
COMMENT ON COLUMN tms_accrual.id IS '主键ID';
COMMENT ON COLUMN tms_accrual.date IS '日期';
COMMENT ON COLUMN tms_accrual.remark IS '备注类型：Bond、CD、EFB、UST、Interbank、Fixed Deposit';
COMMENT ON COLUMN tms_accrual.trade_number IS '交易编号';
COMMENT ON COLUMN tms_accrual.entity_id IS 'Entity ID';
COMMENT ON COLUMN tms_accrual.counterpart IS '交易对手方';
COMMENT ON COLUMN tms_accrual.currency IS '币种';
COMMENT ON COLUMN tms_accrual.price IS '价格';
COMMENT ON COLUMN tms_accrual.sum_of_hkd_amount IS 'HKD金额汇总';
COMMENT ON COLUMN tms_accrual.sum_of_notional_in_base_currency IS '基础货币面值汇总';
COMMENT ON COLUMN tms_accrual.final_price IS '最终价格';
COMMENT ON COLUMN tms_accrual.debt_security_name IS '债务证券名称';
COMMENT ON COLUMN tms_accrual.isin IS 'ISIN代码';
COMMENT ON COLUMN tms_accrual.daily_accrued_interest IS '当日应计利息';
COMMENT ON COLUMN tms_accrual.accrued_interest IS '已计提利息';
COMMENT ON COLUMN tms_accrual.interest_journal_status IS '计提利息制证状态';
COMMENT ON COLUMN tms_accrual.interest_journal_no IS '计提利息凭证编号';
COMMENT ON COLUMN tms_accrual.daily_valuation_change IS '当日估值变动';
COMMENT ON COLUMN tms_accrual.accrued_valuation_change IS '已计提估值变动';
COMMENT ON COLUMN tms_accrual.valuation_journal_status IS '估值变动制证状态';
COMMENT ON COLUMN tms_accrual.valuation_journal_no IS '估值变动凭证编号';
COMMENT ON COLUMN tms_accrual.created_by IS '创建人';
COMMENT ON COLUMN tms_accrual.created_time IS '创建时间';
COMMENT ON COLUMN tms_accrual.updated_by IS '更新人';
COMMENT ON COLUMN tms_accrual.updated_time IS '更新时间';

CREATE TABLE IF NOT EXISTS tms_account_stream (
    id BIGSERIAL PRIMARY KEY,
    account_name VARCHAR(100) NOT NULL,
    account_number VARCHAR(50) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    transaction_type VARCHAR(10) NOT NULL,
    transaction_amount DECIMAL(20, 2) NOT NULL,
    transaction_date DATE NOT NULL,
    account_balance DECIMAL(20, 2),
    check_no VARCHAR(50),
    transaction_remark TEXT,
    voucher_status VARCHAR(20) NOT NULL DEFAULT 'UNPROCESSED',
    is_deleted INT NOT NULL DEFAULT 0,
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50),
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE tms_account_stream IS '账户流水信息表';
COMMENT ON COLUMN tms_account_stream.account_name IS '本方账户名称';
COMMENT ON COLUMN tms_account_stream.account_number IS '本方账号';
COMMENT ON COLUMN tms_account_stream.currency IS '币种';
COMMENT ON COLUMN tms_account_stream.transaction_type IS '收款/付款';
COMMENT ON COLUMN tms_account_stream.transaction_amount IS '流水金额';
COMMENT ON COLUMN tms_account_stream.transaction_date IS '流水日期';
COMMENT ON COLUMN tms_account_stream.account_balance IS '账户余额';
COMMENT ON COLUMN tms_account_stream.check_no IS '匹配号';
COMMENT ON COLUMN tms_account_stream.transaction_remark IS '备注';
COMMENT ON COLUMN tms_account_stream.voucher_status IS '制证状态：UNPROCESSED-未处理，MATCHED-已匹配，VOUCHERED-已制证';
COMMENT ON COLUMN tms_account_stream.is_deleted IS '是否删除：0-未删除，1-已删除';
COMMENT ON COLUMN tms_account_stream.created_by IS '创建人';
COMMENT ON COLUMN tms_account_stream.created_time IS '创建时间';
COMMENT ON COLUMN tms_account_stream.updated_by IS '更新人';
COMMENT ON COLUMN tms_account_stream.updated_time IS '更新时间';


-- 附件信息表
CREATE TABLE IF NOT EXISTS tms_attachment (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    document_name VARCHAR(200) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    upload_by VARCHAR(50) NOT NULL,
    upload_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    attachment_type VARCHAR(50) NOT NULL,
    business_key VARCHAR(50) NOT NULL,
    is_deleted INT NOT NULL DEFAULT 0,
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50),
    updated_time TIMESTAMP,
    CONSTRAINT pk_attachment PRIMARY KEY (id)
);

COMMENT ON TABLE tms_attachment IS '附件信息表';
COMMENT ON COLUMN tms_attachment.id IS '主键ID';
COMMENT ON COLUMN tms_attachment.document_name IS '文档名称';
COMMENT ON COLUMN tms_attachment.file_path IS '附件存放路径';
COMMENT ON COLUMN tms_attachment.upload_by IS '上传人';
COMMENT ON COLUMN tms_attachment.upload_time IS '上传时间';
COMMENT ON COLUMN tms_attachment.attachment_type IS '附件类型';
COMMENT ON COLUMN tms_attachment.business_key IS '业务主键';
COMMENT ON COLUMN tms_attachment.is_deleted IS '是否删除：0-未删除，1-已删除';
COMMENT ON COLUMN tms_attachment.created_by IS '创建人';
COMMENT ON COLUMN tms_attachment.created_time IS '创建时间';
COMMENT ON COLUMN tms_attachment.updated_by IS '更新人';
COMMENT ON COLUMN tms_attachment.updated_time IS '更新时间';


CREATE TABLE tms_cit_journal (
    tms_cit_journal_id VARCHAR(32) DEFAULT gen_random_uuid() PRIMARY KEY,
    tms_posting_rule_id VARCHAR(32),
    category_name VARCHAR(32) NOT NULL,
    journal_sequence VARCHAR(50) NOT NULL,
    effective_date DATE NOT NULL,
    cur_no VARCHAR(3),
    segment1 VARCHAR(25),
    segment2 VARCHAR(25) NOT NULL,
    segment3 VARCHAR(25),
    segment4 VARCHAR(25) NOT NULL,
    segment5 VARCHAR(25),
    segment6 VARCHAR(25) NOT NULL,
    segment7 VARCHAR(25),
    segment8 VARCHAR(25) DEFAULT '000000' NOT NULL,
    segment9 VARCHAR(25) DEFAULT '0000' NOT NULL,
    entered_dr NUMERIC(20, 2) NOT NULL,
    entered_cr NUMERIC(20, 2) NOT NULL,
    accounted_dr NUMERIC(20, 2) NOT NULL,
    accounted_cr NUMERIC(20, 2) NOT NULL,
    p_entered_dr NUMERIC(20, 2) NOT NULL,
    p_entered_cr NUMERIC(20, 2) NOT NULL,
    journal_line_description VARCHAR(1000),
    journal_status BIGINT NOT NULL,
    write_offs VARCHAR(1),
    ready_state VARCHAR(50),
    data_state VARCHAR(2) DEFAULT '0' NOT NULL,
    created_by VARCHAR(45) DEFAULT 'SYSTEM' NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_by VARCHAR(45) DEFAULT 'SYSTEM' NOT NULL,
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    data_source VARCHAR(100),
    data_id VARCHAR(32),
    balance_status VARCHAR(1),
    secur_no VARCHAR(30),
    secur_type VARCHAR(2),
    market_no VARCHAR(10),
    deal_number VARCHAR(100),
    books_no VARCHAR(32),
    segment10 VARCHAR(25) DEFAULT '0000',
    segment11 VARCHAR(25) DEFAULT '0000',
    segment12 VARCHAR(25) DEFAULT '0000',
    segment13 VARCHAR(25) DEFAULT '0000',
    segment14 VARCHAR(25) DEFAULT '0000',
    msg VARCHAR(25) DEFAULT 'HK_SOB',
    attribute7 VARCHAR(500),
    attribute1 VARCHAR(150),
    attribute2 VARCHAR(150),
    attribute3 VARCHAR(150),
    attribute4 VARCHAR(150),
    attribute5 VARCHAR(150),
    attribute6 VARCHAR(150),
    attribute8 VARCHAR(150),
    attribute9 VARCHAR(150),
    attribute10 VARCHAR(150),
    flexible_status VARCHAR(1),
    journal_source VARCHAR(2),
    journal_temp VARCHAR(50),
	journal_name VARCHAR(50),
    check_by VARCHAR(45),
    check_date DATE,
    record_person VARCHAR(45),
    business_id VARCHAR(50)
);
COMMENT ON TABLE tms_cit_journal IS '系统凭证业务表';
COMMENT ON COLUMN tms_cit_journal.tms_cit_journal_id IS '数据库主键';
COMMENT ON COLUMN tms_cit_journal.tms_posting_rule_id IS '制证规则编号(记账凭证必填)';
COMMENT ON COLUMN tms_cit_journal.category_name IS '明晰凭证、手工凭证、导入凭证、其它凭证';
COMMENT ON COLUMN tms_cit_journal.journal_sequence IS '凭证编号';
COMMENT ON COLUMN tms_cit_journal.effective_date IS '制证日期';
COMMENT ON COLUMN tms_cit_journal.cur_no IS '币种，如：RMB/HKD/USD';
COMMENT ON COLUMN tms_cit_journal.segment1 IS '公司段';
COMMENT ON COLUMN tms_cit_journal.segment2 IS '业务段';
COMMENT ON COLUMN tms_cit_journal.segment3 IS '成本中心';
COMMENT ON COLUMN tms_cit_journal.segment4 IS '产品段';
COMMENT ON COLUMN tms_cit_journal.segment5 IS '科目';
COMMENT ON COLUMN tms_cit_journal.segment6 IS '子目';
COMMENT ON COLUMN tms_cit_journal.segment7 IS '备用段1：0000';
COMMENT ON COLUMN tms_cit_journal.segment8 IS '备用段2（关联方）：0000';
COMMENT ON COLUMN tms_cit_journal.segment9 IS '备用段3';
COMMENT ON COLUMN tms_cit_journal.entered_dr IS '借方金额';
COMMENT ON COLUMN tms_cit_journal.entered_cr IS '贷方金额';
COMMENT ON COLUMN tms_cit_journal.accounted_dr IS '本位币借方金额';
COMMENT ON COLUMN tms_cit_journal.accounted_cr IS '本位币贷方金额';
COMMENT ON COLUMN tms_cit_journal.p_entered_dr IS '报告币借金额';
COMMENT ON COLUMN tms_cit_journal.p_entered_cr IS '报告币贷金额';
COMMENT ON COLUMN tms_cit_journal.journal_line_description IS '备注，即摘要';
COMMENT ON COLUMN tms_cit_journal.journal_status IS '1:待上传，2:上传成功';
COMMENT ON COLUMN tms_cit_journal.write_offs IS '冲销状态：Y已冲销';
COMMENT ON COLUMN tms_cit_journal.ready_state IS '就绪凭证编号';
COMMENT ON COLUMN tms_cit_journal.data_state IS '数据状态：Y进入接口表 N还没进入接口表';
COMMENT ON COLUMN tms_cit_journal.created_by IS '创建人';
COMMENT ON COLUMN tms_cit_journal.created_time IS '创建时间';
COMMENT ON COLUMN tms_cit_journal.updated_by IS '修改人';
COMMENT ON COLUMN tms_cit_journal.updated_time IS '修改时间';
COMMENT ON COLUMN tms_cit_journal.data_source IS '制证业务数据来源表';
COMMENT ON COLUMN tms_cit_journal.data_id IS '制证业务数据ID';
COMMENT ON COLUMN tms_cit_journal.balance_status IS '是否进行了科目余额处理';
COMMENT ON COLUMN tms_cit_journal.secur_no IS '证券代码';
COMMENT ON COLUMN tms_cit_journal.secur_type IS '证券类别:1-股票,2-基金,3-债券,4-回购,5-另类金融产品,D-定存';
COMMENT ON COLUMN tms_cit_journal.market_no IS '证券市场';
COMMENT ON COLUMN tms_cit_journal.deal_number IS '交易数量';
COMMENT ON COLUMN tms_cit_journal.segment10 IS '备用段4';
COMMENT ON COLUMN tms_cit_journal.segment11 IS '备用段5';
COMMENT ON COLUMN tms_cit_journal.segment12 IS '备用段6';
COMMENT ON COLUMN tms_cit_journal.segment13 IS '备用段7';
COMMENT ON COLUMN tms_cit_journal.segment14 IS '备用段8';
COMMENT ON COLUMN tms_cit_journal.msg IS '默认值:HK_SOB';
COMMENT ON COLUMN tms_cit_journal.attribute7 IS '备用字段';
COMMENT ON COLUMN tms_cit_journal.attribute1 IS '备用字段';
COMMENT ON COLUMN tms_cit_journal.attribute2 IS '备用字段';
COMMENT ON COLUMN tms_cit_journal.attribute3 IS '备用字段';
COMMENT ON COLUMN tms_cit_journal.attribute4 IS '备用字段';
COMMENT ON COLUMN tms_cit_journal.attribute5 IS '备用字段';
COMMENT ON COLUMN tms_cit_journal.attribute6 IS '备用字段';
COMMENT ON COLUMN tms_cit_journal.attribute8 IS '备用字段';
COMMENT ON COLUMN tms_cit_journal.attribute9 IS '备用字段';
COMMENT ON COLUMN tms_cit_journal.attribute10 IS '备用字段';
COMMENT ON COLUMN tms_cit_journal.flexible_status IS '弹性域的校验状态【N-不通过】【Y-通过】';
COMMENT ON COLUMN tms_cit_journal.journal_source IS '【0-AVS导入凭证】【1-自动生成凭证】';
COMMENT ON COLUMN tms_cit_journal.journal_temp IS '【0-默认】【1-PAOB Buy】【2-PAOB Sell】【3-PAOB Borrow】【4-PAOB Lend】【5-支付利息】【6-支付本金&利息】【7-收取本金】【8-收取本金&利息】【9-Repo】【10-FX】【11-计提利息】【12-计提估值变动】';
COMMENT ON COLUMN tms_cit_journal.journal_name IS '模板名称';
COMMENT ON COLUMN tms_cit_journal.check_by IS '复核人';
COMMENT ON COLUMN tms_cit_journal.check_date IS '复核时间';
COMMENT ON COLUMN tms_cit_journal.record_person IS '记录人';
COMMENT ON COLUMN tms_cit_journal.business_id IS '业务id 审批用';


CREATE TABLE tms_cit_journal_temp (
    tms_cit_journal_temp_id VARCHAR(32) DEFAULT gen_random_uuid() PRIMARY KEY,
    temp_no BIGINT NOT NULL,
    temp_name VARCHAR(100) NOT NULL,
    effective_date DATE NOT NULL,
    cur_no VARCHAR(3),
    segment1 VARCHAR(25),
    segment2 VARCHAR(25) NOT NULL,
    segment3 VARCHAR(25),
    segment4 VARCHAR(25) NOT NULL,
    segment5 VARCHAR(25),
    segment6 VARCHAR(25),
    segment7 VARCHAR(25) DEFAULT '0000' NOT NULL,
    segment8 VARCHAR(25) DEFAULT '0000' NOT NULL,
    direction VARCHAR(1) NOT NULL,
    entered_dr NUMERIC(20, 2) NOT NULL,
    entered_cr NUMERIC(20, 2) NOT NULL,
    accounted_dr NUMERIC(20, 2) NOT NULL,
    accounted_cr NUMERIC(20, 2) NOT NULL,
    p_entered_dr NUMERIC(20, 2) NOT NULL,
    p_entered_cr NUMERIC(20, 2) NOT NULL,
    journal_line_description VARCHAR(240) DEFAULT '0' NOT NULL,
    data_state VARCHAR(2) DEFAULT '0' NOT NULL,
    created_by VARCHAR(45) DEFAULT 'SYSTEM' NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_by VARCHAR(45) DEFAULT 'SYSTEM' NOT NULL,
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    books_no VARCHAR(32) DEFAULT '0000',
    segment9 VARCHAR(25) DEFAULT '0000',
    segment10 VARCHAR(25) DEFAULT '0000',
    segment11 VARCHAR(25) DEFAULT '0000',
    segment12 VARCHAR(25) DEFAULT '0000',
    segment13 VARCHAR(25) DEFAULT '0000',
    segment14 VARCHAR(25) DEFAULT '0000',
    temp_type VARCHAR(25),
    stream_type VARCHAR(25),
    temp_description VARCHAR(200),
    Remark VARCHAR(25)
);
-- 对表添加注释
COMMENT ON TABLE tms_cit_journal_temp
IS '凭证模板表';

-- 对字段添加注释
COMMENT ON COLUMN tms_cit_journal_temp.tms_cit_journal_temp_id
IS '数据库主键';
COMMENT ON COLUMN tms_cit_journal_temp.temp_no
IS '模板编号：每次新增默认为当前数据库中最大的编号+1';
COMMENT ON COLUMN tms_cit_journal_temp.temp_name
IS '模板名称';
COMMENT ON COLUMN tms_cit_journal_temp.effective_date
IS '制证日期';
COMMENT ON COLUMN tms_cit_journal_temp.cur_no
IS '币种';
COMMENT ON COLUMN tms_cit_journal_temp.segment1
IS '公司段';
COMMENT ON COLUMN tms_cit_journal_temp.segment2
IS '业务段';
COMMENT ON COLUMN tms_cit_journal_temp.segment3
IS '成本中心';
COMMENT ON COLUMN tms_cit_journal_temp.segment4
IS '产品段';
COMMENT ON COLUMN tms_cit_journal_temp.segment5
IS '科目';
COMMENT ON COLUMN tms_cit_journal_temp.segment6
IS '子目';
COMMENT ON COLUMN tms_cit_journal_temp.segment7
IS '备用段1：0000';
COMMENT ON COLUMN tms_cit_journal_temp.segment8
IS '备用段2（关联方）：0000';
COMMENT ON COLUMN tms_cit_journal_temp.direction
IS '方向(借/贷（g代表借，d代表贷）';
COMMENT ON COLUMN tms_cit_journal_temp.entered_dr
IS '借方金额';
COMMENT ON COLUMN tms_cit_journal_temp.entered_cr
IS '贷方金额';
COMMENT ON COLUMN tms_cit_journal_temp.accounted_dr
IS '帐户借方金额';
COMMENT ON COLUMN tms_cit_journal_temp.accounted_cr
IS '帐户贷方金额';
COMMENT ON COLUMN tms_cit_journal_temp.p_entered_dr
IS '报告币借金额';
COMMENT ON COLUMN tms_cit_journal_temp.p_entered_cr
IS '报告币贷金额';
COMMENT ON COLUMN tms_cit_journal_temp.journal_line_description
IS '备注，即摘要';
COMMENT ON COLUMN tms_cit_journal_temp.data_state
IS '数据状态';
COMMENT ON COLUMN tms_cit_journal_temp.created_by
IS '创建人';
COMMENT ON COLUMN tms_cit_journal_temp.created_time
IS '创建时间';
COMMENT ON COLUMN tms_cit_journal_temp.updated_by
IS '修改人';
COMMENT ON COLUMN tms_cit_journal_temp.updated_time
IS '修改时间';
COMMENT ON COLUMN tms_cit_journal_temp.books_no
IS '账套编号';
COMMENT ON COLUMN tms_cit_journal_temp.segment9
IS '备用段3';
COMMENT ON COLUMN tms_cit_journal_temp.segment10
IS '备用段4';
COMMENT ON COLUMN tms_cit_journal_temp.segment11
IS '备用段5';
COMMENT ON COLUMN tms_cit_journal_temp.segment12
IS '备用段6';
COMMENT ON COLUMN tms_cit_journal_temp.segment13
IS '备用段7';
COMMENT ON COLUMN tms_cit_journal_temp.segment14
IS '备用段8';
COMMENT ON COLUMN tms_cit_journal_temp.temp_type
IS '模板类型(PAOB Buy、PAOB Sell、PAOB Borrow、PAOB Lend、支付利息、支付本金&利息、收取本金、收取本金&利息、Repo、FX、计提利息、计提估值变动)';
COMMENT ON COLUMN tms_cit_journal_temp.stream_type
IS '流水类型(付款;收款)';
COMMENT ON COLUMN tms_cit_journal_temp.temp_description
IS '模板描述';
COMMENT ON COLUMN tms_cit_journal_temp.Remark
IS '投资品种类型(Bond、CD、EFB、UST、Interbank、Fixed Deposit、Repo、FX)';


CREATE TABLE IF NOT EXISTS tms_payment_account (
    id BIGSERIAL PRIMARY KEY,
    account_name VARCHAR(100),
    account_number VARCHAR(50),
    bank_name VARCHAR(100),
    currency VARCHAR(10),
    swift_code VARCHAR(20),
    open_date DATE,
    close_date DATE,
    bank_address VARCHAR(200),
    account_status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    account_set VARCHAR(50),
    company_segment VARCHAR(50),
    subject VARCHAR(50),
    sub_subject VARCHAR(50),
    is_deleted INT NOT NULL DEFAULT 0,
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50),
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_payment_account_number UNIQUE (account_number)
);

COMMENT ON TABLE tms_payment_account IS '付款账户信息表';
COMMENT ON COLUMN tms_payment_account.id IS '主键ID';
COMMENT ON COLUMN tms_payment_account.account_name IS '付款账户名称';
COMMENT ON COLUMN tms_payment_account.account_number IS '付款账号';
COMMENT ON COLUMN tms_payment_account.bank_name IS '开户行名称';
COMMENT ON COLUMN tms_payment_account.currency IS '付款币种';
COMMENT ON COLUMN tms_payment_account.swift_code IS 'SWIFT CODE';
COMMENT ON COLUMN tms_payment_account.open_date IS '开户日期';
COMMENT ON COLUMN tms_payment_account.close_date IS '销户日期';
COMMENT ON COLUMN tms_payment_account.bank_address IS '银行地址';
COMMENT ON COLUMN tms_payment_account.account_status IS '账户状态：ACTIVE-有效，INACTIVE-无效';
COMMENT ON COLUMN tms_payment_account.account_set IS '账套';
COMMENT ON COLUMN tms_payment_account.company_segment IS '公司段';
COMMENT ON COLUMN tms_payment_account.subject IS '科目';
COMMENT ON COLUMN tms_payment_account.sub_subject IS '子目';
COMMENT ON COLUMN tms_payment_account.is_deleted IS '是否删除：0-未删除，1-已删除';
COMMENT ON COLUMN tms_payment_account.created_by IS '创建人';
COMMENT ON COLUMN tms_payment_account.created_time IS '创建时间';
COMMENT ON COLUMN tms_payment_account.updated_by IS '更新人';
COMMENT ON COLUMN tms_payment_account.updated_time IS '更新时间';

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


-- 创建表 tms_subject_balance
CREATE TABLE tms_subject_balance (
    code_combination_id VARCHAR(200),
    set_of_books_id VARCHAR(200),
    books_name VARCHAR(200),
    period_name VARCHAR(200),
    currency_code VARCHAR(200),
    actual_flag VARCHAR(200),
    segment1 VARCHAR(100),
    segment1_description VARCHAR(100),
    segment2 VARCHAR(100),
    segment2_description VARCHAR(100),
    segment3 VARCHAR(100),
    segment3_description VARCHAR(100),
    segment4 VARCHAR(100),
    segment4_description VARCHAR(100),
    acc_code1 VARCHAR(100),
    acc_code1_name VARCHAR(100),
    acc_code2 VARCHAR(100),
    acc_code2_name VARCHAR(100),
    acc_code3 VARCHAR(100),
    acc_code3_name VARCHAR(100),
    segment6 VARCHAR(100),
    segment6_description VARCHAR(100),
    segment7 VARCHAR(100),
    segment7_description VARCHAR(100),
    segment8 VARCHAR(100),
    segment8_description VARCHAR(100),
    begin_balance_dr DECIMAL(20,6),
    begin_balance_cr DECIMAL(20,6),
    period_net_dr DECIMAL(20,6) DEFAULT NULL,
    period_net_cr DECIMAL(20,6) DEFAULT NULL,
    end_balance_dr DECIMAL(20,6),
    end_balance_cr DECIMAL(20,6),
    end_balance DECIMAL(20,6),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 为表 tms_subject_balance 添加唯一约束
ALTER TABLE tms_subject_balance
ADD CONSTRAINT uc_unique_subject_balance UNIQUE (set_of_books_id, books_name, period_name, currency_code, segment3, segment4, acc_code3);

-- 为表 tms_subject_balance 添加注释
COMMENT ON TABLE tms_subject_balance IS '会计科目余额表';
-- 为表 tms_subject_balance 的各列添加注释
COMMENT ON COLUMN tms_subject_balance.code_combination_id IS '组合代码ID';
COMMENT ON COLUMN tms_subject_balance.set_of_books_id IS '账套ID';
COMMENT ON COLUMN tms_subject_balance.books_name IS '账套名称';
COMMENT ON COLUMN tms_subject_balance.period_name IS '期间';
COMMENT ON COLUMN tms_subject_balance.currency_code IS '币种';
COMMENT ON COLUMN tms_subject_balance.actual_flag IS '实际数标记:实际数';
COMMENT ON COLUMN tms_subject_balance.segment1 IS '公司段';
COMMENT ON COLUMN tms_subject_balance.segment1_description IS '公司段描述';
COMMENT ON COLUMN tms_subject_balance.segment2 IS '业务段';
COMMENT ON COLUMN tms_subject_balance.segment2_description IS '业务段描述';
COMMENT ON COLUMN tms_subject_balance.segment3 IS '成本中心';
COMMENT ON COLUMN tms_subject_balance.segment3_description IS '成本中心描述';
COMMENT ON COLUMN tms_subject_balance.segment4 IS '产品段';
COMMENT ON COLUMN tms_subject_balance.segment4_description IS '产品段描述';
COMMENT ON COLUMN tms_subject_balance.acc_code1 IS '一级会计科目';
COMMENT ON COLUMN tms_subject_balance.acc_code1_name IS '一级会计科目名称';
COMMENT ON COLUMN tms_subject_balance.acc_code2 IS '二级会计科目';
COMMENT ON COLUMN tms_subject_balance.acc_code2_name IS '二级会计科目名称';
COMMENT ON COLUMN tms_subject_balance.acc_code3 IS '三级会计科目';
COMMENT ON COLUMN tms_subject_balance.acc_code3_name IS '三级会计科目名称';
COMMENT ON COLUMN tms_subject_balance.segment6 IS '子目段';
COMMENT ON COLUMN tms_subject_balance.segment6_description IS '子目段名称';
COMMENT ON COLUMN tms_subject_balance.segment7 IS '备用1';
COMMENT ON COLUMN tms_subject_balance.segment8 IS '关联方';
COMMENT ON COLUMN tms_subject_balance.segment8_description IS '关联方名称';
COMMENT ON COLUMN tms_subject_balance.begin_balance_dr IS '期初借方';
COMMENT ON COLUMN tms_subject_balance.begin_balance_cr IS '期初贷方';
COMMENT ON COLUMN tms_subject_balance.period_net_dr IS '本期借方发生额';
COMMENT ON COLUMN tms_subject_balance.period_net_cr IS '本期贷方发生额';
COMMENT ON COLUMN tms_subject_balance.end_balance_dr IS '期末借方';
COMMENT ON COLUMN tms_subject_balance.end_balance_cr IS '期末贷方';
COMMENT ON COLUMN tms_subject_balance.end_balance IS '期末余额';
COMMENT ON COLUMN tms_subject_balance.created_time IS '创建时间';
COMMENT ON COLUMN tms_subject_balance.updated_time IS '更新时间';


