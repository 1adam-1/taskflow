from app.clients.core_api import CoreApiClient
from app.service.task_analyser import analyze_tasks
from app.service.llm_service import llm


async def fetch_tasks_node(state: dict) -> dict:
    """
    Récupère les tâches de l'utilisateur depuis Spring Boot.
    """
    core_api = CoreApiClient(state["token"])

    try:
        tasks = await core_api.get_my_task()
    finally:
        await core_api.close()

    return {
        "tasks": tasks
    }


def analyze_tasks_node(state: dict) -> dict:
    """
    Analyse les tâches avec les règles Python.
    """
    analyzed_tasks = analyze_tasks(state["tasks"])

    return {
        "analyzed_tasks": analyzed_tasks
    }


async def generate_answer_node(state: dict) -> dict:
    """
    Génère une réponse contextualisée avec Ollama.
    """
    analyzed_tasks = state["analyzed_tasks"]

    prompt = f"""
You are TaskFlow's intelligent assistant.

The user asks the following question:
{state["message"]}

Here are the results of the analysis of their tasks:
{analyzed_tasks}

Instructions:
- Identify the tasks that require attention.
- Explain why each task is a priority.
- Suggest concrete actions.
- Do not create any information that is not present in the data.
- If no task requires urgent attention, state this clearly.
- Be clear and concise.
"""

    response = await llm.ainvoke(prompt)

    return {
        "answer": response.content
    }