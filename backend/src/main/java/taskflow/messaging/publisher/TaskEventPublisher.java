package taskflow.messaging.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import taskflow.messaging.config.RabbitMqConfig;
import taskflow.messaging.event.TaskCreatedEvent;

@Component
@RequiredArgsConstructor
public class TaskEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public void PublishTaskCreated(TaskCreatedEvent event)
    {
        rabbitTemplate.convertAndSend(
                RabbitMqConfig.TASK_EXCHANGE,
                RabbitMqConfig.TASK_CREATED_ROUTING_KEY,
                event
        );
    }

}
