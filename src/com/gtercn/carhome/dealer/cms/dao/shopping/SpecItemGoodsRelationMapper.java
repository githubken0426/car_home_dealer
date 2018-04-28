package com.gtercn.carhome.dealer.cms.dao.shopping;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gtercn.carhome.dealer.cms.entity.shopping.SpecItemGoodsRelation;


@Repository
public interface SpecItemGoodsRelationMapper {

    int insert(List<SpecItemGoodsRelation> record);

    List<SpecItemGoodsRelation> selectByGoodsId(String goodsId);

    int deleteByGoodsId(String goodsId);
}