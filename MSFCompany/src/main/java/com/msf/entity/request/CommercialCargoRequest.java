package com.msf.entity.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommercialCargoRequest {

    private Long commercialCargoId;
    private Long commercialOrderId;
    private Integer packageDetails;
    private String packageType;
}
