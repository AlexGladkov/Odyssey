includeBuild("../") {
    dependencySubstitution {
        substitute(module("ru.alexgladkov:comon-common-root")).using(project(":common:common-root"))
        substitute(module("ru.alexgladkov:odyssey-odyssey-core")).using(project(":odyssey:odyssey-core"))
        substitute(module("ru.alexgladkov:odyssey-odyssey-compose")).using(project(":odyssey:odyssey-compose"))
        substitute(module("ru.alexgladkov:common-common-root")).using(project(":common:common-root"))
    }
}

