DROP TABLE IF EXISTS tms_subject_balance;
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
    begin_balance DECIMAL(20,6),
    period_net_dr DECIMAL(20,6) DEFAULT NULL,
    period_net_cr DECIMAL(20,6) DEFAULT NULL,
    period_net DECIMAL(20,6) DEFAULT NULL,
    end_balance_dr DECIMAL(20,6),
    end_balance_cr DECIMAL(20,6),
    end_balance DECIMAL(20,6),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 为表 tms_subject_balance 添加唯一约束
ALTER TABLE tms_subject_balance
ADD CONSTRAINT uc_unique_subject_balance UNIQUE (set_of_books_id, code_combination_id, period_name, currency_code;

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