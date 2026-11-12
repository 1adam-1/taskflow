package notification.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notification.event.TaskCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import service.NotificationService;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskCreatedConsumer {
    private  final NotificationService notificationService;

    @RabbitListener(queues = "taskflow.task.queue")
    public void handle(TaskCreatedEvent event){
        log.info(
                "Notification received for task {} - {}",
                event.taskId(),
                event.title()
        );

        notificationService.createTaskNotification(event);
    }
}
