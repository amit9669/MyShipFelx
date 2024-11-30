package com.superAdmin.SuperAdmin.service.Impl;

import com.superAdmin.SuperAdmin.entity.Industry;
import com.superAdmin.SuperAdmin.entity.request.IndustryRequest;
import com.superAdmin.SuperAdmin.entity.request.PageDTO;
import com.superAdmin.SuperAdmin.repository.IndustryRepository;
import com.superAdmin.SuperAdmin.service.IIndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndustryService implements IIndustryService{

    @Autowired
    private IndustryRepository industryRepository;

    @Override
    public Object saveOrUpdateIndustry(IndustryRequest industryRequest) {
        if(industryRepository.existsById(industryRequest.getIndustryId())){
            Industry industry = industryRepository.findById(industryRequest.getIndustryId()).get();
            industry.setDescription(industryRequest.getDescription());
            industry.setName(industryRequest.getName());
            industry.setIsActive(true);
            industry.setIsDeleted(false);
            industryRepository.save(industry);
            return "Updated Successfully!!";
        }else{
            Industry industry = new Industry();
            industry.setDescription(industryRequest.getDescription());
            industry.setName(industryRequest.getName());
            industry.setIsActive(true);
            industry.setIsDeleted(false);
            industryRepository.save(industry);
            return "Inserted Successfully!!!";
        }
    }

    @Override
    public Object getAllIndustry(Pageable pageable) {
        List<Industry> all = industryRepository.findAll();
        Page<Industry> industryPage = new PageImpl<>(all);
        return new PageDTO(industryPage.getContent(),industryPage.getTotalElements(),industryPage.getTotalPages(),industryPage.getNumberOfElements());
    }

    @Override
    public Object deleteIndustry(Long id) {
        if(industryRepository.existsById(id)){
            Industry industry = industryRepository.findById(id).get();
            if(industry.getIsActive()){
                industry.setIsActive(false);
                industry.setIsDeleted(true);
            }
            industryRepository.save(industry);
            return "Deleted Successfully!!";
        }else{
            return "Id not Exists!!";
        }
    }
}