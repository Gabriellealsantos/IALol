package me.dio.IALol.adapters.out;

import me.dio.IALol.domain.ports.GenerativeAiService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@ConditionalOnProperty(name = "generative-ai.provider", havingValue = "OPENAI")
@FeignClient(name = "openAiApi", url = "${openai.base-url}")
public interface OpenAiService extends GenerativeAiService {

    @PostMapping("/v1/chat/completions")
    OpenAiChatCompletionResp chatCompletion(OpenAiChatCompletionReq req);

    @Override
    default String generateContent(String objective, String context) {
        String model = "gpt-3.5-turbo";

        List<Message> messages = List.of(
                new Message("system", objective),
                new Message("user", context)
        );

        OpenAiChatCompletionReq req = new OpenAiChatCompletionReq(model, messages);

        OpenAiChatCompletionResp resp = chatCompletion(req);

        return resp.choices().get(0).message().content();
    }

    record OpenAiChatCompletionReq(String model, List<Message> messages) {}
    record Message(String role, String content) {}

    record OpenAiChatCompletionResp(List<Choice> choices) {}
    record Choice(Message message) {}
}
