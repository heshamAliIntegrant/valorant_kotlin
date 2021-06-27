package com.example.valorant_agents.features.agentsList.Models

import androidx.room.ColumnInfo
import com.example.roomwordsample.Entities.RoomAgent
import com.example.roomwordsample.Entities.toRoomList
import com.example.valorant_agents.mappers.RoomMapper

data class Agent(
        val abilities: List<Ability>,
        val assetPath: String,
        val bustPortrait: String?,
        val characterTags: List<String>?,
        val description: String,
        val developerName: String,
        val displayIcon: String,
        val displayIconSmall: String,
        val displayName: String,
        val fullPortrait: String?,
        val isAvailableForTest: Boolean,
        val isFullPortraitRightFacing: Boolean,
        val isPlayableCharacter: Boolean,
        val killfeedPortrait: String,
        val role: Role?,
        val isFavorite: Boolean = false,
        val uuid: String
): RoomMapper<RoomAgent> {
    override fun toRoomEntity(): RoomAgent = RoomAgent(
            abilities.toRoomList(),
            assetPath,
            bustPortrait,
            characterTags ?: emptyList<String>(),
            description,
            developerName,
            displayIcon,
            displayIconSmall,
            displayName,
            fullPortrait,
            isAvailableForTest,
            isFullPortraitRightFacing,
            isPlayableCharacter,
            killfeedPortrait,
            role?.toRoomEntity(),
            isFavorite,
            uuid
    )
}