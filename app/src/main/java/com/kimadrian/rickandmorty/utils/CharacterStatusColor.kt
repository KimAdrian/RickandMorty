package com.kimadrian.rickandmorty.utils

import android.graphics.Color

enum class CharacterStatusColor(val color: Int) {
    ALIVE(color = Color.GREEN),
    DEAD(color = Color.RED),
    UNKNOWN(color = Color.GRAY);

    companion object {
        fun statusColor(status: String): Int {
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