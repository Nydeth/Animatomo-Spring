package com.jhs.animatomo.repository;

import com.jhs.animatomo.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
}
