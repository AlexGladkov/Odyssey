package ru.alexgladkov.odyssey.core.declarative

import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.destination.DestinationFlow
import ru.alexgladkov.odyssey.core.destination.DestinationMultiFlow

class MultistackFlowBuilder(val name: String) {

    private val _destinations: MutableList<DestinationFlow> = mutableListOf()

    fun flow(name: String, animationType: AnimationType, block: FlowBuilder.() -> Unit) {
        _destinations.add(FlowBuilder(name, animationType).apply(block).build())
    }

    fun build(): DestinationMultiFlow = DestinationMultiFlow(name, _destinations)
}