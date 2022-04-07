Back pressed flow available by default

To support hardware back press you if you use your own setup you should use
```kotlin
rootController.setupWithActivity(this)
```

To call back operation by code you just need to call `rootController` and use `popBackStack()` function