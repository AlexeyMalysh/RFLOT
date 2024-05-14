package com.example.bachelordegreeproject.core.util.constants

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

@Suppress("FunctionName")
fun <T> MultipleEventFlow(capacity: Int = 100): MutableSharedFlow<T> = MutableSharedFlow(
    replay = 0,
    extraBufferCapacity = capacity,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
)

fun <T> MutableSharedFlow<T>.observe(
    scope: CoroutineScope,
    action: suspend (value: T) -> Unit
): Job = scope.launch {
    collect(action::invoke)
}
