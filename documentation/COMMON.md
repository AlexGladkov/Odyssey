### How to use it with desktop

First you need to create navigation graph. Warning! Now library use RootController 
function
```kotlin
fun generateDestinations(list: List<Destination>)
```

You can provide array of 3 types of Destinations

#### Create simple screen
```kotlin
generateDestinations(ArrayList<Destination>().apply {
    add(DestinationScreen("splash"))
})
```


#### Create flow with screens
```kotlin
generateDestinations(ArrayList<Destination>().apply {
    add(
        DestinationFlow(
            name = "auth_flow",
            screens = ArrayList<DestinationScreen>().apply {
                add(DestinationScreen("login"))
                add(DestinationScreen("login_code"))
            })
    )
})
```

#### Create multi stack with flows
```kotlin
generateDestinations(ArrayList<Destination>().apply {
    add(
        DestinationMultiFlow(
            name = "multi_flow",
            flows = ArrayList<DestinationFlow>().apply {
                add(DestinationFlow(
                    name = "first_flow",
                    screens = ArrayList<DestinationScreen>().apply {
                        add(DestinationScreen("first_screen"))
                        add(DestinationScreen("second_screen"))
                    }
                ))

                add(DestinationFlow(
                    name = "second_flow",
                    screens = ArrayList<DestinationScreen>().apply {
                        add(DestinationScreen("first_screen"))
                    }
                ))

                // Here you can create any count of flows
            }
        )
    )
})
```
