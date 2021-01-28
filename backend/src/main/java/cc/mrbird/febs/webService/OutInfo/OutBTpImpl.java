package cc.mrbird.febs.webService.OutInfo;

import cc.mrbird.febs.out.entity.OutBInfo;
import cc.mrbird.febs.out.service.IOutBInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;

@Slf4j
@Component
@Service
@WebService(name = "dca", targetNamespace = "urn:OutInfo.webService.febs.mrbird.cc",
        endpointInterface = "cc.mrbird.febs.webService.OutInfo.IOutBTpService"// 接口地址
)
public class OutBTpImpl implements IOutBTpService {
    @Autowired
    private IOutBInfoService iOutBInfoService;

    @Override
    public List<OutBInfo> GetInfo(String dcaYear,String tpzb,String dyzc,String tpbt){
        return  this.iOutBInfoService.getAll(dcaYear,tpzb,dyzc,tpbt);
    }
}
