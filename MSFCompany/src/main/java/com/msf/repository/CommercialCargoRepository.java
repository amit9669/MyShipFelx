package com.msf.repository;

import com.msf.entity.CommercialCargoDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommercialCargoRepository extends JpaRepository<CommercialCargoDetails,Long> {
    boolean existsByCommercialOrderIdAndIsDeleted(Long commercialOrderId, boolean b);

    List<CommercialCargoDetails> findByCommercialOrderIdAndIsDeleted(Long commercialOrderId, boolean b);
}
