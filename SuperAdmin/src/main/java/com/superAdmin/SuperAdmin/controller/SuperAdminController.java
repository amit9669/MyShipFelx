package com.superAdmin.SuperAdmin.controller;

import com.superAdmin.SuperAdmin.entity.request.LogInRequest;
import com.superAdmin.SuperAdmin.entity.request.SuperAdminRequest;
import com.superAdmin.SuperAdmin.entity.response.CustomException;
import com.superAdmin.SuperAdmin.service.ISuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/superAdmin")
public class SuperAdminController {

    @Autowired
    private ISuperAdminService iSuperAdminService;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUpSuperAdmin(@RequestBody SuperAdminRequest superAdminRequest){
        return new ResponseEntity<>(iSuperAdminService.signUp(superAdminRequest), HttpStatus.CREATED);
    }

    @PostMapping("/logIn")
    public ResponseEntity<?> logInSuperAdmin(@RequestBody LogInRequest logIn){
        try{
            return new ResponseEntity<>(iSuperAdminService.logIn(logIn),HttpStatus.OK);
        }catch(CustomException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/checkOTP")
    public ResponseEntity<?> checkOtp(@RequestBody LogInRequest logIn){
        try{
            return new ResponseEntity<>(iSuperAdminService.checkOtp(logIn),HttpStatus.OK);
        }catch(CustomException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
