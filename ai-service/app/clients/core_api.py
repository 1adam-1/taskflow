import os

import httpx
from dotenv import load_dotenv

load_dotenv()
API_CORE = os.getenv("SPRING_API_URL").rstrip("/")


class CoreApiClient:
    def __init__(self, token: str):
        self.client = httpx.AsyncClient(
            base_url=API_CORE,
            headers={"Authorization": token},
        )

    async def get_projects(self):
        response = await self.client.get("projects")
        response.raise_for_status()
        return response.json()

    async def get_project(self, project_id: int):
        response = await self.client.get(f"projects/{project_id}")
        response.raise_for_status()
        return response.json()

    async def get_my_task(self):
        response = await self.client.get("tasks")
        response.raise_for_status()
        return response.json()

    async def get_task(self, task_id: int):
        response = await self.client.get(f"tasks/{task_id}")
        response.raise_for_status()
        return response.json()

    async def get_project_tasks(self, project_id: int):
        response = await self.client.get(f"projects/{project_id}/tasks")
        response.raise_for_status()
        return response.json()

    async def close(self):
        await self.client.aclose()
