package com.example.roomwordsample.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.TypeConverter
import com.example.valorant_agents.features.agentsList.Models.Ability
import com.example.valorant_agents.features.agentsList.Models.Role
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromJsonElement
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement

@Entity(tableName = "agent_table")
data class RoomAgent(
        //@ColumnInfo(name = "abilities") val abilities: ArrayList<RoomAbility>,
        @ColumnInfo(name = "assetPath") val assetPath: String,
        @ColumnInfo(name = "bustPortrait") val bustPortrait: String,
        @ColumnInfo(name = "characterTags") val characterTags: List<String>?,
        @ColumnInfo(name = "description") val description: String,
        @ColumnInfo(name = "developerName") val developerName: String,
        @ColumnInfo(name = "displayIcon") val displayIcon: String,
        @ColumnInfo(name = "displayIconSmall") val displayIconSmall: String,
        @ColumnInfo(name = "displayName") val displayName: String,
        @ColumnInfo(name = "fullPortrait") val fullPortrait: String,
        @ColumnInfo(name = "isAvailableForTest") val isAvailableForTest: Boolean,
        @ColumnInfo(name = "isFullPortraitRightFacing") val isFullPortraitRightFacing: Boolean,
        @ColumnInfo(name = "isPlayableCharacter") val isPlayableCharacter: Boolean,
        @ColumnInfo(name = "killfeedPortrait") val killfeedPortrait: String,
        //@ColumnInfo(name = "role") val role: Role,
        @PrimaryKey @ColumnInfo(name = "uuid") val uuid: Int)

@Entity(tableName = "agent_ability")
data class RoomAbility(
        @PrimaryKey(autoGenerate = true) @ColumnInfo val abilityId: Int,
        @ColumnInfo(name = "description") val description: String,
        @ColumnInfo(name = "displayIcon") val displayIcon: String,
        @ColumnInfo(name = "displayName") val displayName: String,
        @ColumnInfo(name = "slot") val slot: String
)

@Entity(tableName = "agent_role")
data class RoomRole(
        @ColumnInfo(name = "assetPath") val assetPath: String,
        @ColumnInfo(name = "description") val description: String,
        @ColumnInfo(name = "displayIcon") val displayIcon: String,
        @ColumnInfo(name = "displayName") val displayName: String,
        @PrimaryKey @ColumnInfo(name = "uuid") val uuid: String
)

class Converters {
        @TypeConverter
        fun fromList(value : List<String>) = Json.encodeToString(value)

        @TypeConverter
        fun toList(value: String) = Json.decodeFromString<List<String>>(value)
}