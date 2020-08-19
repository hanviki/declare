package cc.mrbird.febs.system.dto;

import lombok.Data;

/**
 * 每一节点的审批结果
 * @author viki
 * @date 2020/08/05
 */
@Data
public class ApproveDTO {
    /**
     * 任务实例ID
     */
    private String taskId;
    /**
     * 流程实例ID
     */
    private String processInstanceId;
    /**
     * 审批人意见
     */
    private String comment;
    /**
     * 审批结果
     */
    private Integer result;
}
