package com.msf.entity.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommercialInvoiceRequest {

    private Long commercialInvoiceId;
    private Long commercialOrderId;
    private String termsOfSale;
    private String reasonOfExport;
    private String countryOfManufacturing;
}
