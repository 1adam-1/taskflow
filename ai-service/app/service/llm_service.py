from langchain_ollama import ChatOllama

llm=ChatOllama(
    model="mistral-nemo:latest"
)

response = llm.invoke(
    "Tu es l'assistant de TaskFlow. Présente-toi en une phrase."
)

print(response.content)