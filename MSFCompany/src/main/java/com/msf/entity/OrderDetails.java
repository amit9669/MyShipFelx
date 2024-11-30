package com.msf.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_details")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commercial_order_id")
    private Long commercialOrderId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "shipment_id")
    private String shipmentId;

    @Column(name = "commercialI_invoices_id")
    private String commercialInvoicesId;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "shipping_from_country")
    private String shippingFromCountry;

    @Column(name = "shipping_from_company_name")
    private String shippingFromCompanyName;

    @Column(name = "shippingF_from_contact_personName")
    private String shippingFromContactPersonName;

    @Column(name = "shipping_from_address")
    private String shippingFromAddress;

    @Column(name = "shipping_from_address_line2")
    private String shippingFromAddressLine2;

    @Column(name = "shipping_from_city")
    private String shippingFromCity;

    @Column(name = "shipping_from_state")
    private String shippingFromState;

    @Column(name = "shipping_from_zip")
    private String shippingFromZip;

    @Column(name = "shipmentF_from_phone_number")
    private String shipmentFromPhoneNumber;

    @Column(name = "shipping_from_email_address")
    private String shippingFromEmailAddress;

    @Column(name = "shipping_from_destination_type")
    private String shippingFromDestinationType;

    @Column(name = "shipping_to_country")
    private String shippingToCountry;

    @Column(name = "shipping_to_company_name")
    private String shippingToCompanyName;

    @Column(name = "shipping_to_contact_person_name")
    private String shippingToContactPersonName;

    @Column(name = "shipping_to_address")
    private String shippingToAddress;

    @Column(name = "shipping_to_address_line2")
    private String shippingToAddressLine2;

    @Column(name = "shipping_to_city")
    private String shippingToCity;

    @Column(name = "shipping_to_state")
    private String shippingToState;

    @Column(name = "shipping_to_zip")
    private String shippingToZip;

    @Column(name = "shipping_to_phone_number")
    private String shippingToPhoneNumber;

    @Column(name = "shipping_to_email_address")
    private String shippingToEmailAddress;

    @Column(name = "shipping_to_destination_type")
    private String shippingToDestinationType;

    @Column(name = "exporter_country")
    private String exporterCountry;

    @Column(name = "exporter_company_name")
    private String exporterCompanyName;

    @Column(name = "exporterContact_person_name")
    private String exporterContactPersonName;

    @Column(name = "exporter_address")
    private String exporterAddress;

    @Column(name = "exporter_address_line2")
    private String exporterAddressLine2;

    @Column(name = "exporter_city")
    private String exporterCity;

    @Column(name = "exporter_state")
    private String exporterState;

    @Column(name = "exporter_zip")
    private String exporterZip;

    @Column(name = "exporter_phone_number")
    private String exporterPhoneNumber;

    @Column(name = "exporter_email_address")
    private String exporterEmailAddress;

    @Column(name = "exporter_destination_type")
    private String exporterDestinationType;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
