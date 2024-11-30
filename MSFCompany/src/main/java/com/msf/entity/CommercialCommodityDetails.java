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
@Table(name = "commercial_commodity_details")
public class CommercialCommodityDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commercial_commodity_id")
    private Long commercialCommodityId;

    @Column(name = "commercial_order_id")
    private Long commercialOrderId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "units")
    private String units;

    @Column(name = "unit_type")
    private String unitType;

    @Column(name = "hs_code")
    private String hsCode;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "unit_currency")
    private String unitCurrency;

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
