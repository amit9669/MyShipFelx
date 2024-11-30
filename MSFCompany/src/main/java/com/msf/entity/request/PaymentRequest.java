package com.msf.entity.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {

    private Long paymentDetailsId;

    private Long companyId;

    private String paymentMethod;

    private String firstName;

    private String lastName;

    private String cardNumber;

    private String cvv;

    private String cardType;

    private String monthYear;
}
