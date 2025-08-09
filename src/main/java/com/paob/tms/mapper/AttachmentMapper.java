package com.paob.tms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.model.Attachment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * 附件Mapper接口
 */
@Mapper
public interface AttachmentMapper extends BaseMapper<Attachment> {
    
    /**
     * 分页查询附件列表
     */
    IPage<Attachment> selectPage(Page<Attachment> page,
                               @Param("attachmentType") String attachmentType,
                               @Param("businessKey") String businessKey,
                               @Param("uploadBy") String uploadBy,
                               @Param("createTimeStart") LocalDateTime createTimeStart,
                               @Param("createTimeEnd") LocalDateTime createTimeEnd);
} 