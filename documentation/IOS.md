### How to use it with iOS

**WARNING!** Compose for iOS is very experimental!

First, you need to setup your navigation graph (check getting started section), and then you need to configure iOS host

You can see example in [main.uikit.kt](/uikit/src/uikitMain/kotlin/main.uikit.kt) file

Then you need to configure navigation for compose. It's need to be wrapped in Column because Compose for ios can't handle 
`safeAreaInsets` right now
```kotlin
    window!!.rootViewController = Application("Odyssey Demo") {
    OdysseyTheme {
        Column(modifier = Modifier.background(Odyssey.color.primaryBackground)) {
            // To skip upper part of screen.
            Box(
                modifier = Modifier.height(56.dp)
            )

            val configuration = OdysseyConfiguration(backgroundColor = Odyssey.color.primaryBackground)
            setNavigationContent(configuration) {
                navigationGraph()
            }
        }
    }
}
```
