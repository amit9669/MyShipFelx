package com.msf.repository;

import com.msf.entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails,Long> {
}
