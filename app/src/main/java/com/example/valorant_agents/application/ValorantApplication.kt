package com.example.valorant_agents.application

import android.app.Application
import com.example.roomwordsample.Entities.ValorantRoomDatabase
import com.example.valorant_agents.features.agentsList.Repo.AgentsRepo
import com.example.valorant_agents.networking.Endpoint
import com.example.valorant_agents.networking.RemoteDataSourceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ValorantApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { ValorantRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { AgentsRepo(database.agentsDao(), RemoteDataSourceImpl(Endpoint.build())) }
}