package com.halil.recipeapps.data.model

data class User(
    val name: String = "",
    val surname: String = "",
    val age: Int = 0,
    val email: String = ""
) {
    // No-argument constructor
    constructor() : this("", "", 0, "")
}