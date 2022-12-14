package com.nategus.spacemachine

enum class ElementType {
    Button,
    Switch
}

data class Element (
    val id: Int,
    val name: String,
    var value: Boolean
)