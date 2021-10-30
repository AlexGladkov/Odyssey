package ru.alexgladkov.common.compose

object NavigationTree {
    enum class Root {
        Splash, Auth, Main, Dialog
    }

    enum class Auth {
        Onboarding, Login, TwoFactor
    }

    enum class Main {
        Feed, Detail
    }

    enum class Favorite {
        Flow
    }

    enum class Settings {
        Profile
    }

    enum class Tabs {
        Main, Favorite, Settings
    }
}