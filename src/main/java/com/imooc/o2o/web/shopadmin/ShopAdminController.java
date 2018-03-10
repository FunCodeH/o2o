package com.imooc.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/shopadmin")
public class ShopAdminController {
	
	@RequestMapping(value="/shopoperation", method={RequestMethod.GET})
	public String shopOperation(){
		//已在spring-web.xml里面配置了前后缀，这里只需要配置一部分路径
		return "shop/shopoperation";
	}

}
