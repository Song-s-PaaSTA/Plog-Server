package com.songspasssta.ploggingservice.domain.repository;

import com.songspasssta.ploggingservice.domain.Plogging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PloggingRepository extends JpaRepository<Plogging, Long> {

    void deleteByMemberId(final Long memberId);

    Slice<Plogging> findByMemberIdOrderByCreatedAtDesc(final Long memberId, final Pageable pageable);
}
