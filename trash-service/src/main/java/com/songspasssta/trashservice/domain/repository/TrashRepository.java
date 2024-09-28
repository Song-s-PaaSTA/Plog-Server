package com.songspasssta.trashservice.domain.repository;

import com.songspasssta.trashservice.domain.Trash;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrashRepository extends JpaRepository<Trash, Long> {
}
