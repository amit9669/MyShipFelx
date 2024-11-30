package com.msf.entity.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShippingFilterRequest {

    private Long companyId;
    private String shipmentToCountry;
    private String shipmentFromCountry;
    private String commercialInvoicesId;
    private String startDate;
    private String endDate;
    private String sortField;
    private String sortBy;
}
