import httpx
from fastapi import APIRouter, Header, HTTPException
from app.clients.core_api import CoreApiClient

router = APIRouter(
    prefix="/api/assistant",
    tags=["assistant"]
)

@router.get("/test/projects")
async def test_projects(authorization: str = Header(...)):
    if not authorization.startswith("Bearer "):
        raise HTTPException(status_code=401, detail="Invalid authorization header")

    core_api = CoreApiClient(authorization)
    try:
        return await core_api.get_projects()
    except httpx.HTTPStatusError as exc:
        raise HTTPException(
            status_code=exc.response.status_code,
            detail="Core API request failed",
        ) from exc
    finally:
        await core_api.close()