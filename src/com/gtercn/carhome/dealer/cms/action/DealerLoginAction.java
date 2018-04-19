package com.gtercn.carhome.dealer.cms.action;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.gtercn.carhome.dealer.cms.entity.DealerUser;
import com.gtercn.carhome.dealer.cms.service.dealeruser.DealerUserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 经销商登陆
 * 
 * @author ken
 * 2017-3-21 上午09:07:18
 */
public class DealerLoginAction extends ActionSupport {

	private static final long serialVersionUID = -6017137602949951689L;
	
	@Autowired
	private DealerUserService dealerUserService;
	
	private DealerUser dealerUser;
	
	public DealerUser getDealerUser() {
		return dealerUser;
	}
	public void setDealerUser(DealerUser dealerUser) {
		this.dealerUser = dealerUser;
	}

	/**
	 * 经销商用户登录
	 * 
	 * @return
	 */
	public String logindeal() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		try {
			DealerUser loginUser = dealerUserService.userLogin(dealerUser);
			if (loginUser != null) {
				if (loginUser.getStatus() == 1) {
					ServletActionContext.getRequest().setAttribute("loginError", "用户被锁定,请联系管理员解锁！");
					return "input";
				}
				session.put("dealer_user", loginUser);
				return "success";
			} else {
				ServletActionContext.getRequest().setAttribute("loginError","用户名或密码错误！");
				return "input";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "input";
		}
	}
	/**
	 * 修改密码页面
	 * 
	 * @return
	 */
	public String changePasswordPage() {
		return "changePwdPage";
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	public String changePassword() {
		ServletRequest request = ServletActionContext.getRequest();
		ServletResponse response = ServletActionContext.getResponse();
		Map<String, Object> session = ActionContext.getContext().getSession();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			DealerUser login_user = (DealerUser) session.get("dealer_user");
			String newPassword = request.getParameter("newPassword");
			login_user.setPassword(DigestUtils.md5Hex(newPassword));
			dealerUserService.changePassword(login_user);
			writer.print("<script>alert('修改成功,请重新登录!');window.top.location.href='signin_logDealOut.action';</script>");
		} catch (Exception e) {
			e.printStackTrace();
			writer.print("<script>alert('修改失败!');';</script>");
		}
		return null;
	}
	
	/**
	 * 退出登录
	 * 
	 * @return
	 */
	public String logDealOut() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.remove("dealer_user");
		return "input";
	}
	
	/**
	 * 经销商
	 * 
	 * @return
	 */
	public String deal() {
		return "input";
	}
	
	public String turnBackDeal(){
		return "success";
	}
}
