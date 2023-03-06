package ru.alexgladkov.shared.helpers

fun generateProducts(count: Int): List<DataModel> = ArrayList<DataModel>().apply {
    for (i in 0..count) {
        add(DataModel(id = i.toString(), title = "Product #$i"))
    }
}

fun generateCities(count: Int): List<DataModel> = ArrayList<DataModel>().apply {
    for (i in 0..count) {
        add(DataModel(id = i.toString(), title = "City #$i"))
    }
}

data class DataModel(
    val id: String,
    val title: String
)