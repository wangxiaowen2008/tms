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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
                (journal != null) {
                    journal.setJournalStatus("4"); // 已复核状态
                    citJournalMapper.updateById(journal);
                }
            }

            log.info("凭证自动审批任务执行完成");
        } catch (Exception e) {
            log.error("凭证自动审批任务执行失败", e);
        }
    }
} 