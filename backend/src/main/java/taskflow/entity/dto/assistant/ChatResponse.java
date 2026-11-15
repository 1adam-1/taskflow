package taskflow.entity.dto.assistant;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatResponse{
    private String answer;
}