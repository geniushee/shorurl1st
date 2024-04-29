package com.surl.first.domain.surl.surl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.surl.first.domain.surl.surl.entity.SUrl;

@Repository
public interface SUrlRepository extends JpaRepository<SUrl, Long> {
}
