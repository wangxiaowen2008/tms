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