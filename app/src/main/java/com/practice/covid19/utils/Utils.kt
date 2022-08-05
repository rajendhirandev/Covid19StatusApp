package com.practice.covid19.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

fun Int.numberFormat(): String = NumberFormat.getNumberInstance(Locale.US).format(this)


inline fun <T> Flow<T>.launchAndCollectIn(
    owner: LifecycleOwner,
    minState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.(T) -> Unit
) {
    owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(minState) {
            collect {
                block(it)
            }
        }
    }
}