package me.dio.IALol.domain.ports;

import me.dio.IALol.domain.models.Champion;

import java.util.List;
import java.util.Optional;

public interface ChampionsRepository {
    List<Champion> findAll();

    Optional<Champion> findById(Long id);
}
