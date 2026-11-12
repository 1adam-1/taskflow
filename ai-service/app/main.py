from fastapi import FastAPI
from app.api.routes.assistant import router

app=FastAPI(
    title="TaskFlow AI Service",
)

app.include_router(router)

@app.get("/health")
def health_check():
    return {"status": "healthy"}