-- 凭证模板表初始数据
INSERT INTO tms_cit_journal_temp (
    temp_no, temp_name, effective_date, cur_no, segment1, segment2, segment3, 
    segment4, segment5, segment6, segment7, segment8, direction, 
    entered_dr, entered_cr, accounted_dr, accounted_cr, 
    p_entered_dr, p_entered_cr, journal_line_description, 
    data_state, created_by, updated_by, books_no, 
    temp_type, stream_type, temp_description, Remark
) VALUES 
-- Bond 类型模板
(10001, 'BOND_INT', CURRENT_DATE, 'USD', '001', 'BOND', 'CC001', 
'PROD001', '6001', 'SUB001', '0000', '0000', 'd', 
0.00, 1000.00, 0.00, 1000.00, 
0.00, 1000.00, '债券利息收入', 
'0', 'SYSTEM', 'SYSTEM', 'HK_SOB', 
'PAOB Buy', '收款', '债券利息收入模板', 'Bond'),
(90001, 'BOND_INT', CURRENT_DATE, 'USD', '001', 'BOND', 'CC001', 
'PROD001', '6001', 'SUB001', '0000', '0000', 'd', 
0.00, 1000.00, 0.00, 1000.00, 
0.00, 1000.00, '债券利息收入', 
'0', 'SYSTEM', 'SYSTEM', 'HK_SOB', 
'PAOB Buy', '收款', '债券利息收入模板', 'Bond'),

(10002, 'BOND_PRI', CURRENT_DATE, 'USD', '001', 'BOND', 'CC001', 
'PROD001', '1001', 'SUB001', '0000', '0000', 'd', 
0.00, 10000.00, 0.00, 10000.00, 
0.00, 10000.00, '债券本金收入', 
'0', 'SYSTEM', 'SYSTEM', 'HK_SOB', 
'PAOB Buy', '收款', '债券本金收入模板', 'Bond'),

-- CD 类型模板
(10003, 'CD_INT', CURRENT_DATE, 'USD', '001', 'CD', 'CC002', 
'PROD002', '6002', 'SUB002', '0000', '0000', 'd', 
0.00, 500.00, 0.00, 500.00, 
0.00, 500.00, '存单利息收入', 
'0', 'SYSTEM', 'SYSTEM', '001', 
'PAOB Buy', '收款', '存单利息收入模板', 'CD'),

-- EFB 类型模板
(10004, 'EFB_INT', CURRENT_DATE, 'USD', '001', 'EFB', 'CC003', 
'PROD003', '6003', 'SUB003', '0000', '0000', 'd', 
0.00, 800.00, 0.00, 800.00, 
0.00, 800.00, 'EFB利息收入', 
'0', 'SYSTEM', 'SYSTEM', '001', 
'PAOB Buy', '收款', 'EFB利息收入模板', 'EFB'),

-- UST 类型模板
(10005, 'UST_INT', CURRENT_DATE, 'USD', '001', 'UST', 'CC004', 
'PROD004', '6004', 'SUB004', '0000', '0000', 'd', 
0.00, 1200.00, 0.00, 1200.00, 
0.00, 1200.00, '国债利息收入', 
'0', 'SYSTEM', 'SYSTEM', '001', 
'PAOB Buy', '收款', '国债利息收入模板', 'UST'),

-- Interbank 类型模板
(10006, 'IB_INT', CURRENT_DATE, 'USD', '001', 'IB', 'CC005', 
'PROD005', '6005', 'SUB005', '0000', '0000', 'g', 
500.00, 0.00, 500.00, 0.00, 
500.00, 0.00, '同业拆借利息支出', 
'0', 'SYSTEM', 'SYSTEM', '001', 
'PAOB Borrow', '付款', '同业拆借利息支出模板', 'Interbank'),

-- Fixed Deposit 类型模板
(10007, 'FD_INT', CURRENT_DATE, 'USD', '001', 'FD', 'CC006', 
'PROD006', '6006', 'SUB006', '0000', '0000', 'd', 
0.00, 600.00, 0.00, 600.00, 
0.00, 600.00, '定期存款利息收入', 
'0', 'SYSTEM', 'SYSTEM', '001', 
'PAOB Lend', '收款', '定期存款利息收入模板', 'Fixed Deposit'),

-- Repo 类型模板
(10008, 'REPO_INT', CURRENT_DATE, 'USD', '001', 'REPO', 'CC007', 
'PROD007', '6007', 'SUB007', '0000', '0000', 'g', 
300.00, 0.00, 300.00, 0.00, 
300.00, 0.00, '回购利息支出', 
'0', 'SYSTEM', 'SYSTEM', '001', 
'Repo', '付款', '回购利息支出模板', 'Repo'),

-- FX 类型模板
(10009, 'FX_TRADE', CURRENT_DATE, 'USD', '001', 'FX', 'CC008', 
'PROD008', '6008', 'SUB008', '0000', '0000', 'd', 
0.00, 2000.00, 0.00, 2000.00, 
0.00, 2000.00, '外汇交易收入', 
'0', 'SYSTEM', 'SYSTEM', '001', 
'FX', '收款', '外汇交易收入模板', 'FX'),

-- 计提利息模板
(10010, 'ACCR_INT', CURRENT_DATE, 'USD', '001', 'ACCR', 'CC009', 
'PROD009', '6009', 'SUB009', '0000', '0000', 'g', 
1500.00, 0.00, 1500.00, 0.00, 
1500.00, 0.00, '利息计提', 
'0', 'SYSTEM', 'SYSTEM', '001', 
'计提利息', '付款', '利息计提模板', 'Bond'),

(10001, 'TEST', CURRENT_DATE, 'USD', '001', 'TEST', 'TEST', 
'TEST', 'TEST', 'TEST', '0000', '0000', 'd', 
0.00, 1000.00, 0.00, 1000.00, 
0.00, 1000.00, '测试', 
'0', 'SYS', 'SYS', '001', 
'TEST', 'TEST', '测试', 'TEST'); 