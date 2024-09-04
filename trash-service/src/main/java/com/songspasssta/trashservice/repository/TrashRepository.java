package com.songspasssta.trashservice.repository;

import com.songspasssta.trashservice.entity.Trash;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrashRepository extends JpaRepository<Trash, Long> {
}
