package com.msf.service.Impl;

import com.msf.entity.MsfCompany;
import com.msf.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserToken {

    @Autowired
    private MsfCompanyService msfCompanyService;

    public MsfCompany getCompanyFromToken() {
        try {
           MsfCompany msfCompany = (MsfCompany) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println(msfCompany.getUsername());
            String email = msfCompany.getUsername();

            return (MsfCompany) msfCompanyService.findByEmail(email);
        } catch (Exception e) {
            throw new CustomException("Error retrieving company from token: " + e.getMessage());
        }
    }
}
