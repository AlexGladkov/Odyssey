package ru.alexgladkov.odyssey.compose.extensions

import com.benasher44.uuid.Uuid

actual fun createUniqueKey(key: String): String {
    return Uuid(Long.MAX_VALUE, Long.MIN_VALUE).toString()
}