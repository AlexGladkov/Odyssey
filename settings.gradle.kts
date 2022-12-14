include(
    ":common:common-sample",

    ":odyssey:odyssey-core",
    ":odyssey:odyssey-compose",
    ":odyssey:odyssey-android",

    ":android",
    ":desktop",
    ":uikit"
)

//includeBuild("convention-plugins")
rootProject.name = "odyssey"