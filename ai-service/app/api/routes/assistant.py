import httpx
from fastapi import APIRouter, Header, HTTPException
from app.schemas.assistant import ChatRequest, ChatResponse
from app.agent.create_agent import agent as assistant_chat
router = APIRouter(
    prefix="/api/assistant",
    tags=["assistant"]
)

@router.post("/chat")
async def chat(request: ChatRequest, authorization: str = Header(...)):
    if not authorization.startswith("Bearer "):
        raise HTTPException(status_code=401, detail="Invalid authorization header")

    try:
        response =await assistant_chat(request.message, authorization)
        return ChatResponse(answer=response)

    except httpx.HTTPStatusError as e:
        raise HTTPException(
            status_code=e.response.status_code,
            detail="Core API request failed"
        )
        