package ru.alexgladkov.odyssey.core.extensions

import com.benasher44.uuid.Uuid

actual fun createUniqueKey(key: String): String = "$key${Uuid(Int.MAX_VALUE.toLong(), Int.MIN_VALUE.toLong())}"