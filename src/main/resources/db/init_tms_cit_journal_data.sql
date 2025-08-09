-- 初始化系统凭证业务表数据
INSERT INTO tms_cit_journal (
    tms_cit_journal_id,
    category_name,
    journal_sequence,
    effective_date,
    cur_no,
    segment1,
    segment2,
    segment3,
    segment4,
    segment5,
    segment6,
    segment7,
    segment8,
    segment9,
    entered_dr,
    entered_cr,
    accounted_dr,
    accounted_cr,
    p_entered_dr,
    p_entered_cr,
    journal_line_description,
    journal_status,
    write_offs,
    ready_state,
    data_state,
    created_by,
    created_time,
    updated_by,
    updated_time,
    secur_no,
    secur_type,
    market_no,
    deal_number,
    books_no,
    segment10,
    segment11,
    segment12,
    segment13,
    segment14,
    msg,
    flexible_status,
    journal_source,
    journal_temp,
    journal_name,
    check_by,
    check_date,
    record_person,
    business_id
) VALUES 
-- 凭证1：PAOB Buy
('1', '明晰凭证', 'J20240101001', '2024-01-01', 'HKD', 'PAOB', 'TRADE', 'CC001', 'EQUITY', '1001', '100101', '0000', '000000', '0000', 
1000000.00, 0.00, 1000000.00, 0.00, 1000000.00, 0.00, '购买股票', 2, 'N', 'READY001', 'Y', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP,
'00001.HK', '1', 'HK', '1000', 'BOOK001', '0000', '0000', '0000', '0000', '0000', 'HK_SOB', 'Y', '1', '1', 'PAOB Buy', 'CHECKER1', '2024-01-01', 'RECORDER1', 'BIZ001'),

-- 凭证1的贷方分录
('2', '明晰凭证', 'J20240101001', '2024-01-01', 'HKD', 'PAOB', 'TRADE', 'CC001', 'EQUITY', '2001', '200101', '0000', '000000', '0000', 
0.00, 1000000.00, 0.00, 1000000.00, 0.00, 1000000.00, '购买股票', 2, 'N', 'READY001', 'Y', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP,
'00001.HK', '1', 'HK', '1000', 'BOOK001', '0000', '0000', '0000', '0000', '0000', 'HK_SOB', 'Y', '1', '1', 'PAOB Buy', 'CHECKER1', '2024-01-01', 'RECORDER1', 'BIZ001'),

-- 凭证2：计提利息
('3', '明晰凭证', 'J20240101002', '2024-01-01', 'HKD', 'PAOB', 'TRADE', 'CC002', 'BOND', '3001', '300101', '0000', '000000', '0000', 
50000.00, 0.00, 50000.00, 0.00, 50000.00, 0.00, '计提债券利息', 2, 'N', 'READY002', 'Y', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP,
'BOND001', '3', 'HK', '100', 'BOOK001', '0000', '0000', '0000', '0000', '0000', 'HK_SOB', 'Y', '1', '11', '计提利息', 'CHECKER1', '2024-01-01', 'RECORDER1', 'BIZ002'),

-- 凭证2的贷方分录
('4', '明晰凭证', 'J20240101002', '2024-01-01', 'HKD', 'PAOB', 'TRADE', 'CC002', 'BOND', '4001', '400101', '0000', '000000', '0000', 
0.00, 50000.00, 0.00, 50000.00, 0.00, 50000.00, '计提债券利息', 2, 'N', 'READY002', 'Y', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP,
'BOND001', '3', 'HK', '100', 'BOOK001', '0000', '0000', '0000', '0000', '0000', 'HK_SOB', 'Y', '1', '11', '计提利息', 'CHECKER1', '2024-01-01', 'RECORDER1', 'BIZ002'),

-- 凭证3：FX交易
('5', '明晰凭证', 'J20240101003', '2024-01-01', 'USD', 'PAOB', 'TRADE', 'CC003', 'FX', '5001', '500101', '0000', '000000', '0000', 
100000.00, 0.00, 780000.00, 0.00, 780000.00, 0.00, '美元兑换港币', 2, 'N', 'READY003', 'Y', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP,
NULL, '10', 'FX', '100000', 'BOOK001', '0000', '0000', '0000', '0000', '0000', 'HK_SOB', 'Y', '1', '10', 'FX', 'CHECKER1', '2024-01-01', 'RECORDER1', 'BIZ003'),

-- 凭证3的贷方分录
('6', '明晰凭证', 'J20240101003', '2024-01-01', 'HKD', 'PAOB', 'TRADE', 'CC003', 'FX', '6001', '600101', '0000', '000000', '0000', 
0.00, 780000.00, 0.00, 780000.00, 0.00, 780000.00, '美元兑换港币', 2, 'N', 'READY003', 'Y', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP,
NULL, '10', 'FX', '100000', 'BOOK001', '0000', '0000', '0000', '0000', '0000', 'HK_SOB', 'Y', '1', '10', 'FX', 'CHECKER1', '2024-01-01', 'RECORDER1', 'BIZ003'); 