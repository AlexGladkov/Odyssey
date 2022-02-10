package ru.alexgladkov.odyssey.core.declarative

import ru.alexgladkov.odyssey.core.animations.AnimationType

//class FlowBuilder(val name: String, val defaultAnimationType: AnimationType) {
//
//    private val _destinations: MutableList<DestinationScreen> = mutableListOf()
//    val destinations: List<DestinationScreen> = _destinations
//
//    fun destination(screen: String) {
//        _destinations.add(DestinationScreen(screen))
//    }
//
//    fun build(): DestinationFlow = DestinationFlow(
//        name = name,
//        params = null,
//        screens = _destinations
//    )
//}