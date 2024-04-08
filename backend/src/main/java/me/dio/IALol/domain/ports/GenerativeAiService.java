package me.dio.IALol.domain.ports;

public interface GenerativeAiService {

    String generateContent(String objective, String context);
}
