package com.superAdmin.SuperAdmin.service;

import com.superAdmin.SuperAdmin.entity.request.LogInRequest;
import com.superAdmin.SuperAdmin.entity.request.SuperAdminRequest;

public interface ISuperAdminService {

    Object signUp(SuperAdminRequest superAdminRequest);

    void sendEmail(String to, String subject, String text);

    Object logIn(LogInRequest logIn);

    Object checkOtp(LogInRequest logIn);
}
