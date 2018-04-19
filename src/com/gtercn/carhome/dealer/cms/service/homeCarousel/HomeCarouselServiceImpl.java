package com.gtercn.carhome.dealer.cms.service.homeCarousel;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gtercn.carhome.dealer.cms.dao.HomeCarouselMapper;
import com.gtercn.carhome.dealer.cms.entity.HomeCarousel;

@Service(value = "homeCarouselService")
public class HomeCarouselServiceImpl implements HomeCarouselService {
	@Autowired
	private HomeCarouselMapper dao;

	@Override
	public List<HomeCarousel> queryAllData(Map<String, Object> map) {
		return dao.queryAllData(map);
	}

	@Override
	public HomeCarousel selectByPrimaryKey(String id) throws Exception {
		return dao.selectByPrimaryKey(id);
	}

	

	@Override
	public int getTotalCount(Map<String, Object> map) {
		return dao.getTotalCount(map);
	}

	@Override
	public int insert(HomeCarousel o) throws Exception {
		return dao.insert(o);		
	}

	@Override
	public void deleteData(String ids[]) throws Exception {
		for (String id : ids) {
			dao.deleteByPrimaryKey(id);
		}
	}

	@Override
	public int updateByPrimaryKey(HomeCarousel record) {
		return dao.updateByPrimaryKey(record);
	}
}
