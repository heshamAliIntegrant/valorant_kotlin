package com.example.valorant_agents.features.agentsList.Repo

import androidx.room.Dao
import androidx.room.Query
import com.example.roomwordsample.Entities.BaseDao
import com.example.roomwordsample.Entities.RoomAgent
import kotlinx.coroutines.flow.Flow
@Dao
interface AgentDao: BaseDao<RoomAgent> {
    @Query("SELECT * FROM agent_table")
    fun getAgents(): Flow<List<RoomAgent>>
}