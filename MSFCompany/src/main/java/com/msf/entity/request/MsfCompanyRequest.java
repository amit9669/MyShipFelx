package com.msf.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MsfCompanyRequest {

    private Long id;

    private String name;

    private String email;

    private String password;

    private String location;

    private String mobNumber;

    private Long companyId;
}
