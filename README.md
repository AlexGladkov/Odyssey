**Odyssey**

Odyssey it's a declarative multiplatform navigation library for Multiplatform Compose

🚧 WARNING! It's an early preview, so you use it with your own responsibility

### How to start

First add dependency to gradle

```kotlin
named("commonMain") {
    dependencies {
        implementation("...") // For core classes
        implementation("...") // For compose extensions
    }
}
```

### Main idea

#### Setup

The main class in library is `RootController` It provides navigation control and host
backstack inside. 

For setup navigation graph in common code you need to use

```kotlin
@Composable
fun RootContainer(
    rootController: RootController
) {
    RootHost(startScreen = NavigationTree.Root.Start, rootController = rootController) {
        destination(screen = NavigationTree.Root.Start) {
            // Here is the place to draw something in Compose
            // ...
            // Or you can provide composable function like below
            SomeScreen(rootController) 
        }
    }
}
```

#### Parameters

Params included inside destination by design

```kotlin
destination(screen = NavigationTree.Root.Start) {
    val key = params as? String
    SomeScreen(rootController, params)
}
```

#### Navigation

You must pass RootController to screen for inner navigation and if you need to open next screen
just use this

```kotlin
 rootController.launch(NavigationTree.Root.Container)
```

or this if you want to pass params

```kotlin
 rootController.launch(NavigationTree.Root.Container, params = "Your any object")
```

#### Back

If you need to go back just use. Library supports multibackstack navigation for any of platform

```kotlin
 rootController.popBackStack()
```

### Android Setup

For Android we must support hardware back pressing, so you need to do this in your Single Activity

```kotlin
  val rootController = RootController()
  rootController.setupWithActivity(this)
```

### Example

Inside this project you can find example and of course if you have an issue or a question 
feel free to open new issue in Issues section

### License
```
MIT License

Copyright (c) 2021 Aleksey Gladkov (@AlexGladkov)

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
