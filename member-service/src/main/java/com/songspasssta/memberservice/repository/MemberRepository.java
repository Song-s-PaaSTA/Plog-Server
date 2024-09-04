package com.songspasssta.memberservice.repository;

import com.songspasssta.memberservice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
