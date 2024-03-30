package me.dio.IALol;

import me.dio.IALol.application.AskChampionsUseCase;
import me.dio.IALol.application.ListChampionsUseCase;
import me.dio.IALol.domain.ports.ChampionsRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IaLolApplication {

	public static void main(String[] args) {
		SpringApplication.run(IaLolApplication.class, args);
	}

	@Bean
	public ListChampionsUseCase provideListChampionsUseCase(ChampionsRepository repository){
		return new ListChampionsUseCase(repository);
	}

	@Bean
	public AskChampionsUseCase provideAskChampionsUseCase(ChampionsRepository repository){
		return new AskChampionsUseCase(repository);
	}

}
