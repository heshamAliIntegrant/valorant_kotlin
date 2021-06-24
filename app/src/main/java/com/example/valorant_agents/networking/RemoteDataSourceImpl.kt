package com.example.valorant_agents.networking

import com.example.valorant_agents.features.agentsList.Models.Agent
import safeApiResponseCall

class RemoteDataSourceImpl(
    private val endPoint: Endpoint
) : RemoteDataSource {

    override suspend fun getAgents(): ApiRemoteResponse<List<Agent>> {
        return safeApiResponseCall(endPoint.getAgents())
    }

}