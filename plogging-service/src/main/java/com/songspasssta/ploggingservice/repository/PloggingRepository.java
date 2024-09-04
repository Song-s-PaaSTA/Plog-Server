package com.songspasssta.ploggingservice.repository;

import com.songspasssta.ploggingservice.entity.Plogging;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PloggingRepository extends JpaRepository<Plogging, Long> {
}
