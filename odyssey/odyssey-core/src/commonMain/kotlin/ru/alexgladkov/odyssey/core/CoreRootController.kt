package ru.alexgladkov.odyssey.core

abstract class CoreRootController<T> {
    abstract var onScreenNavigate: ((String, String) -> Unit)?
    protected abstract val _backstack: MutableList<T>
}