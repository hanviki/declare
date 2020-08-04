package cc.mrbird.febs.webService.ScmToGys;

import lombok.Data;

import java.util.List;
@Data
public class WcfMess_XH {


    /// <summary>
    /// 返回信息
    /// </summary>
    private String mess ;

    /// <summary>
    /// 是否成功调用
    /// </summary>
    private Boolean isSuccess ;

    /**
     * 返回采购订单信息
     */
    private List<Purchase> purchasePlans ;
}
