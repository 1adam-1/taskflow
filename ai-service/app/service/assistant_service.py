from app.graph.workflow import assistant_graph

async def chat(message: str, token: str):
    initial_state = {
        "message": message,
        "token": token,
        "tasks": [],
        "analyzed_tasks": [],
        "answer": ""
    }

    result = await assistant_graph.ainvoke(initial_state)
    return result["answer"]