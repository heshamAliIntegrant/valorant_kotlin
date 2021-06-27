package com.example.roomwordsample.Entities

import androidx.room.*
import com.example.valorant_agents.features.agentsList.Models.Ability
import com.example.valorant_agents.features.agentsList.Models.Role
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromJsonElement
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement

@Entity(tableName = "agent_table")
data class RoomAgent(
        @ColumnInfo(name = "abilities") val abilities: List<RoomAbility>,
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
        @Embedded(prefix = "character_") val role: RoomRole,
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
        fun fromRoomAbilityList(value : List<RoomAbility>) : String {
                val type = object : TypeToken<List<RoomAbility?>?>() {}.type
                return Gson().toJson(value, type)
        }

        @TypeConverter
        fun toRoomAbilityList(value: String) : List<RoomAbility> {
                val type = object : TypeToken<List<RoomAbility?>?>() {}.type
                return Gson().fromJson<List<RoomAbility>>(value, type)
        }

        @TypeConverter
        fun stringListToJson(value: List<String>?) = Gson().toJson(value)

        @TypeConverter
        fun jsonToStringList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
}