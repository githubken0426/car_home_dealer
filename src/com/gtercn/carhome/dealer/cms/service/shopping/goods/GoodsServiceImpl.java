package com.gtercn.carhome.dealer.cms.service.shopping.goods;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gtercn.carhome.dealer.cms.dao.shopping.GoodsMapper;
import com.gtercn.carhome.dealer.cms.dao.shopping.SpecItemGoodsRelationMapper;
import com.gtercn.carhome.dealer.cms.entity.shopping.Goods;
import com.gtercn.carhome.dealer.cms.entity.shopping.SpecItemGoodsRelation;

@Service(value = "goodsService")
public class GoodsServiceImpl implements GoodsService {
	@Autowired
	private GoodsMapper dao;
	@Autowired
	private SpecItemGoodsRelationMapper relationDao;

	@Override
	public Goods selectByPrimaryKey(String id) {
		return dao.selectByPrimaryKey(id);
	}

	@Override
	public List<Goods> queryAllData(Map<String, Object> map) {
		return dao.queryAllData(map);
	}

	@Override
	public int getTotalCount(Map<String, Object> map) {
		return dao.getTotalCount(map);
	}

	@Override
	public int insert(Goods record,List<SpecItemGoodsRelation> relationList) {
		relationDao.insert(relationList);
		return dao.insert(record);
	}

	@Override
	public int update(Goods record,List<SpecItemGoodsRelation> relationList) {
		relationDao.deleteByGoodsId(record.getId());
		relationDao.insert(relationList);
		return dao.update(record);
	}

	@Override
	public int deleteBatch(String[] ids) {
		return dao.deleteBatch(ids);
	}

	@Override
	public Goods selectBySkuCode(String skuCode) {
		return dao.selectBySkuCode(skuCode);
	}
	
	@Override
	public Integer selectGoodsByItem(String goodsId, String cityId, String brandId, String itemIds) {
		return dao.selectGoodsByItem(goodsId, cityId, brandId, itemIds);
	}

	@Override
	public List<Goods> selectGoodsByCity(String cityid, String brandId) {
		return dao.selectGoodsByCity(cityid, brandId);
	}
}
