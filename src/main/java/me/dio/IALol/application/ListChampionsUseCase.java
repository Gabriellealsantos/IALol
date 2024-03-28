package me.dio.IALol.application;

import me.dio.IALol.domain.models.Champions;
import me.dio.IALol.domain.ports.ChampionsRepository;

import java.util.List;

public record ListChampionsUseCase (ChampionsRepository repository) {

    public List<Champions> findAll() {
        return repository.findAll();
    }

}
