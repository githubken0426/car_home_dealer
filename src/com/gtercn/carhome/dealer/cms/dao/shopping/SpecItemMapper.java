package com.gtercn.carhome.dealer.cms.dao.shopping;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gtercn.carhome.dealer.cms.entity.shopping.SpecItem;

@Repository
public interface SpecItemMapper {

    SpecItem selectByPrimaryKey(String id);

    /**
     * 根据specItemIds,拼接规格名、值
     * @param specItemIds
     * @return
     */
    List<String> selectConcatSpecItems(List<String> specItemIds);
}