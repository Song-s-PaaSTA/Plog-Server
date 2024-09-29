package com.songspasssta.memberservice.domain.repository;

import com.songspasssta.memberservice.domain.Reward;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RewardRepository extends CrudRepository<Reward, Long> {

    Optional<Reward> findByMemberId(final Long memberId);

    Slice<Reward> findAll(final Pageable pageable);
}
