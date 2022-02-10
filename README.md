[![Maven metadata URL](https://img.shields.io/maven-metadata/v?color=blue&metadataUrl=https://s01.oss.sonatype.org/service/local/repo_groups/public/content/io/github/alexgladkov/odyssey-core/maven-metadata.xml&style=for-the-badge)](https://repo.maven.apache.org/maven2/io/github/alexgladkov/)
[![Support Platform](https://img.shields.io/static/v1?label=platforms&message=windows%20|%20macos%20|%20linux%20|%20android&color=green&style=for-the-badge)](https://github.com/AlexGladkov/Odyssey/tree/main/common/common-compose)
[![Compose Version](https://img.shields.io/static/v1?label=Compose%20version&message=v%201.0.0&color=purple&style=for-the-badge)](https://www.jetbrains.com/ru-ru/lp/compose-mpp/)

**Odyssey**

Odyssey it's a declarative multiplatform navigation library for Multiplatform Compose

🚧 WARNING! It's an early preview, so you use it with your own responsibility

### Getting Started

First add dependency to gradle

```kotlin
named("commonMain") {
    dependencies {
        implementation("io.github.alexgladkov:odyssey-core:0.3.2") // For core classes
        implementation("io.github.alexgladkov:odyssey-compose:0.3.2") // For compose extensions
    }
}
```

### Start
This library contains two basic entities for work - RootController and NavigationGraph.

RootController needs to make navigation and works like single action

NavigationGraph describes your destinations and connections between them

To create navigation graph just create this extension

```kotlin 
fun RootComposeBuilder.generateGraph() {}
```

Then you can add screens inside this navigation graph and connect it to platforms. For now you can use 
this types of navigation

[1. Simple Screen](documentation/SIMPLE_SCREEN.md)
[2. Flow](documentation/FLOW_SCREEn.md)
[3. Bottom Navigation](documentation/BOTTOM_NAVIGATION.md)
[4. Bottom Sheet Navigation](documentation/BOTTOM_SHEET.md)

#### Navigation
[1. How to connect Android](documentation/ANDROID.md)
[2. How to connect Desktop](documentation/DESKTOP.md)
[3. How to connect iOS](documentation/IOS.md)

[4. Animations](documentation/ANIMATIONS.md)

#### Parameters

If you need to add params to your navigation just use params parameter in launch function

```kotlin
rootController.launch(screen = "screen_name", params = paramState.value)
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
