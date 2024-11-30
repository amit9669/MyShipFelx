package com.msf.entity.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommercialCommodityRequest {
    private Long commercialCommodityId;
    private Long commercialOrderId;
    private String itemName;
    private String units;
    private String unitType;
    private String hsCode;
    private Double unitPrice;
    private String unitCurrency;
}
