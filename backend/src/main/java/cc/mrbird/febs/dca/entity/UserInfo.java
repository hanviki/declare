package cc.mrbird.febs.dca.entity;

import lombok.Data;

import java.util.List;

@Data
public class UserInfo {
//    this.eduList = []
//            this.partjobList = []
//            this.punishOrPrizeList = []
//            this.boardList = []
//            this.auditList = []
//            this.acdemicList = []
    private List<DcaBEducationexperice> eduList;
    private List<DcaBParttimejob> partjobList;
    private List<DcaBPrizeorpunish> punishOrPrizeList;
    private List<DcaBExportcountry> boardList;
    private List<DcaBAuditfive> auditList;
    private List<DcaBAcademic> acdemicList;
}
