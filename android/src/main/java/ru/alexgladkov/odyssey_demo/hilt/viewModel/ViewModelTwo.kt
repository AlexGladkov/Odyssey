package ru.alexgladkov.odyssey_demo.hilt.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ViewModelTwo @Inject constructor() : ViewModel() {

    val randomLifecycleValue = Random(System.currentTimeMillis()).nextInt(1000)
}
