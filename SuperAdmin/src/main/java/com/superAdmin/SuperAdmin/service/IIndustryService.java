package com.superAdmin.SuperAdmin.service;

import com.superAdmin.SuperAdmin.entity.request.IndustryRequest;
import org.springframework.data.domain.Pageable;

public interface IIndustryService {

    Object saveOrUpdateIndustry(IndustryRequest industryRequest);

    Object getAllIndustry(Pageable pageable);

    Object deleteIndustry(Long id);
}
