package com.ilshat.node.ui.util

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
inline fun <reified T : Interaction> InteractionSource.collectAs(): MutableState<T?> {
    val state = remember { mutableStateOf(null as T?) }
    LaunchedEffect(this) {
        interactions.collect { interaction ->
            val typed = interaction as? T
            state.value = typed
        }
    }
    return state
}

@Composable
inline fun <reified T : Interaction> InteractionSource.collect(
    crossinline block: (T) -> Unit
) {
    LaunchedEffect(this) {
        interactions.collect { interaction ->
            val typed = interaction as? T
            typed?.let(block)
        }
    }
}