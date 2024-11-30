package com.msf.entity;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "brand_deatils")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "primary_color")
    private String color;

    @Column(name = "brand_logo")
    private String logo;

    @Column(name = "shipping_link")
    private String link;

    @Column(name = "company_id")
    private Long companyId;
}
