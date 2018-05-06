package com.gtercn.carhome.dealer.cms.service.shopping.order;

import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gtercn.carhome.dealer.cms.ApplicationConfig;
import com.gtercn.carhome.dealer.cms.dao.ShopMapper;
import com.gtercn.carhome.dealer.cms.dao.shopping.LogisticsMapper;
import com.gtercn.carhome.dealer.cms.dao.shopping.OrderMapper;
import com.gtercn.carhome.dealer.cms.entity.Shop;
import com.gtercn.carhome.dealer.cms.entity.shopping.Address;
import com.gtercn.carhome.dealer.cms.entity.shopping.Logistics;
import com.gtercn.carhome.dealer.cms.entity.shopping.LogisticsDetail;
import com.gtercn.carhome.dealer.cms.entity.shopping.Order;
import com.gtercn.carhome.dealer.cms.util.AliSMSUtils;
import com.gtercn.carhome.dealer.cms.util.CommonUtil;

@Service(value = "logisticsService")
public class LogisticsServiceImpl implements LogisticsService {
	@Autowired
	private LogisticsMapper dao;
	@Autowired
	private OrderMapper orderDao;
	@Autowired
	private ShopMapper shopDao;

	@Override
	public Logistics selectLogisticsByOrder(String orderId) {
		return dao.selectLogisticsByOrder(orderId);
	}

	@Override
	public Logistics selectByPrimaryKey(String id) {
		return dao.selectByPrimaryKey(id);
	}

	@Override
	public int delivery(Logistics logistics, String addressId) {
		String orderId = logistics.getOrderId();
		Order order = orderDao.selectByPrimaryKey(orderId);
		if (order == null)
			return 0;
		String logisticsId = CommonUtil.getUID();
		logistics.setId(logisticsId);
		if (order.getFlag() == 0) {
			Address address = dao.selectAddressByPrimaryKey(addressId);
			if (address != null) {
				StringBuffer sb = new StringBuffer();
				logistics.setRealname(address.getName());
				logistics.setTelphone(address.getPhone());
				logistics.setPostalCode(address.getPostalCode());
				String province = address.getProvince();
				if (StringUtils.isNotBlank(province))
					sb.append(province);
				String city = address.getCity();
				if (StringUtils.isNotBlank(city))
					sb.append(city);
				String dis = address.getDistrict();
				if (StringUtils.isNotBlank(dis))
					sb.append(dis);
				String del = address.getAddress();
				if (StringUtils.isNotBlank(del))
					sb.append(del);
				logistics.setAddress(sb.toString());
			}
		} else {
			Shop shop = shopDao.selectByPrimaryKey(addressId);
			if (shop != null) {
				StringBuffer sb = new StringBuffer();
				logistics.setRealname(shop.getShopName());
				logistics.setTelphone(shop.getTelNumberList());
				String province = shop.getProvince();
				if (StringUtils.isNotBlank(province))
					sb.append(province);
				String city = shop.getCity();
				if (StringUtils.isNotBlank(city))
					sb.append(city);
				String dis = shop.getDistrict();
				if (StringUtils.isNotBlank(dis))
					sb.append(dis);
				String del = shop.getDetailAddress();
				if (StringUtils.isNotBlank(del))
					sb.append(del);
				logistics.setAddress(sb.toString());
			}
		}
		// 更新订单
		orderDao.updateOrderLogistics(orderId, logisticsId);
		// 插入物流信息
		dao.add(logistics);
		LogisticsDetail detail = new LogisticsDetail();
		detail.setId(CommonUtil.getUID());
		detail.setLogisticsId(logisticsId);
		detail.setDescription(ApplicationConfig.LOGISTICS_INFO);
		dao.addDetail(detail);

		// 發貨成功，发送短信
		/*SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		String arriveDate = CommonUtil.getDaysAfterTime(ApplicationConfig.ARRIVE_DAY, format);
		String serviceDate = CommonUtil.getDaysAfterTime(ApplicationConfig.SERVICE_DAY, format);
		String orderNo = order.getOrderNo();
		if (order.getFlag() == 1) {// 经销商
			AliSMSUtils.sendUserDealerMsg(order.getTelphone(), orderNo, order.getShopName(), arriveDate, serviceDate);
			String phone = order.getDealerTelphone();
			String dealerPhone = CommonUtil.matcherPhone(phone);
			AliSMSUtils.sendDelaerMsg(dealerPhone, order.getUserName(), orderNo, arriveDate);
		} else {
			AliSMSUtils.sendUserSelfMsg(order.getTelphone(), orderNo, arriveDate);
		}*/
		return 1;
	}

}
