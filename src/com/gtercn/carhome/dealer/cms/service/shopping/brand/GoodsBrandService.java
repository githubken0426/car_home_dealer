package com.gtercn.carhome.dealer.cms.service.shopping.brand;

import java.util.List;

import com.gtercn.carhome.dealer.cms.entity.shopping.GoodsBrand;


public interface GoodsBrandService {
	/**
	 * 主键查询
	 * @param id
	 * @return
	 * @throws 
	 * @date 2018年2月6日 下午1:53:15
	 */
	GoodsBrand selectByPrimaryKey(String id);
	
	List<GoodsBrand> queryDataByCategory(String categoryId);
}