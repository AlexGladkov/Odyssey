include(
    ":common:common-root",
    ":common:common-compose",
    ":common:common-shared",

    ":odyssey:odyssey-core",
    ":odyssey:odyssey-compose",

    ":android",
    ":desktop"
)

//includeBuild("convention-plugins")
rootProject.name = "odyssey"