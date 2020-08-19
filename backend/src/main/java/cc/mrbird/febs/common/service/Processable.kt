package cc.mrbird.febs.common.service

/**
 * 与流程有关联的业务实体类必须实现此接口
 * @author viki
 * @date 20200805
 */
interface Processable {

    val id: Long?

    fun setProcessInstanceId(processInstanceId: String)

}