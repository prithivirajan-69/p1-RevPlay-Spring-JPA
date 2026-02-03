package com.revplay.repository;

import com.revplay.entity.ListeningHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListeningHistoryRepository extends JpaRepository<ListeningHistory, Long> {
    List<ListeningHistory> findByUserId(Long userId);
}
