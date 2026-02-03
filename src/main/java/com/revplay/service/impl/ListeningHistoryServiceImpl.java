package com.revplay.service.impl;

import com.revplay.repository.ListeningHistoryRepository;
import com.revplay.service.ListeningHistoryService;
import org.springframework.stereotype.Service;

@Service
public class ListeningHistoryServiceImpl implements ListeningHistoryService {

    private final ListeningHistoryRepository repo;

    public ListeningHistoryServiceImpl(ListeningHistoryRepository repo) {
        this.repo = repo;
    }

    @Override
    public void viewHistory(Long userId) {
        repo.findByUserId(userId)
                .forEach(h -> System.out.println("🎧 " + h.getSong().getTitle()));
    }
}
