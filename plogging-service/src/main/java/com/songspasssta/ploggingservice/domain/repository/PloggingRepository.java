package com.songspasssta.ploggingservice.domain.repository;

import com.songspasssta.ploggingservice.domain.Plogging;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PloggingRepository extends JpaRepository<Plogging, Long> {

    void deleteByMemberId(final Long memberId);
}