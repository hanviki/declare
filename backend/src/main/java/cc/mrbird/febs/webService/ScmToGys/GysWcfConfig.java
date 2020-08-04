package cc.mrbird.febs.webService.ScmToGys;

import cc.mrbird.febs.webService.SapToScm.ITestService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class GysWcfConfig {
    @Autowired
    private Bus bus;

    @Autowired
    private ISCM_XHService iscm_xhService;

    @Bean(name = "endGys")
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, iscm_xhService);
        endpoint.publish("/gys");
        return endpoint;
    }
}
