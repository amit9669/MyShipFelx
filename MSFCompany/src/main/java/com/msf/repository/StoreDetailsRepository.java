package com.msf.repository;

import com.msf.entity.StoreDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreDetailsRepository extends JpaRepository<StoreDetails,Long> {

    boolean existsByPhoneNo(String phoneNo);

    @Query("SELECT sd FROM StoreDetails AS sd LEFT JOIN MsfCompany AS msf ON " +
            " sd.companyId=msf.companyId WHERE sd.companyId=:companyId")
    Page<StoreDetails> findByStoreDetailsByCompanyId(@Param("companyId") Long companyId, Pageable pageable);
}
