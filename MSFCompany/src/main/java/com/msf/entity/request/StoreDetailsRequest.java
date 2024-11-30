package com.msf.entity.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreDetailsRequest {

    private Long storeId;

    private Long companyId;

    private String name;

    private String industry;

    private String country;

    private String phoneNo;

    private String parcelCount;
}
