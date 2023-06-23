package ru.alexgladkov.odyssey.core.extensions

import platform.Foundation.NSUUID

actual fun createUniqueKey(key: String): String = "$key${NSUUID.UUID().UUIDString}"