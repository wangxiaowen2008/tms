-- 插入REPO交易测试数据（如果不存在）
INSERT INTO tms_fps_dw_repo (
    trade_number, counterpart, type_of_facility, repo_rate, currency, amount,
    trade_date, value_date, repurchase_date, collateral, interest, channel,
    trade_time, sort_id, payment_status, journal_status, journal_sequence,
    created_by, created_time, updated_by, updated_time
)
SELECT 
    'REPO202403150001', '中国银行', 1.0000, 2.5000, 'CNY', 1000000.00,
    CURRENT_DATE, CURRENT_DATE + 1, CURRENT_DATE + 7, '国债', 1000.00, '柜台',
    CURRENT_TIME, 1, '已付款', '待复核', 'V202403150001',
    'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM tms_fps_dw_repo WHERE trade_number = 'REPO202403150001');

-- 插入一条公司行为测试数据
INSERT INTO tms_corporate_action (
    action_no,
    trade_numbers,
    entity_id,
    counterpart,
    action_type,
    currency,
    settlement_amount,
    value_date,
    remark,
    debt_security_name,
    isin,
    settlement_account,
    broker,
    payment_status,
    journal_status,
    principal_amount,
    interest_amount,
    total_amount,
    create_by,
    update_by
) VALUES (
    'EFB00001',
    'TRADE001,TRADE002',
    'ENTITY001',
    'COUNTERPART001',
    '利息',
    'USD',
    10000.0000,
    '2024-04-17',
    'EFB',
    'TEST BOND 2024',
    'US1234567890',
    'ACC001',
    'BROKER001',
    '未收款',
    '未制证',
    8000.0000,
    2000.0000,
    10000.0000,
    'admin',
    'admin'
);

-- 插入更多REPO交易测试数据
INSERT INTO tms_fps_dw_repo (
    trade_number, counterpart, type_of_facility, repo_rate, currency, amount,
    trade_date, value_date, repurchase_date, collateral, interest, channel,
    trade_time, sort_id, payment_status, journal_status, journal_sequence,
    created_by, created_time, updated_by, updated_time
)
SELECT 
    'REPO202403150002', '工商银行', 1.5000, 2.8000, 'CNY', 2000000.00,
    CURRENT_DATE, CURRENT_DATE + 1, CURRENT_DATE + 14, '地方债', 2800.00, '电子交易',
    CURRENT_TIME, 2, '未付款', '未复核', 'V202403150002',
    'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM tms_fps_dw_repo WHERE trade_number = 'REPO202403150002');

INSERT INTO tms_fps_dw_repo (
    trade_number, counterpart, type_of_facility, repo_rate, currency, amount,
    trade_date, value_date, repurchase_date, collateral, interest, channel,
    trade_time, sort_id, payment_status, journal_status, journal_sequence,
    created_by, created_time, updated_by, updated_time
)
SELECT 
    'REPO202403150003', '建设银行', 2.0000, 3.2000, 'USD', 500000.00,
    CURRENT_DATE, CURRENT_DATE + 2, CURRENT_DATE + 30, '金融债', 4000.00, '柜台',
    CURRENT_TIME, 3, '已付款', '已复核', 'V202403150003',
    'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM tms_fps_dw_repo WHERE trade_number = 'REPO202403150003');

-- 插入更多公司行为测试数据
INSERT INTO tms_corporate_action (
    action_no,
    trade_numbers,
    entity_id,
    counterpart,
    action_type,
    currency,
    settlement_amount,
    value_date,
    remark,
    debt_security_name,
    isin,
    settlement_account,
    broker,
    payment_status,
    journal_status,
    principal_amount,
    interest_amount,
    total_amount,
    create_by,
    update_by
) 
SELECT
    'EFB00002',
    'TRADE003,TRADE004',
    'ENTITY002',
    'COUNTERPART002',
    '到期',
    'CNY',
    5000000.0000,
    CURRENT_DATE + 30,
    'EFB',
    '21国债11',
    'CN1234567890',
    'ACC002',
    'BROKER002',
    '未收款',
    '未制证',
    4800000.0000,
    200000.0000,
    5000000.0000,
    'admin',
    'admin'
WHERE NOT EXISTS (SELECT 1 FROM tms_corporate_action WHERE action_no = 'EFB00002');

INSERT INTO tms_corporate_action (
    action_no,
    trade_numbers,
    entity_id,
    counterpart,
    action_type,
    currency,
    settlement_amount,
    value_date,
    remark,
    debt_security_name,
    isin,
    settlement_account,
    broker,
    payment_status,
    journal_status,
    principal_amount,
    interest_amount,
    total_amount,
    create_by,
    update_by
) 
SELECT
    'EFB00003',
    'TRADE005',
    'ENTITY003',
    'COUNTERPART003',
    '利息',
    'HKD',
    300000.0000,
    CURRENT_DATE + 60,
    'EFB',
    'HKGB 2025',
    'HK9876543210',
    'ACC003',
    'BROKER003',
    '已收款',
    '已制证',
    0.0000,
    300000.0000,
    300000.0000,
    'admin',
    'admin'
WHERE NOT EXISTS (SELECT 1 FROM tms_corporate_action WHERE action_no = 'EFB00003');

-- 插入更多用户数据
INSERT INTO sys_user (um_code, user_name, email, department, user_status, created_by, created_time)
SELECT 'trader1', '交易员1', 'trader1@example.com', '交易部', 1, 'system', CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE um_code = 'trader1');

INSERT INTO sys_user (um_code, user_name, email, department, user_status, created_by, created_time)
SELECT 'risk1', '风控员1', 'risk1@example.com', '风控部', 1, 'system', CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE um_code = 'risk1');

-- 插入更多角色数据
INSERT INTO sys_role (role_name, role_status, created_by, created_time)
SELECT '交易员', 1, 'system', CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM sys_role WHERE role_name = '交易员');

INSERT INTO sys_role (role_name, role_status, created_by, created_time)
SELECT '风控员', 1, 'system', CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM sys_role WHERE role_name = '风控员');

-- 关联用户和角色
DO $$
DECLARE 
    v_user_id BIGINT;
    v_role_id BIGINT;
BEGIN
    -- 获取交易员用户ID
    SELECT id INTO v_user_id FROM sys_user WHERE um_code = 'trader1';
    
    -- 获取交易员角色ID
    SELECT id INTO v_role_id FROM sys_role WHERE role_name = '交易员';
    
    -- 关联交易员用户和角色
    INSERT INTO sys_user_role (user_id, role_id, created_by, created_time)
    SELECT v_user_id, v_role_id, 'system', CURRENT_TIMESTAMP
    WHERE NOT EXISTS (
        SELECT 1 FROM sys_user_role 
        WHERE user_id = v_user_id AND role_id = v_role_id
    );
    
    -- 获取风控员用户ID
    SELECT id INTO v_user_id FROM sys_user WHERE um_code = 'risk1';
    
    -- 获取风控员角色ID
    SELECT id INTO v_role_id FROM sys_role WHERE role_name = '风控员';
    
    -- 关联风控员用户和角色
    INSERT INTO sys_user_role (user_id, role_id, created_by, created_time)
    SELECT v_user_id, v_role_id, 'system', CURRENT_TIMESTAMP
    WHERE NOT EXISTS (
        SELECT 1 FROM sys_user_role 
        WHERE user_id = v_user_id AND role_id = v_role_id
    );
END $$; 