package com.example.valorant_agents.features.agentsList.Models

import com.example.roomwordsample.Entities.RoomRole
import com.example.valorant_agents.mappers.RoomMapper

data class Role(
    val assetPath: String,
    val description: String,
    val displayIcon: String,
    val displayName: String,
    val uuid: String
): RoomMapper<RoomRole> {
    override fun toRoomEntity(): RoomRole = RoomRole(assetPath, description, displayIcon, displayName, uuid)
}