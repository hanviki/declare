package cc.mrbird.febs.check.entity;

import lombok.Data;

@Data
public class CheckShowTitle {
    private String filedName;

    /**
     * 指标名称
     */
    private String filedTitle;

    private  String ks;

    private  String auditTitletype;
    private  String lb;
    private  String userAccount;
}
