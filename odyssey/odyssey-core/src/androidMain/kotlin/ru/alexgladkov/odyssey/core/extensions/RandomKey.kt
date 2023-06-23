package ru.alexgladkov.odyssey.core.extensions

import com.benasher44.uuid.Uuid

actual fun createUniqueKey(key: String): String = "$key${Uuid.randomUUID()}"