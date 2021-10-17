**Odyssey**

Odyssey it's a declarative multiplatform navigation library for Multiplatform Compose

🚧 WARNING! It's an early preview, so you use it with your own responsibility

### How to start

First add dependency to gradle

```kotlin
named("commonMain") {
    dependencies {
        implementation("io.github.alexgladkov:odyssey-core:0.0.1") // For core classes
        implementation("io.github.alexgladkov:odyssey-compose:0.0.1") // For compose extensions
    }
}
```

#### Setup

[1. Common preparations](documentation/COMMON.md)

[2. How to start with Android](documentation/ANDROID.md)

[3. How to start with Desktop](documentation/DESKTOP.md)

[4. How to start with iOS](documentation/IOS.md)

#### Parameters

If you need to add params to your navigation just use params parameter in launch function

```kotlin
rootController.launch(screen = "screen_name", params = paramState.value)
```

To catch this value you can use **backStackObserver**

```kotlin
// Compose example
val navigation = rootController.backStackObserver.observeAsState()
val params = (navigation.value?.destination as? DestinationScreen)?.params
```

```swift
// iOS Example
let observer = rootController.backStackObserver.watch { navigation in
    let params = (navigation.value?.destination as? DestinationScreen)?.params
}

// Don't forget to destroy observer
observer.close()
```

#### Navigation

You must pass RootController to screen for inner navigation and if you need to open next screen
just use this. You can use String as key for screen

```kotlin
 rootController.launch("splash")
```

You can use specific flags to make navigation as you want. Now we have only one flag.
```LaunchFlag.SingleNewTask``` to clear backstack if you need to start new screen from scratch

```kotlin
 rootController.launch(screen = "splash", launchFlag = LaunchFlag.SingleNewTask)
```

#### Back

If you need to go back just use. Library supports multibackstack navigation for any of platform

```kotlin
 rootController.popBackStack()
```


### Example

Inside this project you can find example and of course if you have an issue or a question 
feel free to open new issue in Issues section

[<img src="screenshots/android-screen-favorite.png" width="250" height = "551" />](screenshots/android-screen-favorite.png)
[<img src="screenshots/desktop-screen-favorite.png" width="500" height = "375" />](screenshots/desktop-screen-favorite.png)

### License
```
MIT License

Copyright (c) 2021 Alex

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
