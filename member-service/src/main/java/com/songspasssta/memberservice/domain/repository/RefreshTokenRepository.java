package com.songspasssta.memberservice.domain.repository;

import com.songspasssta.memberservice.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}