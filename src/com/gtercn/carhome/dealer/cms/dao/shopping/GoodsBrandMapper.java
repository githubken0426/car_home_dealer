package com.gtercn.carhome.dealer.cms.dao.shopping;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gtercn.carhome.dealer.cms.entity.shopping.GoodsBrand;


@Repository
public interface GoodsBrandMapper {
    
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