package com.superAdmin.SuperAdmin.entity.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuperAdminRequest {

    private Long superAdminId;

    private String firstName;

    private String lastName;

    private String gender;

    private String email;

    private String password;

    private String mobNumber;
}
