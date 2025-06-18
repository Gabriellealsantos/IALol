package me.dio.IALol.adapters.outs;

import feign.FeignException;
import feign.RequestInterceptor;
import me.dio.IALol.domain.ports.GenerativeAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@ConditionalOnProperty(name = "generative-ai.provider", havingValue = "GEMINI", matchIfMissing = true)
@FeignClient(name = "geminiApi", url = "${gemini.base-url}", configuration = GoogleGeminiService.Config.class)
public interface GoogleGeminiService extends GenerativeAiService {

    @PostMapping("/v1/models/gemini-2.5-flash:generateContent")
    GoogleGeminiResp textOnlyInput(GoogleGeminiReq req);

    @Override
    default String generateContent(String objective, String context) {
        String prompt = """
                %s
                %s
                """.formatted(objective, context);

        GoogleGeminiReq req = new GoogleGeminiReq(
                List.of(new Content(List.of(new Part(prompt))))
        );
        try {
            GoogleGeminiResp resp = textOnlyInput(req);
            return Optional.ofNullable(resp)
                    .map(GoogleGeminiResp::candidates)
                    .flatMap(candidates -> candidates.stream().findFirst())
                    .map(Candidate::content)
                    .flatMap(content -> content.parts().stream().findFirst())
                    .map(Part::text)
                    .orElse("Deu mais ruim ainda! O retorno da API do Google Gemini não contém os dados esperados.");
        } catch (FeignException httpErrors) {
            return "Deu ruim! Erro de comunicação com a API do Google Gemini.";
        } catch (Exception unexpectedError) {
            return "Deu mais ruim ainda! O retorno da API do Google Gemini não contém os dados esperados.";
        }
    }

    record GoogleGeminiReq(List<Content> contents) {}
    record Content(List<Part> parts) {}
    record Part(String text) {}
    record GoogleGeminiResp(List<Candidate> candidates) {}
    record Candidate(Content content) {}

    class Config {
        @Bean
        public RequestInterceptor apiKeyRequestInterceptor(@Value("${gemini.api-key}") String apiKey) {
            return requestTemplate -> requestTemplate.query("key", apiKey);
        }
    }
}
