package com.example.roomwordsample.Entities

import androidx.room.*
import com.example.valorant_agents.features.agentsList.Models.Ability
import com.example.valorant_agents.features.agentsList.Models.Agent
import com.example.valorant_agents.features.agentsList.Models.Role
import com.example.valorant_agents.mappers.DtoMapper
import com.example.valorant_agents.mappers.RoomMapper
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
        @ColumnInfo(name = "bustPortrait") val bustPortrait: String?,
        @ColumnInfo(name = "characterTags") val characterTags: List<String>?,
        @ColumnInfo(name = "description") val description: String,
        @ColumnInfo(name = "developerName") val developerName: String,
        @ColumnInfo(name = "displayIcon") val displayIcon: String,
        @ColumnInfo(name = "displayIconSmall") val displayIconSmall: String,
        @ColumnInfo(name = "displayName") val displayName: String,
        @ColumnInfo(name = "fullPortrait") val fullPortrait: String?,
        @ColumnInfo(name = "isAvailableForTest") val isAvailableForTest: Boolean,
        @ColumnInfo(name = "isFullPortraitRightFacing") val isFullPortraitRightFacing: Boolean,
        @ColumnInfo(name = "isPlayableCharacter") val isPlayableCharacter: Boolean,
        @ColumnInfo(name = "killfeedPortrait") val killfeedPortrait: String,
        @Embedded(prefix = "character_") val role: RoomRole?,
        @ColumnInfo(name = "isFavorite") val isFavorite: Boolean = false,
        @PrimaryKey @ColumnInfo(name = "uuid") val uuid: String) : DtoMapper<Agent> {
        override fun toDto(): Agent = Agent(abilities.toDtoList(),
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
                role?.toDto(),
                isFavorite,
                uuid
        )
}

fun <E, T: DtoMapper<E>> List<T>.toDtoList(): List<E> {
        var list: MutableList<E> = mutableListOf<E>()
        this.forEach {
                list.add(it.toDto())
        }
        return list
}


fun <E, T: RoomMapper<E>> List<T>.toRoomList(): List<E> {
        var list: MutableList<E> = mutableListOf<E>()
        this.forEach {
                list.add(it.toRoomEntity())
        }
        return list
}

@Entity(tableName = "agent_ability")
data class RoomAbility(
        @PrimaryKey(autoGenerate = true) @ColumnInfo val abilityId: Int = -1,
        @ColumnInfo(name = "description") val description: String,
        @ColumnInfo(name = "displayIcon") val displayIcon: String?,
        @ColumnInfo(name = "displayName") val displayName: String,
        @ColumnInfo(name = "slot") val slot: String
): DtoMapper<Ability> {
        override fun toDto(): Ability = Ability(this.description, this.displayIcon, this.displayName, this.slot)
}


@Entity(tableName = "agent_role")
data class RoomRole(
        @ColumnInfo(name = "assetPath") val assetPath: String,
        @ColumnInfo(name = "description") val description: String,
        @ColumnInfo(name = "displayIcon") val displayIcon: String,
        @ColumnInfo(name = "displayName") val displayName: String,
        @PrimaryKey @ColumnInfo(name = "uuid") val uuid: String = ""
): DtoMapper<Role> {
        override fun toDto(): Role = Role(this.assetPath, this.description, this.displayIcon, this.displayName, uuid)
}

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