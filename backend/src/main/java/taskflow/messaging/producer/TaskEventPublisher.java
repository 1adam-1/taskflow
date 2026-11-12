package taskflow.messaging.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import taskflow.messaging.event.TaskCreatedEvent;


@Component
@RequiredArgsConstructor
public class TaskEventPublisher {
    public static final String TASK_EXCHANGE = "taskflow.task.exchange";
    public static final String TASK_CREATED_ROUTING_KEY = "task.created";

    private final RabbitTemplate rabbitTemplate;

    public void publishTaskCreated(TaskCreatedEvent event) {

        rabbitTemplate.convertAndSend(
                TASK_EXCHANGE,
                TASK_CREATED_ROUTING_KEY,
                event
        );
    }
}
