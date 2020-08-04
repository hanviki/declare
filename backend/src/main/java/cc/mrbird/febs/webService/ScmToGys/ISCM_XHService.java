package  cc.mrbird.febs.webService.ScmToGys;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.Date;
import java.util.List;

/**
 * WebService2
 */
@WebService(name = "ISCM_XHService"
)
public interface ISCM_XHService {
    /**
     * 获取采购订单
      * @param userName
     * @param password
     * @param startTime
     * @param endTime
     * @return
     */
    @WebMethod
    String HelloWorld();
    @WebMethod
    WcfMess_XH ExportPurchasePlan(String userName, String password, String startTime, String endTime);

    /**
     * 上传采购计划
     * @param userName
     * @param password
     * @param trueName
     * @param PlanDetails
     * @return
     */
    @WebMethod
   List<WcfPlan_XH> ImportSupplyPlan(String userName, String password, String trueName, List<PlanDetail> PlanDetails);
}