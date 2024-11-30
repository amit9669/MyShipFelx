package com.msf.entity.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private Long userId;

    private Long companyId;

    private String companyName;

    private String firstName;

    private String lastName;

    private String industry;

    private String location;

    private String phoneNumber;
}
