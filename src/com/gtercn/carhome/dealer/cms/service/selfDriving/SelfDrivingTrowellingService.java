package com.gtercn.carhome.dealer.cms.service.selfDriving;

import java.util.List;
import java.util.Map;

import com.gtercn.carhome.dealer.cms.entity.SelfDrivingTrowelling;

public interface SelfDrivingTrowellingService {

	/**
	 * 查询
	 * 
	 * @return
	 */
	public List<SelfDrivingTrowelling> queryAllData(Map<String, Object> map);

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
	public SelfDrivingTrowelling getDataById(String id) throws Exception;

	/**
	 * 新增
	 * 
	 * @param o
	 * @return
	 */
	public int addData(SelfDrivingTrowelling o) throws Exception;

	/**
	 * 修改
	 * 
	 * @param o
	 * @return
	 */
	public int updateData(SelfDrivingTrowelling o) throws Exception;

	/**
	 * 删除
	 * 
	 * @param o
	 * @return
	 */
	public void deleteData(String id) throws Exception;
	
	/**
     * 获取查看名单人数
     * @param map 参数
     * @return
     */
	public Integer getCount(String selfDrivingId);
}
