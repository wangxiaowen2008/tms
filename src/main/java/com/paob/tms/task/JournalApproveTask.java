package com.paob.tms.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.paob.tms.mapper.TmsCitJournalMapper;
import com.paob.tms.mapper.TmsVoucherDetailMapper;
import com.paob.tms.model.TmsCitJournal;
import com.paob.tms.model.TmsVoucherDetail;
import com.paob.tms.service.impl.JournalServiceImpl;
import com.paob.tms.util.VoucherFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Component
public class JournalApproveTask {

    @Autowired
    private TmsVoucherDetailMapper voucherDetailMapper;

    @Autowired
    private TmsCitJournalMapper citJournalMapper;

    @Autowired
    private JournalServiceImpl journalService;

    @Autowired
    private VoucherFileUtil voucherFileUtil;

    @Value("${voucher.file.useJavaEncrypt:true}")
    private boolean useJavaEncrypt;

    @Scheduled(cron = "0 */5 * * * ?") // 每5分钟执行一次
    public void approvePendingJournals() {
        log.info("开始执行凭证自动审批任务");
        try {
            // 查询所有状态为待复核的凭证
            LambdaQueryWrapper<TmsVoucherDetail> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(TmsVoucherDetail::getJournalStatus, "2"); // 待复核状态
            List<TmsVoucherDetail> voucherDetails = voucherDetailMapper.selectList(wrapper);
            
            if (voucherDetails.isEmpty()) {
                log.info("没有待审批的凭证");
                return;
            }

            log.info("查询到{}条待审批的凭证记录", voucherDetails.size());

            // 处理凭证文件
            voucherFileUtil.processVoucherFiles(voucherDetails, useJavaEncrypt);

            // 更新凭证状态
            for (TmsVoucherDetail detail : voucherDetails) {
                // 更新凭证明细状态
                detail.setJournalStatus("4"); // 已复核状态
                voucherDetailMapper.updateById(detail);

                // 更新凭证主表状态
                TmsCitJournal journal = citJournalMapper.selectById(detail.getJournalId());
                if (journal != null) {
                    journal.setJournalStatus("4"); // 已复核状态
                    citJournalMapper.updateById(journal);
                }
            }

            log.info("凭证自动审批任务执行完成");
        } catch (Exception e) {
            log.error("凭证自动审批任务执行失败", e);
        }
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * 根据用户名查找用户 - 使用参数化查询防止SQL注入
     * @param username 用户名
     * @return 用户列表
     */
    public List<User> findUserByUsername(String username) {
        // 修复：使用参数化查询防止SQL注入
        String sql = "SELECT * FROM users WHERE username = ?";
        return jdbcTemplate.query(sql, new UserRowMapper(), username);
    }
    
    /**
     * 根据用户ID删除用户 - 使用参数化查询防止SQL注入
     * @param userId 用户ID
     */
    public void deleteUser(String userId) {
        // 修复：使用参数化查询防止SQL注入
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, userId);
    }
    
    /**
     * 用户行映射器
     */
    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            // 根据实际的用户表结构设置属性
            // user.setId(rs.getLong("id"));
            // user.setUsername(rs.getString("username"));
            // 添加其他必要的属性设置
            return user;
        }
    }
    
    /**
     * 用户实体类
     */
    public static class User {
        // 添加必要的用户属性
        // private Long id;
        // private String username;
        // 添加getter和setter方法
    }
} 