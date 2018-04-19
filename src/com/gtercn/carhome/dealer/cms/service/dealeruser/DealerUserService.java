package com.gtercn.carhome.dealer.cms.service.dealeruser;

import com.gtercn.carhome.dealer.cms.entity.DealerUser;



public interface DealerUserService {

	/**
	 * 登陆
	 * @param user
	 * @return
	 */
	public DealerUser userLogin(DealerUser user) throws Exception;
	
	public void changePassword(DealerUser user);
}
