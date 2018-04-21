package com.gtercn.carhome.dealer.cms.service.shopping.expert;

import java.util.List;
import java.util.Map;

import com.gtercn.carhome.dealer.cms.entity.APIUser;
import com.gtercn.carhome.dealer.cms.entity.shopping.Expert;

public interface ExpertService {
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
	
    /**
     * 通过用户id,达人id获取达人
     * 用户一对一达人
     * @param map
     * @return
     * 2017-2-22 下午01:53:30
     */
    int getExcludeExpert(Map<String,Object> map);
    
    Expert selectByPrimaryKey(String id);
    int deleteBatch(String []ids);
    public int registerUserAndExpertShop(Expert o,APIUser user,boolean isAdd);
    int updateShopExpert(Expert record);
}