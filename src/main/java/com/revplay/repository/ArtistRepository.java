package com.revplay.repository;

import com.revplay.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findByUser(User user);
}
