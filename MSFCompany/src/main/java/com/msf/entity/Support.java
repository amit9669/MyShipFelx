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
@Table(name = "msf_support")
public class Support {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supportId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "ticket_id")
    private String ticketId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "carrier_id")
    private Long carrierId;

    @Column(name = "carrier_name")
    private String carrierName;

    @Column(name = "from_country")
    private String fromCountry;

    @Column(name = "to_country")
    private String toCountry;

    @Column(name = "issue_type")
    private String issueType;

    @Column(name = "description_of_issue", columnDefinition = "TEXT")
    private String descriptionOfIssue;

    @Column(name = "file")
    private String file;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "is_active")
    private Boolean isActive;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
