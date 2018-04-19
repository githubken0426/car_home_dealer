package com.gtercn.carhome.dealer.cms.dao;

import org.springframework.stereotype.Repository;

import com.gtercn.carhome.dealer.cms.entity.DealerUser;

@Repository
public interface DealerUserDao {
	/**
	 * 登陆
	 * @param user
	 * @return
	 */
	public DealerUser userLogin(DealerUser user);
	
	/**
	 * 保存用户的登陆信息
	 * @param user
	 * @return
	 */
	public int saveLoginInfo(DealerUser user);
	
	public void changePassword(DealerUser user);
}
