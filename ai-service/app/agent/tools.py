from app.clients.core_api import CoreApiClient
from langchain.tools import tool, ToolRuntime

@tool
async def fetch_my_tasks(runtime: ToolRuntime) -> dict:
    """
    Retrieves user tasks from Spring Boot.
    """
    core_api = CoreApiClient(runtime.context.jwt)

    try:
        tasks = await core_api.get_my_task()
    finally:
        await core_api.close()

    return {
        "data": tasks
    }

@tool
async def fetch_my_projects(runtime: ToolRuntime) -> dict:
    """
    Retrieves user projects from Spring Boot.
    """
    core_api = CoreApiClient(runtime.context.jwt)

    try:
        projects = await core_api.get_projects()
    finally:
        await core_api.close()

    return {
        "data": projects
    }

