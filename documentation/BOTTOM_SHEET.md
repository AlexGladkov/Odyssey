Bottom sheet navigation uses new version of Odyssey and hidden inside 
Root Controller class. To use bottom sheet just call this from RootController

```kotlin
val rootController = LocalRootController.current
val modalController = rootController.findModalController()
```

Then you need to configure modal sheet controller
You can customize height of modal sheet with percent of screen and corner radius
Also you **CAN** show many bottom sheets

```kotlin
val modalSheetConfiguration = ModalSheetConfiguration(0.7f, cornerRadius = 16)
```

To close modal sheet you can use this
```kotlin
    modalController.popBackStack()
```