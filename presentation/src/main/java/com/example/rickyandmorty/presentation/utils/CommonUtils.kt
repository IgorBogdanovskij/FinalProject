package com.example.rickyandmorty.presentation.utils

fun <T> unsafeLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)

fun String.toUpperCaseFromList(list: List<String>): String {

    val strForSearch = list.joinToString(" ") {
        it.replaceFirstChar { it1 -> it1.uppercase() }
    }

    return strForSearch
}