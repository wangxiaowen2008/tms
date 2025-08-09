-- 创建用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGSERIAL PRIMARY KEY,
    um_code VARCHAR(50) NOT NULL UNIQUE,
    user_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    department VARCHAR(100),
    user_status INTEGER DEFAULT 1,
    created_by VARCHAR(50),
    created_time TIMESTAMP,
    updated_by VARCHAR(50),
    updated_time TIMESTAMP,
    deleted INTEGER DEFAULT 0
);

-- 创建角色表
CREATE TABLE IF NOT EXISTS sys_role (
    id BIGSERIAL PRIMARY KEY,
    role_name VARCHAR(100) NOT NULL,
    role_status INTEGER DEFAULT 1,
    created_by VARCHAR(50),
    created_time TIMESTAMP,
    updated_by VARCHAR(50),
    updated_time TIMESTAMP,
    deleted INTEGER DEFAULT 0
);

-- 创建菜单表
CREATE TABLE IF NOT EXISTS sys_menu (
    id BIGSERIAL PRIMARY KEY,
    parent_id BIGINT,
    menu_name VARCHAR(100) NOT NULL,
    menu_path VARCHAR(200),
    menu_icon VARCHAR(100),
    menu_sort INTEGER DEFAULT 0,
    menu_status INTEGER DEFAULT 1,
    created_by VARCHAR(50),
    created_time TIMESTAMP,
    updated_by VARCHAR(50),
    updated_time TIMESTAMP,
    deleted INTEGER DEFAULT 0
);

-- 创建菜单按钮表
CREATE TABLE IF NOT EXISTS sys_menu_button (
    id BIGSERIAL PRIMARY KEY,
    menu_id BIGINT NOT NULL,
    button_name VARCHAR(100) NOT NULL,
    button_code VARCHAR(100) NOT NULL,
    button_status INTEGER DEFAULT 1,
    created_by VARCHAR(50),
    created_time TIMESTAMP,
    updated_by VARCHAR(50),
    updated_time TIMESTAMP,
    deleted INTEGER DEFAULT 0
);

-- 创建用户角色关联表
CREATE TABLE IF NOT EXISTS sys_user_role (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    created_by VARCHAR(50),
    created_time TIMESTAMP,
    deleted INTEGER DEFAULT 0
);

-- 创建角色菜单关联表
CREATE TABLE IF NOT EXISTS sys_role_menu (
    id BIGSERIAL PRIMARY KEY,
    role_id BIGINT NOT NULL,
    menu_id BIGINT NOT NULL,
    created_by VARCHAR(50),
    created_time TIMESTAMP,
    deleted INTEGER DEFAULT 0
);

-- 创建角色按钮关联表
CREATE TABLE IF NOT EXISTS sys_role_button (
    id BIGSERIAL PRIMARY KEY,
    role_id BIGINT NOT NULL,
    button_id BIGINT NOT NULL,
    created_by VARCHAR(50),
    created_time TIMESTAMP,
    deleted INTEGER DEFAULT 0
);

-- 创建REPO交易表
CREATE TABLE IF NOT EXISTS tms_fps_dw_repo (
    id BIGSERIAL PRIMARY KEY,
    trade_number VARCHAR(50) NOT NULL UNIQUE,
    counterpart VARCHAR(100) NOT NULL,
    type_of_facility DECIMAL(10,4),
    repo_rate DECIMAL(10,4),
    currency VARCHAR(10) NOT NULL,
    amount DECIMAL(20,2) NOT NULL,
    trade_date DATE NOT NULL,
    value_date DATE NOT NULL,
    repurchase_date DATE NOT NULL,
    collateral VARCHAR(200),
    interest DECIMAL(20,2),
    channel VARCHAR(50),
    trade_time TIME,
    sort_id INTEGER DEFAULT 0,
    payment_status VARCHAR(20),
    journal_status VARCHAR(20),
    journal_sequence VARCHAR(50),
    created_by VARCHAR(50),
    created_time TIMESTAMP,
    updated_by VARCHAR(50),
    updated_time TIMESTAMP,
    deleted INTEGER DEFAULT 0
); 

-- 创建公司行为表
CREATE TABLE IF NOT EXISTS tms_corporate_action (
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