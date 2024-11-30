package com.msf.repository;

import com.msf.entity.CommercialCommodityDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommercialCommodityDetailsRepository extends JpaRepository<CommercialCommodityDetails,Long> {
    boolean existsByCommercialOrderIdAndIsDeleted(Long commercialOrderId, boolean b);

    List<CommercialCommodityDetails> findByCommercialOrderIdAndIsDeleted(Long commercialOrderId, boolean b);
}
