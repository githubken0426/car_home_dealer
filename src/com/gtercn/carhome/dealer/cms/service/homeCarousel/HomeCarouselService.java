package com.gtercn.carhome.dealer.cms.service.homeCarousel;

import java.util.List;
import java.util.Map;

import com.gtercn.carhome.dealer.cms.entity.HomeCarousel;

public interface HomeCarouselService {

	/**
	 * 查询
	 * 
	 * @return
	 */
	public List<HomeCarousel> queryAllData(Map<String, Object> map);
	
	/**
	 * 查询所有数据条数
	 * 
	 * @return
	 */
	public int getTotalCount(Map<String, Object> map);

	/**
	 * 根据id获取
	 * 
	 * @param id
	 * @return
	 */
	public HomeCarousel selectByPrimaryKey(String id) throws Exception;

	/**
	 * 新增
	 * 
	 * @param o
	 * @return
	 */
	public int insert(HomeCarousel o) throws Exception;

	/**
     * 修改文章
     * @param record
     * @return
     * 2017-3-1 上午11:14:59
     */
    int updateByPrimaryKey(HomeCarousel record);
    

	/**
	 * 删除
	 * 
	 * @param o
	 * @return
	 */
	public void deleteData(String ids[]) throws Exception;
	
}
