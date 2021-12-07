### How to make animations

Odyssey provides beautiful animations to developers. Now you can use only predefined animations style but with
transition time customization

#### Animation in general

For launching new screen with animation you can use standard launch function, but with animation type

```kotlin
rootController.launch(
    screen = "auth",
    params = "param",
    launchFlag = LaunchFlag.SingleNewTask,
    animationType = AnimationType.Present(animationTime = 500)
)
```

For your comfort Odyssey provides top level functions for all default animations with 500 ms for animation time

```kotlin
defaultFadeAnimation() // For fade animations
defaultPushAnimation() // For inner push animations
defaultPresentationAnimation() // For present modal animations
```

And also you can use another function to start new screen. It's completely copy of launch function but with predefined
animations

```kotlin
rootController.push() // For inner push animations
rootController.present() // For present modal animations
```