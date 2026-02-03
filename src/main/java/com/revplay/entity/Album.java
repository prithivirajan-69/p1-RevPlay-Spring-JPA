package com.revplay.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDate releaseDate;

    @ManyToOne
    private Artist artist;

    @OneToMany
    private List<Song> songs = new ArrayList<>();
}
