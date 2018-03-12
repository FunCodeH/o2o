package com.imooc.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 为html文件添加路由
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/shopadmin")
public class ShopAdminController {
	
	@RequestMapping(value="/shopoperation", method={RequestMethod.GET})
	public String shopOperation(){
		//已在spring-web.xml里面配置了前后缀，这里只需要配置一部分路径
		return "shop/shopoperation";
	}
	
	@RequestMapping(value="/shoplist", method={RequestMethod.GET})
	public String shopList(){
		//已在spring-web.xml里面配置了前后缀，这里只需要配置一部分路径
		return "shop/shoplist";
	}
	
	@RequestMapping(value="/shopmanagement", method={RequestMethod.GET})
	public String shopManagement(){
		//已在spring-web.xml里面配置了前后缀，这里只需要配置一部分路径
		return "shop/shopmanagement";
	}
	
	@RequestMapping(value="/productcategorymanagement", method={RequestMethod.GET})
	public String productCategoryManagement(){
		//已在spring-web.xml里面配置了前后缀，这里只需要配置一部分路径
		return "shop/productcategorymanagement";
	}

}
