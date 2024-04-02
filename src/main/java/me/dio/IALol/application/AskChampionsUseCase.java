package me.dio.IALol.application;

import me.dio.IALol.domain.exception.ChampionNotFoundException;
import me.dio.IALol.domain.models.Champion;
import me.dio.IALol.domain.ports.ChampionsRepository;
import me.dio.IALol.domain.ports.GenerativeAiApi;

import java.util.List;

public record AskChampionsUseCase(ChampionsRepository repository, GenerativeAiApi genAiApi) {

    public String askChampion(Long championId, String question ) {

        Champion champion = repository.findById(championId)
                .orElseThrow(() -> new ChampionNotFoundException(championId));

        String championContext = champion.generateContextByQuestion(question);

        return champion.generateContextByQuestion(question);
    }

}
