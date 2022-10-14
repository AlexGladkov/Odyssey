package ru.alexgladkov.odyssey.compose.extensions

import platform.Foundation.NSUUID

actual fun createUniqueKey(key: String): String = "$key${NSUUID.UUID().UUIDString}"