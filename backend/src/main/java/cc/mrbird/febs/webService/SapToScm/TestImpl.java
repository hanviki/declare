package cc.mrbird.febs.webService.SapToScm;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

@Component
@Service
@WebService(name = "test",targetNamespace ="SapToScm.webService.febs.mrbird.cc",
        endpointInterface = "cc.mrbird.febs.webService.SapToScm.ITestService"// 接口地址
)
public class TestImpl  implements ITestService {
    public String HelloWorld() {
         return  "haha";
    }
}
