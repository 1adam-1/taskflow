package taskflow.messaging.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import taskflow.messaging.config.RabbitMqConfig;
import taskflow.messaging.event.TaskCreatedEvent;

@Slf4j
@Component
public class TaskEventConsumer {

    @RabbitListener(queues = RabbitMqConfig.TASK_CREATED_QUEUE)
    public void HandleTaskCreated(TaskCreatedEvent event){
        log.info(
                "Task created event received: taskId={}, projectId={}, title={}, assigneeId={}",
                event.taskId(),
                event.projectId(),
                event.title(),
                event.assigneeId()
        );
    }
}
