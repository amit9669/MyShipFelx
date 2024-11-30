package com.superAdmin.SuperAdmin.service.Impl;

import com.superAdmin.SuperAdmin.entity.SuperAdmin;
import com.superAdmin.SuperAdmin.entity.request.LogInRequest;
import com.superAdmin.SuperAdmin.entity.request.SuperAdminRequest;
import com.superAdmin.SuperAdmin.jwt.AuthenticationService;
import com.superAdmin.SuperAdmin.jwt.JwtService;
import com.superAdmin.SuperAdmin.repository.SuperAdminRepository;
import com.superAdmin.SuperAdmin.service.ISuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SuperAdminService implements ISuperAdminService {

    private static final String regex = "^(\\+\\d{1,3}[- ]?)?\\d{10}$"; // Example regex

    @Autowired
    private SuperAdminRepository superAdminRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Object signUp(SuperAdminRequest superAdminRequest) {

        SuperAdmin superAdmin = new SuperAdmin();
        if (superAdminRepository.existsByEmail(superAdminRequest.getEmail())) {
            return "Email Already Exists!!";
        } else {
            superAdmin.setEmail(superAdminRequest.getEmail());
        }
        if (superAdminRepository.existsByMobNumber(superAdminRequest.getMobNumber())) {
            return "Mobile Number Already Exists!!";
        } else {
            if (superAdminRequest.getMobNumber().matches(regex)) {
                superAdmin.setMobNumber(superAdminRequest.getMobNumber());
            } else {
                return "Mobile number Should contain 10 Digits!!!";
            }
        }
        superAdmin.setFirstName(superAdminRequest.getFirstName());
        superAdmin.setLastName(superAdminRequest.getLastName());
        superAdmin.setGender(superAdminRequest.getGender());
        superAdmin.setPassword(passwordEncoder.encode(superAdminRequest.getPassword()));
        superAdminRepository.save(superAdmin);
        sendEmail(superAdminRequest.getEmail(), "Welcome MSF-Admin", "Your Email : " + superAdminRequest.getEmail() + "\n"
                + "Your Password : " + superAdminRequest.getPassword());
        return "Thank-You For Sign-Up! \n Check Your Email!1";
    }

    @Override
    public void sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(to);
            email.setSubject(subject);
            email.setText(text);
            javaMailSender.send(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object logIn(LogInRequest logIn) {
        if (superAdminRepository.existsByEmail(logIn.getEmail())) {
            SuperAdmin superAdmin = superAdminRepository.findByEmail(logIn.getEmail()).get();
            if (passwordEncoder.matches(logIn.getPassword(), superAdmin.getPassword())) {

                Authentication authenticate = authenticationService.authenticate(logIn);
                if (authenticate.isAuthenticated()) {
                    sendEmail(superAdmin.getEmail(), "OTP-Verification",
                            "Your OTP is : " + otpService.generateOTP(superAdmin.getEmail()));
                    return "LogIn Successfully!! Check Email for OTP Verification!";
                } else {
                    return "Email is Not Authenticated!!";
                }
            } else {
                return "password wrong";
            }
        } else {
            return "Email Invalid!!!";
        }
    }

    @Override
    public Object checkOtp(LogInRequest logIn) {
        if (superAdminRepository.existsByEmail(logIn.getEmail())) {
            if (otpService.getOtp(logIn.getEmail()) == (logIn.getOtp())) {
                otpService.clearOTP(logIn.getEmail());
                return jwtService.generateToken("JwtToken : " + logIn.getEmail());
            } else {
                return "Invalid OTP";
            }
        } else {
            return "Email Invalid";
        }
    }
}