package cc.mrbird.febs.check.entity;

import lombok.Data;

@Data
public class TotalResultNum {
    private String userAccount;
    private String dcaYear;
    private Double auditResult;
    private String auditTitletype;
    private String strAuditResult;
}
