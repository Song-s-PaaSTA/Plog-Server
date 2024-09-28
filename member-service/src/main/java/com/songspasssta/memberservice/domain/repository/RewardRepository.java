package com.songspasssta.memberservice.domain.repository;

import com.songspasssta.memberservice.domain.Reward;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RewardRepository extends CrudRepository<Reward, Long> {

    Optional<Reward> findByMemberId(final Long memberId);
}
