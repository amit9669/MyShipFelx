package com.msf.repository;

import com.msf.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByPhoneNumberAndIsDeleted(String phoneNumber, boolean b);

    boolean existsByPhoneNumberAndUserIdNotIn(String phoneNumber, List<Long> uId);

    Page<User> findAllUserByCompanyId(Long companyId, Pageable pageable);
}
