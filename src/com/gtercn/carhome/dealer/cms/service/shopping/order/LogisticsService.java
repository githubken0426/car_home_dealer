package com.gtercn.carhome.dealer.cms.service.shopping.order;

import org.springframework.stereotype.Repository;

import com.gtercn.carhome.dealer.cms.entity.shopping.Logistics;

@Repository
public interface LogisticsService {
	/**
	 * 查询订单物流
	 * @param orderId 订单id
	 * @return
	 * @date 2018年1月15日 下午7:06:31
	 */
	Logistics selectLogisticsByOrder(String orderId);
    
    Logistics selectByPrimaryKey(String id);
    
    int delivery(Logistics logistics,String addressId);
}