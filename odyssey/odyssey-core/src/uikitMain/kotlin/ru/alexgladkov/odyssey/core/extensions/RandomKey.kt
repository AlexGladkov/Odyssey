package ru.alexgladkov.odyssey.core.extensions

import com.benasher44.uuid.Uuid

actual fun createUniqueKey(key: String): String {
    return "$key${Uuid(Long.MAX_VALUE, Long.MIN_VALUE)}"
}