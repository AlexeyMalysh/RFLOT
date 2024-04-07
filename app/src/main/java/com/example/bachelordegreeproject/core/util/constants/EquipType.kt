package com.example.bachelordegreeproject.core.util.constants

enum class EquipType(val value: String) {
    OK("Ok"),
    UNKNOWN("Unknown");

    companion object {
        fun findByValue(value: String): EquipType {
            return entries.firstOrNull { it.value == value } ?: UNKNOWN
        }
    }
}
