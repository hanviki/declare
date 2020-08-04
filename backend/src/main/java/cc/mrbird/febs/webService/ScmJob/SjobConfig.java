package cc.mrbird.febs.webService.ScmJob;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class SjobConfig {


    @Autowired
    private Bus bus;

    @Autowired
    private IScmJobService iScmJobService;

    @Bean(name = "endSjob")
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, iScmJobService);
        endpoint.publish("/sjob");
        return endpoint;
    }

}
