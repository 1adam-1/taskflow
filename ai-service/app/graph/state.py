from typing import List, TypedDict

class AssistantState(TypedDict):
    message: str
    token: str
    tasks: List[dict]
    analyzed_tasks: List[dict]
    answer: str