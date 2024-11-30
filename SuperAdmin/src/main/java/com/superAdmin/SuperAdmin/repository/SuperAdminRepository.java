package com.superAdmin.SuperAdmin.repository;

import com.superAdmin.SuperAdmin.entity.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long> {

    boolean existsByEmail(String email);

    boolean existsByMobNumber(String mobNumber);

    Optional<SuperAdmin> findByEmail(String email);
}
