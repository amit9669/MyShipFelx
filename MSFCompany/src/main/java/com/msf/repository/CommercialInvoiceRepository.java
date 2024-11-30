package com.msf.repository;

import com.msf.entity.CommercialInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommercialInvoiceRepository extends JpaRepository<CommercialInvoice,Long> {
    boolean existsByCommercialOrderIdAndIsCancel(Long commercialOrderId, boolean b);

    List<CommercialInvoice> findByCommercialOrderIdAndIsCancel(Long commercialOrderId, boolean b);
}
