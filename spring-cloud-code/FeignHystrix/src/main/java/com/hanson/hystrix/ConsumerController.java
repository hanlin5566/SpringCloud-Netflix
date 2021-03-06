package com.hanson.hystrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟客户请求，接口写在了本类中。应该单独封装为一个单独接口
 * @author THINK
 *
 */
@RestController
public class ConsumerController {
	@Autowired
	HansonService service;//注入定义的接口
	@RequestMapping("/")
    public String helloWorld(){
		/**
		 * 使用@FeignClient("hanson-service") 指定要访问的service为hanson-service,由于get()对应的mapping为@RequestMapping(value = "/", method = GET) 那么该方法实际调用url为 
		 * http://hanson-service/，因此在执行此接口时，实际是访问了http://hanson-service/
		 */
		String string = service.get();
		System.out.println(string);
        return string;
    }
	
	/**
	 * feigen接口
	 */
	@FeignClient(value = "hanson-service",fallback = HansonServiceHystrix.class )//声明此接口访问hanson-service-->与服务提供者的配置的spring.application.name一致
	interface HansonService{
		@RequestMapping(value = "/",method = RequestMethod.GET)
	    String get();
	}
	/**
	 * 注册至spring
	 */
	@Component
	class HansonServiceHystrix implements HansonService{
		@Override
		public String get() {
			return "some exception occur call fallback method.";
		}
		
	}
}
