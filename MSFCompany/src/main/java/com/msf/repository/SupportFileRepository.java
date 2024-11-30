package com.msf.repository;

import com.msf.entity.SupportFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportFileRepository extends JpaRepository<SupportFile,Long> {
    List<SupportFile> findBySupportId(Long supportId);

    List<SupportFile> findAllBySupportId(Long supportId);
}
