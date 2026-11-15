package taskflow.services.interfaces;

import taskflow.entity.dto.assistant.ChatRequest;
import taskflow.entity.dto.assistant.ChatResponse;

public interface AssistantService {
    ChatResponse chat(ChatRequest request, String authorization);
}
