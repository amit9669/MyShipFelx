package com.msf.repository;

import com.msf.entity.Support;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportRepository extends JpaRepository<Support,Long> {
    boolean existsByTicketId(String s);

    Page<Support> findAllSupportByCompanyId(Long companyId, Pageable pageable);
}
