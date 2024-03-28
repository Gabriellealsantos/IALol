package me.dio.IALol.domain.ports;

import me.dio.IALol.domain.models.Champions;

import java.util.List;
import java.util.Optional;

public interface ChampionsRepository {
    List<Champions> findAll();

    Optional<Champions> findById(Long id);
}
