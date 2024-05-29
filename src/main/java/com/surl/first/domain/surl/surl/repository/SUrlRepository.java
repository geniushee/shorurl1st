package com.surl.first.domain.surl.surl.repository;

import com.surl.first.domain.member.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.surl.first.domain.surl.surl.entity.SUrl;

import java.util.List;
import java.util.Optional;

@Repository
public interface SUrlRepository extends JpaRepository<SUrl, Long> {
    List<SUrl> findAllByMember(Member member);

    Optional<SUrl> findByTitle(String sUrl);

}
