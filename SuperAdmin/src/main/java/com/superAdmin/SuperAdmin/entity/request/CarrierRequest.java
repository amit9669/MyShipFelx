package com.superAdmin.SuperAdmin.entity.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarrierRequest {

    private Long carrierId;

    private String name;

    private String description;

    private MultipartFile logo;
}
