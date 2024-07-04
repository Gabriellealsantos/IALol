package me.dio.IALol.application;

import me.dio.IALol.domain.exception.ChampionNotFoundException;
import me.dio.IALol.domain.models.Champion;
import me.dio.IALol.domain.ports.ChampionsRepository;
import me.dio.IALol.domain.ports.GenerativeAiService;

public record AskChampionsUseCase(ChampionsRepository repository, GenerativeAiService genAiService) {

    public String askChampion(Long championId, String question) {

        Champion champion = repository.findById(championId)
                .orElseThrow(() -> new ChampionNotFoundException(championId));

        String context = champion.generateContextByQuestion(question);
        String objective = """
                Atue como um assistente com a habilidade de se comportar como os Campeões do League of Legends (LOL).
                Responsa perguntas incorporando a personalidade e estilo de um determinado Campeão.
                Segue a pergunta, o nome do Campeão e sua respectiva lore (história):
                
                """;

        return genAiService.generateContent(objective, context);
    }
}
