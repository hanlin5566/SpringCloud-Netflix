package com.hanson.eureka;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class ProductServiceApplication {

	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Value("${server.port}")
    private String port;
	
	@RequestMapping(value = { "" }, method = RequestMethod.GET)
    String get() {
		String ret = String.format("你好，这是你要的XX商品，由%s为您提供服务。", port);
        return ret;
    }
    
    @SuppressWarnings("deprecation")
	@RequestMapping("/registered")
    public String getRegistered(){
        List<ServiceInstance> list = discoveryClient.getInstances("STORES");
        System.out.println(discoveryClient.getLocalServiceInstance());
        System.out.println("discoveryClient.getServices().size() = " + discoveryClient.getServices().size());
        
        for( String s :  discoveryClient.getServices()){
        	System.out.println("services " + s);
        	List<ServiceInstance> serviceInstances =  discoveryClient.getInstances(s);
        	for(ServiceInstance si : serviceInstances){
        		System.out.println("    services:" + s + ":getHost()=" + si.getHost());
        		System.out.println("    services:" + s + ":getPort()=" + si.getPort());
        		System.out.println("    services:" + s + ":getServiceId()=" + si.getServiceId());
        		System.out.println("    services:" + s + ":getUri()=" + si.getUri());
        		System.out.println("    services:" + s + ":getMetadata()=" + si.getMetadata());
        	}
        }
        
        System.out.println(list.size());
        if (list != null && list.size() > 0 ) {
            System.out.println( list.get(0).getUri());
        }
    	return null;
    }
	
	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}
}
