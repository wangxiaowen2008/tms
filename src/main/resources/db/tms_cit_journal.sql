CREATE TABLE tms_cit_journal (
    tms_cit_journal_id VARCHAR(50) DEFAULT gen_random_uuid() PRIMARY KEY,
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
    journal_temp VARCHAR(50),
    check_by VARCHAR(45),
    check_date DATE,
    record_person VARCHAR(45),
    business_id VARCHAR(50)
);
COMMENT ON TABLE tms_cit_journal IS '系统凭证业务表';
COMMENT ON COLUMN tms_cit_journal.tms_cit_journal_id IS '数据库主键';
COMMENT ON COLUMN tms_cit_journal.tms_posting_rule_id IS '制证规则编号(记账凭证必填)';
COMMENT ON COLUMN tms_cit_journal.category_name IS '1司库凭证、2手工凭证、3导入凭证、4其它凭证';
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