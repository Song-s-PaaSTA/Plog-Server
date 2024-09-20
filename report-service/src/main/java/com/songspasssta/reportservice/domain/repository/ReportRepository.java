package com.songspasssta.reportservice.domain.repository;

import com.songspasssta.reportservice.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
