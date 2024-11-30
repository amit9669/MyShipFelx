package com.msf.entity.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private Long commercialOrderId;
    private String shipmentId;
    private String referenceNumber;
    private String shippingFromCountry;
    private String shippingFromCompanyName;
    private String shippingFromContactPersonName;
    private String shippingFromAddress;
    private String shippingFromAddressLine2;
    private String shippingFromCity;
    private String shippingFromState;
    private String shippingFromZip;
    private String shipmentFromPhoneNumber;
    private String shippingFromEmailAddress;
    private String shippingFromDestinationType;
    private String shippingToCountry;
    private String shippingToCompanyName;
    private String shippingToContactPersonName;
    private String shippingToAddress;
    private String shippingToAddressLine2;
    private String shippingToCity;
    private String shippingToState;
    private String shippingToZip;
    private String shippingToPhoneNumber;
    private String shippingToEmailAddress;
    private String shippingToDestinationType;
    private String exporterCountry;
    private String exporterCompanyName;
    private String exporterContactPersonName;
    private String exporterAddress;
    private String exporterAddressLine2;
    private String exporterCity;
    private String exporterState;
    private String exporterZip;
    private String exporterPhoneNumber;
    private String exporterEmailAddress;
    private String exporterDestinationType;
    private String commercialInvoicesId;
}
