<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/jsp/common/common.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
response.flushBuffer();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>修改</title>
	<link rel="stylesheet" href="<%=basePath %>/css/pubmain.css" />
	<link href="<%=basePath %>/css/commen.css" rel="stylesheet" type="text/css"/>
	<link href="<%=basePath %>/css/global.css" rel="stylesheet" type="text/css"/>
	<link href="<%=basePath %>/js/webuploader/webuploader.css" rel="stylesheet" type="text/css"/>
    <link href="<%=basePath %>/js/webuploader/preview.css" rel="stylesheet" type="text/css"/>
    
	<script type="text/javascript" src="<%=basePath %>/js/jquery1.9.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/layer/layer.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/webuploader/webuploader.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/webuploader/previewSmall.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/webuploader/previewBig.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/webuploader/previewDetail.js"></script>
<script type="text/javascript">
	function deleteImg(type,index){
		$("#"+type+"Del_"+index).remove();
		$("#"+type+"Src_"+index).remove();
		$("#"+type+"Input_"+index).remove();
	}
	
   	//添加
	function addSubmit(){
   		var goodsTitle=$.trim($("#goodsTitle").val());
		if(!goodsTitle){
			$("#goodsTitle").focus();
			layer.tips('请输入商品标题！', '#goodsTitle');
			return false;
   		} 
		$("#addForm").submit();	
	}
    //返回
  	function turnBack(){
  		$("#backForm").submit();
  	}
    
  </script>
</head>
<body>

