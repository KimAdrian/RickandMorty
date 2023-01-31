package com.kimadrian.rickandmorty.utils

import androidx.compose.ui.graphics.Color

enum class CharacterStatusColor(val color: Color) {
    ALIVE(color = Color(0xFF55CC44)),
    DEAD(color = Color(0xFFD63D2E)),
    UNKNOWN(color = Color(0xFF9E9E9E));

    companion object {
        fun statusColor(status: String): Color {
            return when (status) {
                "Alive" -> ALIVE.color
                "Dead" -> DEAD.color
                else -> {
                    UNKNOWN.color
                }
            }
        }
    }

}