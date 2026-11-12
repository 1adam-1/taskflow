package notification.event;

public record TaskCreatedEvent(
        Long taskId,
        Long projectId,
        String title,
        Long assigneeId
) {
}
