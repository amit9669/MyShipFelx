package com.msf.repository;

import com.msf.entity.MsfCompanyCarriers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MsfCompanyCarriesRepository extends JpaRepository<MsfCompanyCarriers, Long> {

    Page<MsfCompanyCarriers> findAllMsfCompanyCarriersByCompanyId(Long companyId, Pageable pageable);
}
