package com.msf.entity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShipmentFilterResponse {

    private String commercialInvoiceId;
    private String shipmentToCountry;
    private String shipmentFromCountry;
    private LocalDateTime createdAt;
}
