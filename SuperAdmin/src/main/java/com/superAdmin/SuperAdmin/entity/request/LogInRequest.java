package com.superAdmin.SuperAdmin.entity.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LogInRequest {

    private String email;
    private String password;
    private int otp;
}
