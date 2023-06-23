package ru.alexgladkov.odyssey.compose

import androidx.compose.runtime.saveable.Saver
import ru.alexgladkov.odyssey.core.configuration.RootControllerType

class SaverTestClass(val rootControllerType: RootControllerType) {

    fun saveInstanceState() {
        
    }
}

val testSaver = Saver<SaverTestClass, Any>(
    save = { it.saveInstanceState() },
    restore = { SaverTestClass(rootControllerType = RootControllerType.Root) }
)