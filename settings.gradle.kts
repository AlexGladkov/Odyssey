include(
    ":common:common-sample",

    ":odyssey:odyssey-core",
    ":odyssey:odyssey-compose",

    ":android",
    ":desktop",
    ":uikit"
)

//includeBuild("convention-plugins")
rootProject.name = "odyssey"