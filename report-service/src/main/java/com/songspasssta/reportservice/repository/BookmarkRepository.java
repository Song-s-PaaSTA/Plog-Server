package com.songspasssta.reportservice.repository;

import com.songspasssta.reportservice.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
