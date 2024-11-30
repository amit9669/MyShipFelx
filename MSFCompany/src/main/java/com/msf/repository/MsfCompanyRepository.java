package com.msf.repository;

import com.msf.entity.MsfCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MsfCompanyRepository extends JpaRepository<MsfCompany,Long> {

    boolean existsByMobNumber(String mobNumber);

    boolean existsByEmail(String email);

    Optional<MsfCompany> findByEmail(String username);

    boolean existsByEmailAndIsDeleted(String email, boolean b);

    boolean existsByCompanyIdAndIsDeleted(Long companyId, boolean b);

    boolean existsByNameAndIsDeleted(String name,boolean b);
}
