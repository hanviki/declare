package cc.mrbird.febs.webService.OutInfo;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class OutBConfig {
    @Autowired
    private Bus bus;

    @Autowired
    private IOutBTpService iOutBTpService;

    @Bean(name = "endtp")
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, iOutBTpService);
        endpoint.publish("/tp");
        return endpoint;
    }
}
