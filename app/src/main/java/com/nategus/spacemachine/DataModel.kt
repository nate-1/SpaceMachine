package com.nategus.spacemachine


enum class Type {
    Button,
    Switch,
    Slider,
    Radio
}

data class Element (
    val type: Type,
    val name: String,
    val data: Any
)
