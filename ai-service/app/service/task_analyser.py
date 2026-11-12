from datetime import date


def analyze_tasks(tasks: list[dict]) -> list[dict]:
    today = date.today()
    analyzed_tasks = []

    for task in tasks:
        status = task.get("status")
        priority = task.get("priority")
        due_date = task.get("dueDate")

        if status == "DONE":
            continue

        days_remaining = None

        if due_date:
            due_date = date.fromisoformat(due_date)
            days_remaining = (due_date - today).days

        if days_remaining is not None and days_remaining < 0:
            urgency = "OVERDUE"
            score = 100

        elif priority == "URGENT":
            urgency = "CRITICAL"
            score = 90

        elif priority == "HIGH" and days_remaining is not None and days_remaining <= 3:
            urgency = "HIGH"
            score = 80

        elif priority == "HIGH":
            urgency = "HIGH"
            score = 70

        elif days_remaining is not None and days_remaining <= 3:
            urgency = "UPCOMING"
            score = 60

        elif priority == "MEDIUM":
            urgency = "MEDIUM"
            score = 40

        else:
            urgency = "LOW"
            score = 20

        analyzed_tasks.append({
            "taskId": task.get("id"),
            "title": task.get("title"),
            "status": status,
            "priority": priority,
            "dueDate": due_date.isoformat() if due_date else None,
            "daysRemaining": days_remaining,
            "projectId": task.get("projectId"),
            "assigneeName": task.get("assigneeName"),
            "urgency": urgency,
            "score": score
        })

    return sorted(
        analyzed_tasks,
        key=lambda task: task["score"],
        reverse=True
    )