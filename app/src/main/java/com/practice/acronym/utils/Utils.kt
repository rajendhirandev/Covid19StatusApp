package com.practice.acronym.utils

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

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

fun String.capitalizeWord() = lowercase()
    .split("\\s+".toRegex())
    .joinToString(" ") {
        it.trim().replaceFirstChar(Char::titlecase)
    }

fun toastMsg(ctx: Context, msg: String) {
    Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show()
}
