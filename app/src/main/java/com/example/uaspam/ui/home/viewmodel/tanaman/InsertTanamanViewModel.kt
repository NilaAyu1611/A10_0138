package com.example.uaspam.ui.home.viewmodel.tanaman




// Menyimpan state form input
data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent() // State default berisi objek kosong dari InsertUiEvent
)


// Menyimpan data input pengguna untuk form tanaman
data class InsertUiEvent(
    val id_tanaman: String = "",
    val nama_tanaman: String = "",
    val periode_tanam: String = "",
    val deskripsi_tanaman: String = ""
)