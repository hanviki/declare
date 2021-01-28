package cc.mrbird.febs.webService.OutInfo;

import cc.mrbird.febs.out.entity.OutBInfo;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService(name = "IOutBTpService"
)

public interface  IOutBTpService {
    /**
     * 投票接口
     * @return
     */
    @WebMethod
    List<OutBInfo> GetInfo(String dcaYear,String tpzb,String dyzc,String tpbt);
}
