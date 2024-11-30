package com.msf.entity.request;

import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MsfLogIn {

    private String email;
    private String password;
    private int otp;
}
