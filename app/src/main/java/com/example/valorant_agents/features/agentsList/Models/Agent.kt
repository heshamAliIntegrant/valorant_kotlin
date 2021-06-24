package com.example.valorant_agents.features.agentsList.Models

data class Agent(
    val abilities: List<Ability>,
    val assetPath: String,
    val bustPortrait: String,
    val characterTags: List<String>?,
    val description: String,
    val developerName: String,
    val displayIcon: String,
    val displayIconSmall: String,
    val displayName: String,
    val fullPortrait: String,
    val isAvailableForTest: Boolean,
    val isFullPortraitRightFacing: Boolean,
    val isPlayableCharacter: Boolean,
    val killfeedPortrait: String,
    val role: Role?,
    val uuid: String
)