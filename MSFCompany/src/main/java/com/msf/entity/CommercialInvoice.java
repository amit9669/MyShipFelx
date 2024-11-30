package com.msf.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "commercial_invoice")
public class CommercialInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commercial_invoice_id")
    private Long commercialInvoiceId;

    @Column(name = "commercial_order_id")
    private Long commercialOrderId;

    @Column(name = "terms_of_sale")
    private String termsOfSale;

    @Column(name = "reason_of_export")
    private String reasonOfExport;

    @Column(name = "country_of_manufacturing")
    private String countryOfManufacturing;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "is_cancel")
    private Boolean isCancel = false;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
