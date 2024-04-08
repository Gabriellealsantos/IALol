package me.dio.IALol.adapters.in;

import me.dio.IALol.application.ListChampionsUseCase;
import me.dio.IALol.domain.models.Champion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/champions")
public record ListChampionsRestController(ListChampionsUseCase useCase) {

    @GetMapping
    public List<Champion> findAllChampions() {
        return useCase.findAll();
    }

}
