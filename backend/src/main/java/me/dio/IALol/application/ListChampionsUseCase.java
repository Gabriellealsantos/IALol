package me.dio.IALol.application;

import me.dio.IALol.domain.models.Champion;
import me.dio.IALol.domain.ports.ChampionsRepository;

import java.util.List;

public record ListChampionsUseCase (ChampionsRepository repository) {

    public List<Champion> findAll() {
        return repository.findAll();
    }

}
