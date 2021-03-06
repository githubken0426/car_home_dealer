package com.gtercn.carhome.dealer.cms.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.gtercn.carhome.dealer.cms.ApplicationConfig;
import com.gtercn.carhome.dealer.cms.entity.APIUser;
import com.gtercn.carhome.dealer.cms.entity.DealerUser;
import com.gtercn.carhome.dealer.cms.entity.ExpertTop;
import com.gtercn.carhome.dealer.cms.entity.ExpertType;
import com.gtercn.carhome.dealer.cms.entity.write.WriteExpertTop;
import com.gtercn.carhome.dealer.cms.service.apiuser.APIUserService;
import com.gtercn.carhome.dealer.cms.service.expert.ExpertTopService;
import com.gtercn.carhome.dealer.cms.service.expertType.ExpertTypeService;
import com.gtercn.carhome.dealer.cms.util.CommonUtil;
import com.gtercn.carhome.dealer.cms.util.MyArrayList;
import com.gtercn.carhome.dealer.cms.util.UploadFtpFileTools;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 达人圈
 * 达人相关
 * 
 * @author ken
 * 2017-2-23 上午09:55:38
 */
public class ExpertTopAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	@Autowired
	private ExpertTopService expertTopService;
	@Autowired
	private ExpertTypeService expertTypeService;
	@Autowired
	private APIUserService apiUserService;
	
	private APIUser apiUser;
	private WriteExpertTop expertTop;
	public APIUser getApiUser() {
		return apiUser;
	}
	public void setApiUser(APIUser apiUser) {
		this.apiUser = apiUser;
	}
	public WriteExpertTop getExpertTop() {
		return expertTop;
	}

	public void setExpertTop(WriteExpertTop expertTop) {
		this.expertTop = expertTop;
	}

	/**
	 * 检索数据
	 * 
	 * @return
	 */
	public String listData() {
		Map<String, Object> map = new HashMap<String, Object>();
		ActionContext context = ActionContext.getContext();
		Map<String,Object> session=context.getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			DealerUser user=(DealerUser) session.get("dealer_user");
			String cityCode = user != null ? user.getCityCode() : "";
			map.put("cityCode", cityCode);
			// 接收查询参数
			String expertName = request.getParameter("expertName");
			if (expertName != null && !expertName.equals("")) {
				expertName = URLDecoder.decode(expertName, "UTF-8");
				map.put("expertName", expertName);
			}
			String deleteFlag = request.getParameter("deleteFlag");
			if (deleteFlag != null && !deleteFlag.equals("-1"))
				map.put("deleteFlag", deleteFlag);
			String topType = request.getParameter("topType");
			if (topType != null && !topType.equals("-1"))
				map.put("topType", topType);
			int pageSize = ApplicationConfig.PAGE_SIZE;// 每页显示数据条
			int totalCount = expertTopService.getTotalCount(map);
			int currentIndex = 0;// 当前页
			String index = request.getParameter("pno");
			if (index != null && index != "") {
				currentIndex = Integer.valueOf(index);
			} else {
				currentIndex = 1;
			}
			int totalPages = (totalCount % pageSize == 0) ? (totalCount / pageSize)
					: (totalCount / pageSize + 1);
			map.put("beginResult", (currentIndex - 1) * pageSize);
			map.put("pageSize", pageSize);
			List<ExpertTop> list = expertTopService.queryAllData(map);
			List<ExpertType> typeList = expertTypeService.queryAllData();
			context.put("topTtitle", typeList);
			context.put("ExpertTopList", list);
			context.put("totalPages", totalPages);
			context.put("totalCount", totalCount);
			context.put("currentIndex", currentIndex);

			context.put("expertName", expertName);
			context.put("deleteFlag", deleteFlag);
			context.put("topType", topType);
			context.put("ftpServerIp", ApplicationConfig.HTTP_PROTOCOL_IP);
		} catch (Exception e) {
			e.printStackTrace();
			return Action.ERROR;
		}
		return "list";
	}

	/**
	 * 添加数据(进入画面)
	 * 
	 * @return
	 */
	public String addDataPage() {
		ActionContext context = ActionContext.getContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			int currentIndex = 0;// 当前页
			String index = request.getParameter("backPageNo");// 返回，记录列表页数据
			if (index != null && index != "") {
				currentIndex = Integer.valueOf(index);
			} else {
				currentIndex = 1;
			}
			String expertName = request.getParameter("expertName");
			String deleteFlag = request.getParameter("deleteFlag");
			String topType = request.getParameter("topType");
			List<ExpertType> typeList = expertTypeService.queryAllData();

			context.put("currentIndex", currentIndex);
			context.put("expertName", expertName);
			context.put("deleteFlag", deleteFlag);
			context.put("topType", topType);
			context.put("typeList", typeList);
		} catch (Exception e) {
			e.printStackTrace();
			return Action.ERROR;
		}
		return "add";
	}

	/**
	 * 添加数据
	 * 
	 * @return
	 * @throws IOException 
	 */
	public String addData() throws IOException {
		ServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String,Object> session=ActionContext.getContext().getSession();
		MultiPartRequestWrapper multipartRequest = (MultiPartRequestWrapper) request;
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = null;
		InputStream userPortraitIn=null;
		InputStream in=null;
		InputStream expertPortraitIn=null;
		try {
			String userId = apiUser.getUserId();
			boolean isAdd=false;
			if(!StringUtils.isNotBlank(userId)){
				userId=CommonUtil.getUID();
				apiUser.setUserId(userId);
				String pwd=CommonUtil.gernateToMD5(apiUser.getPassword());
				apiUser.setPassword(pwd);
				isAdd=true;
			}
			//用户头像上传
			File[] userPortrait = multipartRequest.getFiles("userPortrait");
			if(userPortrait!=null){
				String[] portraitSavePath = { ApplicationConfig.FTP_ROOTPATH,
						ApplicationConfig.FTP_USERS_PATH, ApplicationConfig.FTP_AVATAR_PATH,userId };
				for (File file : userPortrait) {
					userPortraitIn = new FileInputStream(file);
					String portraitFileName = System.currentTimeMillis() + ".jpg";
					boolean bool = UploadFtpFileTools.uploadFile(portraitSavePath,
							portraitFileName, userPortraitIn);
					if (bool) {
						String dbPortaitSavePath = File.separator
								+ ApplicationConfig.FTP_ROOTPATH + File.separator
								+ ApplicationConfig.FTP_USERS_PATH + File.separator
								+ ApplicationConfig.FTP_AVATAR_PATH+ File.separator
								+ userId + File.separator + portraitFileName;
						apiUser.setAvatarUrl(dbPortaitSavePath);
					}
				}
			}
			//达人
			String expertId = CommonUtil.getUID();
			expertTop.setId(expertId);
			expertTop.setUserId(userId);
			List<String> displayList = new MyArrayList<String>();
			// 个人详细照片上传
			File[] displayPicture = multipartRequest.getFiles("displayPicture");
			if(displayPicture!=null){
				String[] displaySavePath = { ApplicationConfig.FTP_ROOTPATH,
						ApplicationConfig.FTP_EXPERT_PATH, expertId, "display" };
				for (File file : displayPicture) {
					in = new FileInputStream(file);
					String fileName = System.currentTimeMillis() + ".jpg";
					boolean bool = UploadFtpFileTools.uploadFile(displaySavePath,
							fileName, in);
					if (bool) {
						String dbSavePath = File.separator
								+ ApplicationConfig.FTP_ROOTPATH + File.separator
								+ ApplicationConfig.FTP_EXPERT_PATH
								+ File.separator + expertId + File.separator
								+ "display" + File.separator + fileName;
						displayList.add(dbSavePath);
					}
				}
			}
			expertTop.setExpertDisplayPicList(displayList.toString());
			// 头像上传
			File[] portraitFile = multipartRequest.getFiles("expertPortrait");
			if(portraitFile!=null){
				String[] portraitSavePath = { ApplicationConfig.FTP_ROOTPATH,
						ApplicationConfig.FTP_EXPERT_PATH, expertId, "portrait" };
				for (File file : portraitFile) {
					expertPortraitIn = new FileInputStream(file);
					String portraitFileName = System.currentTimeMillis() + ".jpg";
					boolean bool = UploadFtpFileTools.uploadFile(portraitSavePath,
							portraitFileName, expertPortraitIn);
					if (bool) {
						String dbPortaitSavePath = File.separator
								+ ApplicationConfig.FTP_ROOTPATH + File.separator
								+ ApplicationConfig.FTP_EXPERT_PATH
								+ File.separator + expertId + File.separator
								+ "portrait" + File.separator + portraitFileName;
						expertTop.setExpertPortraitUrl(dbPortaitSavePath);
					}
				}
			}
			DealerUser user=(DealerUser) session.get("dealer_user");
			String cityCode = user != null ? user.getCityCode() : "";
			expertTop.setCityCode(cityCode);
			expertTop.setExpertName(apiUser.getRealName());
			expertTop.setExpertTelNumber(apiUser.getLoginPhone());
			
			expertTopService.registerUserAndExpert(expertTop, apiUser, isAdd);
			writer = response.getWriter();
			writer.print("<script>alert('添加成功!');window.location.href='expertTop_listData.action';</script>");
		} catch (Exception e) {
			e.printStackTrace();
			writer.print("<script>alert('添加失败!');window.location.href='expertTop_listData.action';</script>");
		}finally{
			if(userPortraitIn!=null)
				userPortraitIn.close();
			if(in!=null)
				in.close();
			if(expertPortraitIn!=null)
				expertPortraitIn.close();
		}
		return null;
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String deleteData(){
		ServletRequest request = ServletActionContext.getRequest();
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			String ids[] = request.getParameterValues("id");
			expertTopService.deleteData(ids);
			writer.print("<script>alert('删除成功!');window.location.href='expertTop_listData.action';</script>");
		} catch (Exception e) {
			e.printStackTrace();
			writer.print("<script>alert('删除失败!');window.location.href='expertTop_listData.action';</script>");
		}
		return null;
	}

	/**
	 * 修改数据(进入画面)
	 * 
	 * @return
	 */
	public String updateDataPage(){
		ActionContext context = ActionContext.getContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String id = request.getParameter("id");
			int currentIndex = 0;// 当前页
			String index = request.getParameter("backPageNo");// 返回，记录列表页数据
			if (index != null && index != "") {
				currentIndex = Integer.valueOf(index);
			} else {
				currentIndex = 1;
			}
			String expertName = request.getParameter("expertName");
			String deleteFlag = request.getParameter("deleteFlag");
			String topType = request.getParameter("topType");
			
			List<ExpertType> typeList = expertTypeService.queryAllData();
			ExpertTop expert = expertTopService.getDataById(id);
			if(expert!=null){
				APIUser user=apiUserService.getUserById(expert.getUserId());
				context.put("apiUser",user);
				context.put("displayList",expert.getDisplayList());
			}
			context.put("expertTop", expert);
			context.put("typeList", typeList);
			//记录列表页查询及分页数据
			context.put("currentIndex", currentIndex);
			context.put("expertName", expertName);
			context.put("deleteFlag", deleteFlag);
			context.put("topType", topType);
			
			context.put("ftpServerIp", ApplicationConfig.HTTP_PROTOCOL_IP);
		} catch (Exception e) {
			e.printStackTrace();
			return Action.ERROR;
		}
		return "update";
	}

	/**
	 * 修改数据
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String updateData() throws Exception {
		ServletResponse response = ServletActionContext.getResponse();
		ServletRequest request=ServletActionContext.getRequest();
		MultiPartRequestWrapper multipartRequest = (MultiPartRequestWrapper) request;
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = null;
		InputStream in=null;
		InputStream expertPortraitIn=null;
		try {
			writer = response.getWriter();
			String id=expertTop.getId();
			String expertPortraitFlag=request.getParameter("expertPortraitFlag");
			if(StringUtils.isNotBlank(expertPortraitFlag))
				expertTop.setExpertPortraitUrl(expertPortraitFlag);
			//保存头像
			File[] portraitFile = multipartRequest.getFiles("expertPortrait");
			if(portraitFile!=null){
				String[] portraitSavePath = { ApplicationConfig.FTP_ROOTPATH,
						ApplicationConfig.FTP_EXPERT_PATH, id, "portrait" };
				for (File file : portraitFile) {
					expertPortraitIn= new FileInputStream(file);
					String portraitFileName = System.currentTimeMillis() + ".jpg";
					boolean bool = UploadFtpFileTools.uploadFile(portraitSavePath,
							portraitFileName, expertPortraitIn);
					if (bool) {
						String dbPortaitSavePath = File.separator
								+ ApplicationConfig.FTP_ROOTPATH + File.separator
								+ ApplicationConfig.FTP_EXPERT_PATH
								+ File.separator + id + File.separator
								+ "portrait" + File.separator + portraitFileName;
						expertTop.setExpertPortraitUrl(dbPortaitSavePath);
					}
				}
			}
			List<String> displayList = new MyArrayList<String>();
			String viewDisplay1Flag=request.getParameter("viewDisplay1Flag");
			if(StringUtils.isNotBlank(viewDisplay1Flag))
				displayList.add(viewDisplay1Flag);
			String viewDisplay2Flag=request.getParameter("viewDisplay2Flag");
			if(StringUtils.isNotBlank(viewDisplay2Flag))
				displayList.add(viewDisplay2Flag);
			String viewDisplay3Flag=request.getParameter("viewDisplay3Flag");
			if(StringUtils.isNotBlank(viewDisplay3Flag))
				displayList.add(viewDisplay3Flag);
			String viewDisplay4Flag=request.getParameter("viewDisplay4Flag");
			if(StringUtils.isNotBlank(viewDisplay4Flag))
				displayList.add(viewDisplay4Flag);
			String viewDisplay5Flag=request.getParameter("viewDisplay5Flag");
			if(StringUtils.isNotBlank(viewDisplay5Flag))
				displayList.add(viewDisplay5Flag);
			String viewDisplay6Flag=request.getParameter("viewDisplay6Flag");
			if(StringUtils.isNotBlank(viewDisplay6Flag))
				displayList.add(viewDisplay6Flag);
			
			// 个人详细照片上传
			File[] displayPicture = multipartRequest.getFiles("displayPicture");
			if(displayPicture!=null){
				String[] displaySavePath = { ApplicationConfig.FTP_ROOTPATH,
						ApplicationConfig.FTP_EXPERT_PATH, id, "display" };
				for (File file : displayPicture) {
					in = new FileInputStream(file);
					String fileName = System.currentTimeMillis() + ".jpg";
					boolean bool = UploadFtpFileTools.uploadFile(displaySavePath,
							fileName, in);
					if (bool) {
						String dbSavePath = File.separator
								+ ApplicationConfig.FTP_ROOTPATH + File.separator
								+ ApplicationConfig.FTP_EXPERT_PATH
								+ File.separator + id + File.separator
								+ "display" + File.separator + fileName;
						displayList.add(dbSavePath);
					}
				}
			}
			expertTop.setExpertDisplayPicList(displayList.toString());
			expertTopService.updateData(expertTop);
			writer.print("<script>alert('修改成功!');window.location.href='expertTop_listData.action';</script>");
		} catch (Exception e) {
			e.printStackTrace();
			writer.print("<script>alert('修改失败!');window.location.href='expertTop_listData.action';</script>");
		}finally{
			if(in!=null)
				in.close();
			if(expertPortraitIn!=null)
				expertPortraitIn.close();
		}
		return null;
	}

	/**
	 * 查询用户是否存在
	 * 
	 * @return
	 */
	public String getUserByLoginPhone() {
		Map<String, Object> map = new HashMap<String, Object>();
		ServletResponse response = ServletActionContext.getResponse();
		ServletRequest request = ServletActionContext.getRequest();
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			String loginPhone = request.getParameter("loginPhone");
			map.put("loginPhone", loginPhone);
			APIUser result = apiUserService.getUserByLoginPhone(map);
			String msg = "0";// 此账号不存在
			if (result != null) {
				String expertId = request.getParameter("expertId");
				if (expertId != null && !expertId.equals(""))
					map.put("expertId", expertId);
				map.put("userId", result.getUserId());
				int exp = expertTopService.getExcludeExpert(map);
				if (exp == 0)// 此账号未被绑定
					msg = "2";
				else
					msg = "1";// 此账号已被绑定
			}else
				result=new APIUser();
			result.setIsExpert(msg);
			JSONObject json=JSONObject.fromObject(result);
			writer.write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return Action.ERROR;
		} finally {
			writer.flush();
			writer.close();
		}
		return null;
	}
	
}
