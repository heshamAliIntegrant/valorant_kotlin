package com.example.valorant_agents.networking

import com.example.valorant_agents.features.agentsList.Models.Agent
import com.example.valorant_agents.features.agentsList.Models.Agents
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
interface Endpoint {
    @GET("agents")
    suspend fun getAgents() : Response<ApiResponse<List<Agent>>>
    companion object {
        const val END_POINT = "https://valorant-api.com/v1/"
        fun build() : Endpoint {
            return Retrofit.Builder()
                    .baseUrl(END_POINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(Endpoint::class.java)
        }
    }
}