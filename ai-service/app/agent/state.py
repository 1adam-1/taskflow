from dataclasses import dataclass
from typing import List
from langchain.agents import AgentState

class AssistantState(AgentState):
    data: List[dict]
    answer: str

@dataclass
class Context:
    jwt: str