package ru.alexgladkov.hilt_demo.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ViewModelOne @Inject constructor() : ViewModel() {

    val randomLifecycleValue = Random(System.currentTimeMillis()).nextInt(1000)
}