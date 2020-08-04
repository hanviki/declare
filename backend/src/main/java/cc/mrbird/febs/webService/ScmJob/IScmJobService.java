package cc.mrbird.febs.webService.ScmJob;
import cc.mrbird.febs.scm.entity.*;
import cc.mrbird.febs.system.domain.User;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * WebService2
 */
@WebService(name = "IScmJobService"
)
public interface IScmJobService {

    /*
    获取验收文件有效期的提醒
     */
    @WebMethod
    List<VMsgFilevalidinfo> getFileValidMsg();

    /*
    获取订单消息的提醒
     */
    @WebMethod
    List<VMsgOrderinfo> getOrderMsg();

    /*
    获取订单改变的记录
     */
    @WebMethod
    List<VMsgOrderinfomodify> getOrderModifyMsg();

    /*
    获取供应计划中物料的有效期提醒
     */
    @WebMethod
    List<VMsgMaterinfovalid> getMaterValidMsg();

    /*
       获取需要询价的记录
         */
    @WebMethod
    List<VMsgQuerypriceinfo> getQueryPriceMsg();

    /*
    获取报告记录
     */
    @WebMethod
    List<VMsgReportinfo> getReportMsg();

    @WebMethod
    List<User> getUserWithoutOpenid();

    @WebMethod
    void setUserOpenid(List<User> users);

    /*
    验收文件库房审核 发消息给库房管理员
     */
    @WebMethod
    List<VMsgGysysaudit> getGysysaudit();

    /*
   验收文件审核不通过 发消息给供应商
    */
    @WebMethod
    List<VMsgGysysauditnot> getGysysauditNot();

    /*
  审核未通过
   */
    @WebMethod
    List<VMsgVendoraudit> getVendoraudit();

    /*
供应计划数量为完成
*/
    @WebMethod
    List<VMsgPlanundo> getPlanundo();
}
