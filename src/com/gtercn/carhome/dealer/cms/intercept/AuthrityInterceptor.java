package com.gtercn.carhome.dealer.cms.intercept;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
 

import com.gtercn.carhome.dealer.cms.entity.DealerUser;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 拦截器
 * 
 * @author Administrator
 * 
 */
public class AuthrityInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		String url = ServletActionContext.getRequest().getRequestURL()
				.toString();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		Map<String, Object> session = ActionContext.getContext().getSession();
		// 对登录与注销请求直接放行,不予拦截
		if ( url.indexOf("logDealOut.action") != -1
				|| url.indexOf("logindeal.action") != -1) {
			return invocation.invoke();
		} else {
			// 验证Session是否过期
			if (!ServletActionContext.getRequest().isRequestedSessionIdValid()) {
				return Action.INPUT;
			} else {
				DealerUser loginUser=(DealerUser) session.get("dealer_user");
				if (loginUser!=null) {
					return invocation.invoke();
				} else {
					return Action.INPUT;// 尚未登录,跳转至登录页面
				}
			}
		}
	}
}
