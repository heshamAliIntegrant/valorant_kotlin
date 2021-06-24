package com.example.valorant_agents.networking

import com.example.valorant_agents.features.agentsList.Models.Agent
import com.example.valorant_agents.features.agentsList.Models.Agents
import java.lang.Exception
typealias ApiRemoteResponse<T> = RemoteResponse<T, Exception>
interface RemoteDataSource {
    suspend fun getAgents() : ApiRemoteResponse<List<Agent>>
}