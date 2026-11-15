package taskflow.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import taskflow.entity.dto.assistant.ChatRequest;
import taskflow.entity.dto.assistant.ChatResponse;
import taskflow.services.interfaces.AssistantService;
import java.net.http.HttpClient;
import org.springframework.http.client.JdkClientHttpRequestFactory;


@Service
@RequiredArgsConstructor
public class AssistantServiceImpl implements AssistantService {

    private final RestClient.Builder restClientBuilder;

    @Value("${ai.service.url}")
    private String aiServiceUrl;

    @Override
    public ChatResponse chat(ChatRequest request, String authorization) {
    HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();

    try {
        ChatResponse response = restClientBuilder
                .baseUrl(aiServiceUrl)
                .requestFactory(new JdkClientHttpRequestFactory(httpClient))
                .build()
                .post()
                .uri("/api/assistant/chat")
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(ChatResponse.class);

            if (response == null) {
                throw new IllegalStateException("AI service returned an empty response");
            }

            return response;
        } catch (RestClientException exception) {
            throw new IllegalStateException("Unable to communicate with AI service", exception);
        }
    }
}
