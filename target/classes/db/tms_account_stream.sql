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