package com.gtercn.carhome.dealer.cms.dao;

import java.util.List;
import java.util.Map;

import com.gtercn.carhome.dealer.cms.entity.HomeCarousel;


public interface HomeCarouselMapper {
	/**
	 * 查询所有文章数据
	 * @param map
	 * @return
	 * 2017-2-21 下午02:48:14
	 */
	List<HomeCarousel> queryAllData(Map<String, Object> map);
	
	/**
	 * 查询所有数据条数
	 * @param map
	 * @return
	 * 2017-2-21 下午02:48:14
	 */
	public int getTotalCount(Map<String, Object> map);
	
    int deleteByPrimaryKey(String id);

    int insert(HomeCarousel record);

    HomeCarousel selectByPrimaryKey(String id);
    /**
     * 修改
     * @param record
     * @return
     * 2017-3-1 上午11:14:59
     */
    int updateByPrimaryKey(HomeCarousel record);
    
 
}