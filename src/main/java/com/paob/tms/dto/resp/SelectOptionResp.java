package com.paob.tms.dto.resp;
import lombok.Data;
import java.io.Serializable;

@Data
public class SelectOptionResp implements Serializable {
    private String key;
    private String value;
    private String type;
    private String contractNo;
    private String attribute1; // 扩展字段1
    private String attribute2; // 扩展字段2
    private String attribute3; // 扩展字段3
    private String attribute4; // 扩展字段4
    private String attribute5; // 扩展字段5
    private String attribute6; // 扩展字段6
    private String attribute7; // 扩展字段7
    private Boolean flag;

    @Override
    public String toString() {
        return "SelectOptionResp{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", type='" + type + '\'' +
                ", contractNo='" + contractNo + '\'' +
                ", attribute1='" + attribute1 + '\'' +
                ", attribute2='" + attribute2 + '\'' +
                ", attribute3='" + attribute3 + '\'' +
                ", attribute4='" + attribute4 + '\'' +
                ", attribute5='" + attribute5 + '\'' +
                ", attribute6='" + attribute6 + '\'' +
                ", attribute7='" + attribute7 + '\'' +
                ", flag=" + flag +
                '}';
    }
}