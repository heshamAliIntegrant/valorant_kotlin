package com.example.valorant_agents.features.agentsList.Repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.roomwordsample.Entities.RoomAgent
import com.example.valorant_agents.features.agentsList.Models.Agent
import com.example.valorant_agents.features.agentsList.Models.Agents
import com.example.valorant_agents.networking.RemoteDataSource
import com.example.valorant_agents.networking.RemoteResponse

class AgentsRepo(private val agentDao: AgentDao, private val remoteDataSource: RemoteDataSource) {

    fun loadAgentsLocally(): LiveData<List<RoomAgent>> {
        return agentDao.getAgents().asLiveData()
    }

    suspend fun loadAgentsRemotely(): List<Agent> {
        return when(val agentsResponse = remoteDataSource.getAgents()) {
            is RemoteResponse.Success -> agentsResponse.body
            is RemoteResponse.Error -> throw agentsResponse.error
        }
    }

}