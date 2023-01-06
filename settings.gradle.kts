include(
    ":common:common-sample",

    ":odyssey:odyssey-core",
    ":odyssey:odyssey-compose",
    ":odyssey:odyssey-android",

    ":android",
    ":desktop",
    ":uikit",
    ":hilt",
)

//includeBuild("convention-plugins")
rootProject.name = "odyssey"