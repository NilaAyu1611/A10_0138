package com.example.uaspam.model

import kotlinx.serialization.Serializable

@Serializable
data class Tanaman(
    val id_tanaman: String,
    val nama_tanaman: String,
    val periode_tanam: String,
    val deskripsi_tanaman: String
)