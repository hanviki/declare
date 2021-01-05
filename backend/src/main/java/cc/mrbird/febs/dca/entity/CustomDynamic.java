package cc.mrbird.febs.dca.entity;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyAuditdynamic;
import lombok.Data;

import java.util.List;

@Data
public class CustomDynamic {
    private  String id;
    private  String userAccount;
    private  String userAccountName;
    private  String dcaYear;
    private List<DcaBCopyAuditdynamic> dcaBCopyAuditdynamicList;
}
