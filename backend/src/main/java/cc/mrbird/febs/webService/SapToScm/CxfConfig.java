package cc.mrbird.febs.webService.SapToScm;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class CxfConfig {

    @Autowired
    private Bus bus;

    @Autowired
    private ISAPtoSCMService isaPtoSCMService;

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, isaPtoSCMService);
        endpoint.publish("/sap");
        return endpoint;
    }
}
