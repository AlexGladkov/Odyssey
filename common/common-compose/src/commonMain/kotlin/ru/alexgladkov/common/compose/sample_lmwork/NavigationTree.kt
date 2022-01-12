package ru.alexgladkov.common.compose.sample_lmwork

object NavigationTree {

    enum class Root {
        Splash, Auth, Dashboard, Web, Scanner, Module, Onboarding, Guide
    }

    enum class Auth {
        Login, LoginWeb
    }

    object Dashboard {
        enum class Tabs {
            Main, Catalog, Feedback, Profile
        }

        enum class Main {
            MainStart, Category, MainAppDetail
        }

        enum class Catalog {
            CatalogStart, CatalogAppDetail
        }

        enum class Feedback {
            Message, Success
        }

        enum class Profile {
            Info, Logout
        }
    }

    enum class ProductDetail {
        ProductCard, Characteristics, Detailing
    }

    enum class Scanner {
        ScannerStart
    }
}