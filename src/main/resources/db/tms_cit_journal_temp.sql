CREATE TABLE tms_cit_journal_temp (
    tms_cit_journal_temp_id VARCHAR(50) DEFAULT gen_random_uuid() PRIMARY KEY,
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
    entered_dr VARCHAR(100) NOT NULL,
    entered_cr VARCHAR(100) NOT NULL,
    accounted_dr VARCHAR(100) NOT NULL,
    accounted_cr VARCHAR(100) NOT NULL,
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