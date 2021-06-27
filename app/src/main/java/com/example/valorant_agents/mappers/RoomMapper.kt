package com.example.valorant_agents.mappers

interface RoomMapper<T> {
    fun toRoomEntity() : T
}

interface DtoMapper<T> {
    fun toDto() : T
}