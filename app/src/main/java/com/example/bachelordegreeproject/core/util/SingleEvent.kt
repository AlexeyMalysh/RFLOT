package com.example.bachelordegreeproject.core.util

open class SingleEvent<out T>(private val content: T) {
    var isHandled = false
        private set

    fun getContent(): T? = if (isHandled) {
        null
    } else {
        isHandled = true
        content
    }
}

class SingleUnitEvent : SingleEvent<Unit>(Unit)