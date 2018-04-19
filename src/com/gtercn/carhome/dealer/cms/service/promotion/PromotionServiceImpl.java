package com.gtercn.carhome.dealer.cms.service.promotion;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gtercn.carhome.dealer.cms.dao.FavorMapper;
import com.gtercn.carhome.dealer.cms.dao.PromotionMapper;
import com.gtercn.carhome.dealer.cms.entity.Promotion;

@Service(value="promotionService")
public class PromotionServiceImpl implements PromotionService {
	@Autowired
	private PromotionMapper dao;
	@Autowired
	private FavorMapper favorDao;
	
	@Override
	public void deleteBatch(String []ids) {
		for (String id : ids) {
			favorDao.deleteByCondition(id, "5");
			dao.deleteByPrimaryKey(id);
		}
	}

	@Override
	public Integer getTotalCount(Map<String, Object> map) {
		return dao.getTotalCount(map);
	}

	@Override
	public int insert(Promotion promotion) {
		return dao.insert(promotion);
	}

	@Override
	public List<Promotion> queryAllData(Map<String, Object> map) {
		return dao.queryAllData(map);
	}

	@Override
	public Promotion selectByPrimaryKey(String id) {
		return dao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(Promotion promotion) {
		if(1==promotion.getDeleteFlag())
			favorDao.deleteByCondition(promotion.getId(),"5");
		return dao.updateByPrimaryKey(promotion);
	}

}
