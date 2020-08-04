package  cc.mrbird.febs.webService.SapToScm;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * WebService2
 */
@WebService(name = "ITestService"
)
public interface  ITestService {
    @WebMethod
    String HelloWorld();
}