-- 附件信息表
CREATE TABLE IF NOT EXISTS tms_attachment (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    document_name VARCHAR(200) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    upload_by VARCHAR(50) NOT NULL,
    upload_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    attachment_type VARCHAR(50) NOT NULL,
    business_key VARCHAR(50) NOT NULL,
    is_deleted INT NOT NULL DEFAULT 0,
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50),
    updated_time TIMESTAMP,
    CONSTRAINT pk_attachment PRIMARY KEY (id)
);

COMMENT ON TABLE tms_attachment IS '附件信息表';
COMMENT ON COLUMN tms_attachment.id IS '主键ID';
COMMENT ON COLUMN tms_attachment.document_name IS '文档名称';
COMMENT ON COLUMN tms_attachment.file_path IS '附件存放路径';
COMMENT ON COLUMN tms_attachment.upload_by IS '上传人';
COMMENT ON COLUMN tms_attachment.upload_time IS '上传时间';
COMMENT ON COLUMN tms_attachment.attachment_type IS '附件类型';
COMMENT ON COLUMN tms_attachment.business_key IS '业务主键';
COMMENT ON COLUMN tms_attachment.is_deleted IS '是否删除：0-未删除，1-已删除';
COMMENT ON COLUMN tms_attachment.created_by IS '创建人';
COMMENT ON COLUMN tms_attachment.created_time IS '创建时间';
COMMENT ON COLUMN tms_attachment.updated_by IS '更新人';
COMMENT ON COLUMN tms_attachment.updated_time IS '更新时间';