from langchain.agents import create_agent
from app.service.llm_service import llm
from app.agent.state import AssistantState
from app.agent.state import Context
from langchain.mcp import MCPAdapter

async def agent(message, token):
        adapter =  MCPAdapter("https://docs.langchain.com/mcp")
        tools = await adapter.list_tools()

        agent = create_agent(
            llm,
            tools=tools,
            state_schema=AssistantState,
            system_prompt="You are a helpful assistant. Be concise and accurate.",
            context_schema=Context
            )
        
        result = await agent.ainvoke(
                {"messages": [{"role": "user", "content": message}]},
                 context=Context(jwt=token),
            ) 
        return result["messages"][-1].content