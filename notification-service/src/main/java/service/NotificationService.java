package service;

import entity.Notification;
import lombok.RequiredArgsConstructor;
import notification.event.TaskCreatedEvent;
import org.springframework.stereotype.Service;
import repository.NotificationRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void createTaskNotification(TaskCreatedEvent event){
        if(event.assigneeId()==null){
            return;
        }
        Notification notification = Notification.builder()
                .userId(event.assigneeId())
                .message("New task assigned : "+ event.title())
                .createdAt(LocalDate.now())
                .build();

        notificationRepository.save(notification);
    }

    public List<Notification> getNotifications(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public void maskAsRead(Long id){
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() ->new RuntimeException("Notificaton non trouvé"));

        notification.setRead(true);

        notificationRepository.save(notification);
    }

}
