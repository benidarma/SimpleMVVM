package com.simple.mvvm.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Source(
    val id: String? = null,
    val name: String
) {
    companion object {
        fun dummy(id: String = "1") = Source(
            id = id,
            name = "name $id"
        )
    }
}