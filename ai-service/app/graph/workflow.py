from langgraph.graph import StateGraph, START, END
from app.graph.state import AssistantState
from app.graph.nodes import (
    fetch_tasks_node,
    analyze_tasks_node,
    generate_answer_node
)

def build_assistant_graph():

    graph = StateGraph(AssistantState)

    graph.add_node("fetch_tasks", fetch_tasks_node)
    graph.add_node("analyze_tasks", analyze_tasks_node)
    graph.add_node("generate_answer", generate_answer_node)

    graph.add_edge(START, "fetch_tasks")
    graph.add_edge("fetch_tasks", "analyze_tasks")
    graph.add_edge("analyze_tasks", "generate_answer")
    graph.add_edge("generate_answer", END)

    return graph.compile()


assistant_graph = build_assistant_graph()