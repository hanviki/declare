package cc.mrbird.febs.webService.ScmToGys;

import lombok.Data;

@Data
public class WcfPlan_XH {
    /**
     * 唯一标识
     */
    private String id ;
    /**
     * 是否成功
     */
    private Boolean isSuccess ;
    /**
     * 消息
     */
    private String mess ;

    /**
     * 供应计划号
     */
    private String code ;

}
