package cc.mrbird.febs.webService.DcaReport;


import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class ReportConfig {
    @Autowired
    private Bus bus;

    @Autowired
    private IReportService iReportService;

    @Bean(name = "endreport")
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, iReportService);
        endpoint.publish("/report");
        return endpoint;
    }
}
