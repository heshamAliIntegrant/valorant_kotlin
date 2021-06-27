package com.example.valorant_agents.features.agentsList.Models

import com.example.roomwordsample.Entities.RoomAbility
import com.example.valorant_agents.mappers.RoomMapper

data class Ability(
    val description: String,
    val displayIcon: String?,
    val displayName: String,
    val slot: String
) : RoomMapper<RoomAbility> {
    override fun toRoomEntity(): RoomAbility = RoomAbility(description = this.description,
            displayIcon =  this.displayIcon,
            displayName = this.displayName,
            slot =  this.slot)
}