package com.msf.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "payment")
public class PaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_details_id")
    private Long paymentDetailsId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "cvv")
    private String cvv;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "mm_yy")
    private String monthYear;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}