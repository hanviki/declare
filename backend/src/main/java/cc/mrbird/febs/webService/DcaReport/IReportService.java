package cc.mrbird.febs.webService.DcaReport;

import cc.mrbird.febs.dca.entity.DcaBReport;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService(name = "IReportService"
)

public interface IReportService {
    @WebMethod
    List<DcaBReport> GetReport();
}
