package ru.alexgladkov.odyssey.compose.navigation

/**
 * Compose multi stack builder, declarative helper for navigation graph builder
 * @see DestinationMultiFlow
 * @param name - flow name
 */
//class ComposeMultiStackBuilder(val name: String) {
//
//    private val _destinations: MutableList<DestinationFlow> = mutableListOf()
//    private val _screenMap: MutableScreenMap = hashMapOf()
//
//    val screenMap: ScreenMap = _screenMap
//
//    fun addFlow(destinationFlow: DestinationFlow, map: ScreenMap) {
//        _destinations.add(destinationFlow)
//        _screenMap.putAll(map)
//    }
//
//    fun build(): DestinationMultiFlow = DestinationMultiFlow(name, _destinations)
//}
//
//fun ComposeMultiStackBuilder.tab(
//    name: String,
//    block: ComposeFlowBuilder.() -> Unit
//) {
//    val builder = ComposeFlowBuilder(name)
//    val destinationFlow = builder.apply(block).build()
//
//    addFlow(destinationFlow, builder.screenMap)
//}