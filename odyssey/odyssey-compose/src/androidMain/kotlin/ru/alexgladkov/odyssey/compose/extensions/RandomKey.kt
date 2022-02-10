package ru.alexgladkov.odyssey.compose.extensions

import com.benasher44.uuid.Uuid

actual fun createUniqueKey(key: String): String = "$key${Uuid.randomUUID()}"