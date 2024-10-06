package com.songspasssta.ploggingservice.domain.repository;

import com.songspasssta.ploggingservice.domain.Plogging;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PloggingRepository extends JpaRepository<Plogging, Long> {

    void deleteByMemberId(final Long memberId);

    List<Plogging> findByMemberIdOrderByCreatedAtDesc(final Long memberId);
}