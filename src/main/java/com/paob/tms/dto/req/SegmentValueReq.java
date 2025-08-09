package com.paob.tms.dto.req;

import lombok.Data;

@Data
public class SegmentValueReq {
    private String bookNo; // 帐套
    private String segmentName; // 段值名称SEGMENT 1~8
    private String codeOrName; // 编码或者描述
    private Long timestamp; // 来自请求客户端的时间戳，防止异步请求中，旧请求结果，覆盖新请求结果
    private String summaryFlag; // 是否查询汇总段值
}