<div id="middle">
	<div class="right"  id="mainFrame">
		<form action="${pageContext.request.contextPath}/goods_update.action" method="post" id="addForm">
			<div class="content-box">
				<div class="content-box-header">
			    	<span class="now_location">当前位置:</span>修改
			        <div class="clear"></div>
			    </div>
		   		<div style="margin:0 auto; margin:10px;">
	            	<table class="table table-bordered" >
						<!-- 数据录入区  -->
						<tr>
							<td width="10%" align="right" nowrap="nowrap" bgcolor="#f1f1f1" height="40px">商品品牌：</td>
							<td width="40%">
								<input type="hidden" name="entity.id" value="${entity.id}" />
								<input type="hidden" name="entity.categoryId" value="${entity.categoryId}" />
								<select id="brandId" name="entity.brandId" style="height:25px;margin-left:30px;width:200px;">
									<c:forEach var="brand" items="${brandList}">
										<option value="${brand.id }" <c:if test="${entity.brandId==brand.id }">selected='selected'</c:if>>
											${brand.cnName}
										</option>
									</c:forEach>
								</select>
							</td>
							<td width="10%"  align="right" nowrap="nowrap" bgcolor="#f1f1f1" height="40px">服务类型:</td>
							<td width="40%" >
								<select name="entity.serviceType" style="height:25px;margin-left:30px;width:200px;">
									<option value="5100" <c:if test="${entity.serviceType=='5100' }">selected='selected'</c:if>>修理服务</option>
									<option value="4100" <c:if test="${entity.serviceType=='4100' }">selected='selected'</c:if>>洗车服务</option>
									<option value="6100" <c:if test="${entity.serviceType=='6100' }">selected='selected'</c:if>>保养服务</option>
									<option value="7100" <c:if test="${entity.serviceType=='7100' }">selected='selected'</c:if>>轮胎服务</option>
								</select>
							</td>
						</tr>
						<c:forEach var="spec" items="${specList}">
						<tr>
							<td align="right" nowrap="nowrap" bgcolor="#f1f1f1" height="40px">${spec.name}：</td>
							<td colspan="3">
								<c:forEach var="specItem" items="${spec.items}" varStatus="index">
									<c:if test="${index.index % 8==0}"><br/></c:if>
									<label style="display: inline;<c:if test="${index.index % 8==0}">margin-left: 25px;</c:if>" >
			                			<input type="radio" name="${specItem.specId}" value="${specItem.id}" 
				                			<c:forEach var='realtion' items='${relationLst}'>
				                				<c:if test='${realtion.specItemId==specItem.id }'>checked='checked'</c:if>
				                			</c:forEach>
			                			/>
			                			<span>${specItem.item} </span>
			                		</label>
								</c:forEach>
							</td>
						</tr>
						</c:forEach>
						
						<tr>
							<td align="right" nowrap="nowrap" bgcolor="#f1f1f1" height="40px">商品标题：</td>
							<td colspan="3">
								<input id="goodsTitle" name="entity.goodsTitle" value="${ entity.goodsTitle}" type="text" style="margin-left: 30px;width:60%;" />
							</td>
						</tr>
						
						<tr>
							<td align="right" nowrap="nowrap" bgcolor="#f1f1f1" height="40px">原价：</td>
							<td>
								<input name="entity.primePrice" value="${ entity.primePrice}" type="text" style="margin-left: 30px;width:200px;" />(元)
							</td>
							<td align="right" nowrap="nowrap" bgcolor="#f1f1f1" height="40px">促销价：</td>
							<td>
								<input name="entity.promotionPrice" value="${entity.promotionPrice}"type="text" style="margin-left: 30px;width:200px;" />(元)
							</td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap" bgcolor="#f1f1f1" height="40px">进货价：</td>
							<td>
								<input name="entity.costPrice" value="${entity.costPrice}"type="text" style="margin-left: 30px;width:200px;" />(元)
							</td>
							<td align="right" nowrap="nowrap" bgcolor="#f1f1f1" height="40px">积分抵值：</td>
							<td>
								<input name="entity.score" value="${entity.score}" type="text" style="margin-left: 30px;width:200px;" />
							</td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap" bgcolor="#f1f1f1" height="40px">商品重量：</td>
							<td>
								<input name="entity.weight" value="${entity.weight}" type="text" style="margin-left: 30px;width:200px;" />(kg)
							</td>
							<td align="right" nowrap="nowrap" bgcolor="#f1f1f1" height="40px">商品产地：</td>
							<td>
								<input name="entity.productArea" value="${entity.productArea}" type="text" style="margin-left: 30px;width:200px;" />
							</td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap" bgcolor="#f1f1f1" height="40px">库存：</td>
							<td>
								<input name="entity.stock" value="${entity.stock}" type="text" style="margin-left: 30px;width:200px;" />
							</td>
							<td align="right" nowrap="nowrap" bgcolor="#f1f1f1" height="40px">已售数量：</td>
							<td>
								<input name="entity.soldNumber" value="${entity.soldNumber}" type="text" style="margin-left: 30px;width:200px;" />
							</td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap" bgcolor="#f1f1f1" height="40px">设置搜索标签：</td>
							<td>
								<input name="entity.searchTag" value="${entity.searchTag}"  type="text" style="margin-left: 30px;width:200px;" />
							</td>
							<td align="right" nowrap="nowrap" bgcolor="#f1f1f1" height="40px">商品状态：</td>
							<td>
								<c:choose>
									<c:when test="${entity.status=='0' }">
										<label style="margin-left: 35px; display: inline;">
					                		<input type="radio" name="entity.status" checked="checked" value="0" style="margin:0px"/>
					                		<span style="margin-left: 10px;">上架</span>
					                	</label>
					                	<label style="margin-left: 25px;display: inline;">
					                		<input type="radio" name="entity.status" value="1" style="margin:0px"/>
					                		<span style="margin-left: 10px;">下架</span>
					                	</label>
									</c:when>
									<c:otherwise>
										<label style="margin-left: 35px; display: inline;">
					                		<input type="radio" name="entity.status" value="0" style="margin:0px"/>
					                		<span style="margin-left: 10px;">上架</span>
					                	</label>
					                	<label style="margin-left: 25px;display: inline;">
					                		<input type="radio" name="entity.status" value="1" checked="checked" style="margin:0px"/>
					                		<span style="margin-left: 10px;">下架</span>
					                	</label>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap" bgcolor="#f1f1f1" height="40px">热门产品：</td>
							<td>
								<c:choose>
									<c:when test="${entity.isHot=='Y' }">
										<label style="margin-left: 35px; display: inline;">
					                		<input type="radio" name="entity.isHot" value="Y" checked="checked"  style="margin:0px"/>
					                		<span style="margin-left: 10px;">是</span>
					                	</label>
					                	<label style="margin-left: 25px;display: inline;">
					                		<input type="radio" name="entity.isHot" value="N"  style="margin:0px"/>
					                		<span style="margin-left: 10px;">否</span>
					                	</label>
									</c:when>
									<c:otherwise>
										<label style="margin-left: 35px; display: inline;">
					                		<input type="radio" name="entity.isHot" value="Y" style="margin:0px"/>
					                		<span style="margin-left: 10px;">是</span>
					                	</label>
					                	<label style="margin-left: 25px;display: inline;">
					                		<input type="radio" name="entity.isHot" value="N" checked="checked"  style="margin:0px"/>
					                		<span style="margin-left: 10px;">否</span>
					                	</label>
									</c:otherwise>
								</c:choose>
							</td>
							<td align="right" nowrap="nowrap" bgcolor="#f1f1f1" height="40px">新品：</td>
							<td>
								<c:choose>
									<c:when test="${entity.isHot=='Y' }">
										<label style="margin-left: 35px; display: inline;">
					                		<input type="radio" name="entity.isNew" checked="checked" value="Y" style="margin:0px"/>
					                		<span style="margin-left: 10px;">是</span>
					                	</label>
					                	<label style="margin-left: 25px;display: inline;">
					                		<input type="radio" name="entity.isNew" value="N" style="margin:0px"/>
					                		<span style="margin-left: 10px;">否</span>
					                	</label>
									</c:when>
									<c:otherwise>
										<label style="margin-left: 35px; display: inline;">
					                		<input type="radio" name="entity.isNew" value="Y" style="margin:0px"/>
					                		<span style="margin-left: 10px;">是</span>
					                	</label>
					                	<label style="margin-left: 25px;display: inline;">
					                		<input type="radio" name="entity.isNew" value="N" checked="checked" style="margin:0px"/>
					                		<span style="margin-left: 10px;">否</span>
					                	</label>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap" bgcolor="#f1f1f1" height="40px">商品简介：</td>
							<td colspan="3">
								<textarea name="entity.goodsSynopsis" 
									style="height:50px;margin-left: 30px;width:60%;">${entity.goodsSynopsis }</textarea>
							</td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap" bgcolor="#f1f1f1" height="40px">商品描述：</td>
							<td colspan="3">
								<textarea name="entity.goodsDescription" style="height:70px;margin-left:30px;width:60%;">${entity.goodsDescription }</textarea>
							</td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap" bgcolor="#f1f1f1" height="40px">商品小图：</td>
							<td colspan="3">
								 <div style="margin-left:45px;margin-top:10px;width:100%;margin-bottom:10px;">
								 <c:forEach var="small" items="${entity.smallPictureList}" varStatus="index">
										<img onclick="deleteImg('small',${index.index})" id="smallDel_${index.index}" src="${pageContext.request.contextPath}/js/webuploader/images/delete.png"
								        	style="width:24px;height:24px;position: absolute;cursor:pointer;" />
										<img id="smallSrc_${index.index}" src="${small}" style="width:110px;height:110px;margin-right:3px;"/>
										<input id="smallInput_${index.index}" type="hidden" value="${small}" name="smallPicture"/>
									</c:forEach>
							    </div>
								<div class="container"  style="margin-left: 30px">
						            <div id="uploaderSmall" class="uploader">
						                <div class="queueList">
						                    <div id="dndAreaSmall" class="placeholder">
						                        <div id="filePickerSmall"></div>
						                    </div>
						                </div>
						                <div class="statusBar" style="display:none;">
						                    <div class="progress">
						                        <span class="text">0%</span>
						                        <span class="percentage"></span>
						                    </div>
						                    <div class="info"></div>
						                    <div class="btns">
						                        <div id="filePickerSmall2" class="filePicker2"></div>
						                        <div class="uploadBtn">开始上传</div>
						                    </div>
						                </div>
						            </div>
						        </div>
							</td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap" bgcolor="#f1f1f1" height="40px">商品大图：</td>
							<td colspan="3">
								<div style="margin-left:45px;margin-top:10px;width:100%;margin-bottom:10px;">
								 	<c:forEach var="big" items="${entity.bigPictureList}" varStatus="index">
									<img onclick="deleteImg('big',${index.index})" id="bigDel_${index.index}" src="${pageContext.request.contextPath}/js/webuploader/images/delete.png"
								        	style="width:24px;height:24px;position: absolute;cursor:pointer;" />
										<img id="bigSrc_${index.index}" src="${big}" style="width:110px;height:110px;margin-right:3px;"/>
										<input id="bigInput_${index.index}" type="hidden" value="${big}" name="bigPicture"/>
									</c:forEach>
							    </div>
								<div class="container"  style="margin-left: 30px">
						            <div id="uploaderBig" class="uploader">
						                <div class="queueList">
						                    <div id="dndAreaBig" class="placeholder">
						                        <div id="filePickerBig"></div>
						                    </div>
						                </div>
						                <div class="statusBar" style="display:none;">
						                    <div class="progress">
						                        <span class="text">0%</span>
						                        <span class="percentage"></span>
						                    </div>
						                    <div class="info"></div>
						                    <div class="btns">
						                        <div id="filePickerBig2" class="filePicker2"></div>
						                        <div class="uploadBtn">开始上传</div>
						                    </div>
						                </div>
						            </div>
						        </div>
							</td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap" bgcolor="#f1f1f1" height="40px">商品详情图：</td>
							<td colspan="3">
								<div style="margin-left:45px;margin-top:10px;width:100%;margin-bottom:10px;">
								 	<c:forEach var="detail" items="${entity.detailPictureList}" varStatus="index">
										<img onclick="deleteImg('detail',${index.index})" id="detailDel_${index.index}" src="${pageContext.request.contextPath}/js/webuploader/images/delete.png"
								        	style="width:24px;height:24px;position: absolute;cursor:pointer;" />
										<img id="detailSrc_${index.index}" src="${detail}" style="width:110px;height:110px;margin-right:3px;"/>
										<input id="detailInput_${index.index}" type="hidden" value="${detail}" name="detailPicture"/>
									</c:forEach>
							    </div>
								<div class="container"  style="margin-left: 30px">
						            <div id="uploaderDetail" class="uploader">
						                <div class="queueList">
						                    <div id="dndAreaDetail" class="placeholder">
						                        <div id="filePickerDetail"></div>
						                    </div>
						                </div>
						                <div class="statusBar" style="display:none;">
						                    <div class="progress">
						                        <span class="text">0%</span>
						                        <span class="percentage"></span>
						                    </div>
						                    <div class="info"></div>
						                    <div class="btns">
						                        <div id="filePickerDetail2" class="filePicker2"></div>
						                        <div class="uploadBtn">开始上传</div>
						                    </div>
						                </div>
						            </div>
						        </div>
							</td>
						</tr>
	            	</table>
	            	 <table  class="margin-bottom-20 table  no-border" style="margin-top:20px;">
			              <tr>
			     	         <td align="right">
			     	         	<input type="button" value="保存" class="btn btn-info " style="width:80px;" onclick="addSubmit()" />
			     	         </td>
			     	         <td align="center">
			     	         	<input type="button" value="返回" class="btn btn-info " style="width:80px;"  onclick="turnBack()"/>
			     	         </td>
			     	         <td></td>
			             </tr>
			        </table>
	         	</div> 
			</div>
		</form>
			
		<!-- 返回，记录列表页数据 -->
		<form id="backForm" method="post" action="${pageContext.request.contextPath}/goods_list.action">
			<input type="hidden" name="pno" value="${currentIndex}" />
			<input type="hidden" name="categoryId" value="${categoryId}" />
			<input type="hidden" name="brandId" value="${brandId}" />
			<input type="hidden" name="title" value="${title}" />
			<input type="hidden" name="beginTime" value="${beginTime}" />
			<input type="hidden" name="endTime" value="${endTime}" />
		</form>
	</div>
</div>
</body>
</html>
