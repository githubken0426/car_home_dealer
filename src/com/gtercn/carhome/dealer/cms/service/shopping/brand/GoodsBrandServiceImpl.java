package com.gtercn.carhome.dealer.cms.service.shopping.brand;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gtercn.carhome.dealer.cms.dao.shopping.GoodsBrandMapper;
import com.gtercn.carhome.dealer.cms.entity.shopping.GoodsBrand;


@Service(value = "goodsBrandService")
public class GoodsBrandServiceImpl implements GoodsBrandService {
	@Autowired
	private GoodsBrandMapper dao;

	@Override
	public GoodsBrand selectByPrimaryKey(String id) {
		return dao.selectByPrimaryKey(id);
	}
	
	@Override
	public List<GoodsBrand> queryDataByCategory(String categoryId) {
		return dao.queryDataByCategory(categoryId);
	}
}
