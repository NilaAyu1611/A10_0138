package com.example.uaspam.ui.home.viewmodel.panen

import com.example.uaspam.model.Panen

// Menyimpan state form input
data class InsertpaUiState(
    val insertpaUiEvent: InsertpaUiEvent = InsertpaUiEvent()  // State default berisi objek kosong dari InsertUiEvent
)

// Menyimpan data input pengguna untuk form
data class InsertpaUiEvent(
    val id_panen: String = "",
    val id_tanaman: String = "",
    val tanggal_panen: String = "",
    val jumlah_panen: String = "",
    val keterangan: String = ""

)


// Fungsi untuk mengubah data InsertUiEvent menjadi Panen
fun InsertpaUiEvent.toPn(): Panen = Panen(
    id_panen = id_panen,
    id_tanaman= id_tanaman,
    tanggal_panen= tanggal_panen,
    jumlah_panen = jumlah_panen,
    keterangan = keterangan
)

// Fungsi untuk mengubah data panen menjadi InsertUiState
fun Panen.toInsertpaUiStatePkj(): InsertpaUiState = InsertpaUiState( // Mahasiswa > insertUiEvent > Masuk ke InsertUiState
    insertpaUiEvent = toInsertpaUiEvent() // Memanggil fungsi toInsertUiEvent untuk mengonversi data Mahasiswa
)

// Fungsi untuk mengubah data panen menjadi data InsertUiEvent
fun Panen.toInsertpaUiEvent(): InsertpaUiEvent = InsertpaUiEvent(
    id_panen = id_panen,
    id_tanaman= id_tanaman,
    tanggal_panen= tanggal_panen,
    jumlah_panen = jumlah_panen,
    keterangan = keterangan
)