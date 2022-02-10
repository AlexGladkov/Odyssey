package ru.alexgladkov.odyssey.core.declarative

import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.screen.ScreenBundle

//class NewRootControllerBuilder {
//    private val _destinations: MutableList<ScreenBundle> = mutableListOf()
//
//    fun destination(destination: String) {
//        _destinations.add(ScreenBundle(key = destination, params = null))
//    }
//
//    fun build(): List<ScreenBundle> = _destinations
//}
//
//class RootControllerBuilder {
//
//    private val _destinations: MutableList<Destination> = mutableListOf()
//
//    fun destination(destination: String) {
//        _destinations.add(DestinationScreen(destination))
//    }
//
//    fun flow(name: String, animationType: AnimationType, block: FlowBuilder.() -> Unit) {
//        _destinations.add(FlowBuilder(name, animationType).apply(block).build())
//    }
//
//    fun multistack(name: String, block: MultistackFlowBuilder.() -> Unit) {
//        _destinations.add(MultistackFlowBuilder(name).apply(block).build())
//    }
//
//    fun build(): List<Destination> = _destinations
//}