package com.gtercn.carhome.dealer.cms.dao;

import java.util.List;

import com.gtercn.carhome.dealer.cms.entity.ServiceEn;


public interface ServiceMapper {
    int deleteByPrimaryKey(String id);

    int insert(ServiceEn record);

    int insertSelective(ServiceEn record);

    ServiceEn selectByPrimaryKey(String id);
    
    List<ServiceEn> selectByShopId(String shopId);

    int updateByPrimaryKeySelective(ServiceEn record);

    int updateByPrimaryKey(ServiceEn record);
}