package com.msf.entity.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandRequest {

    private Long brandId;
    private String brandName;
    private String color;
    private MultipartFile logo;
    private String link;
}
