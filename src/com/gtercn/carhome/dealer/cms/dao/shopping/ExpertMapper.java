package com.gtercn.carhome.dealer.cms.dao.shopping;

import java.util.List;
import java.util.Map;

import com.gtercn.carhome.dealer.cms.entity.shopping.Expert;

public interface ExpertMapper {
	/**
	 * 查询所有数据
	 * @param map
	 * @return
	 * 2017-2-21 下午02:48:14
	 */
	List<Expert> queryAllData(Map<String, Object> map);
	/**
	 * 查询所有数据条数
	 * @param map
	 * @return
	 * 2017-2-21 下午02:48:20
	 */
	public int getTotalCount(Map<String, Object> map);
	Expert selectByPrimaryKey(String id);
	
    int insertShopExpert(Expert record);
    int updateShopExpert(Expert record);
    int deleteBatch(String[] ids);
    int getExcludeExpert(Map<String,Object> map);
}