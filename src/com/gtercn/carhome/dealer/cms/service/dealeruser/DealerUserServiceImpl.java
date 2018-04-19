package com.gtercn.carhome.dealer.cms.service.dealeruser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gtercn.carhome.dealer.cms.dao.DealerUserDao;
import com.gtercn.carhome.dealer.cms.entity.DealerUser;
import com.gtercn.carhome.dealer.cms.util.ComputerIp;


@Service(value = "dealerUserService")
public class DealerUserServiceImpl implements DealerUserService {
	@Autowired
	private DealerUserDao userDao;

	/**
	 * 用户登录后保存登陆信息
	 */
	@SuppressWarnings({ "finally" })
	@Override
	public DealerUser userLogin(DealerUser user) throws Exception {
		DealerUser loginUser = userDao.userLogin(user);
		try {
			String loginIp = ComputerIp.getComputerIp();
			loginUser.setLoginIp(loginIp);
			loginUser.setLoginNum(loginUser.getLoginNum() + 1);
			userDao.saveLoginInfo(loginUser);// 保存登陆信息
		} catch (Exception e) {
			throw new RuntimeException();
		}finally{
			//即使登陆信息保存失败，登陆也正常
			return loginUser;
		}
	}

	@Override
	public void changePassword(DealerUser user) {
		userDao.changePassword(user);	
	}

}
