package com.example.uaspam.ui.home.viewmodel.tanaman

import com.example.uaspam.model.Tanaman


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


// Fungsi untuk mengubah data InsertUiEvent menjadi
fun InsertUiEvent.toTnm(): Tanaman = Tanaman( // InsertUiEvent > Mahasiswa > Simpan data Mhs ke db
    id_tanaman = id_tanaman,
    nama_tanaman=nama_tanaman,
    periode_tanam=periode_tanam,
    deskripsi_tanaman = deskripsi_tanaman

)

// Fungsi untuk mengubah data Mahasiswa menjadi InsertUiState
fun Tanaman.toInsertUiStateTnm(): InsertUiState = InsertUiState( // Mahasiswa > insertUiEvent > Masuk ke InsertUiState
    insertUiEvent = toInsertUiEvent() // Memanggil fungsi toInsertUiEvent untuk mengonversi data Mahasiswa
)

// Fungsi untuk mengubah data Mahasiswa menjadi data InsertUiEvent
fun Tanaman.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    id_tanaman = id_tanaman,
    nama_tanaman=nama_tanaman,
    periode_tanam=periode_tanam,
    deskripsi_tanaman = deskripsi_tanaman

)