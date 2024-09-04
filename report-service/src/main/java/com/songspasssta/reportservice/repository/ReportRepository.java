package com.songspasssta.reportservice.repository;

import com.songspasssta.reportservice.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
