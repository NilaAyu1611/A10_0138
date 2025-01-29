package com.example.uaspam.ui.home.viewmodel.pekerja

import com.example.uaspam.model.Pekerja


// Menyimpan state form input mahasiswa
data class InsertpeUiState(
    val insertpeUiEvent: InsertpeUiEvent = InsertpeUiEvent()  // State default berisi objek kosong dari InsertUiEvent
)

// Menyimpan data input pengguna untuk form mahasiswa
data class InsertpeUiEvent(
    val id_pekerja: String = "",
    val nama_pekerja: String = "",
    val jabatan: String = "",
    val kontak_pekerja: String = ""

)


// Fungsi untuk mengubah data InsertUiEvent menjadi Mahasiswa
fun InsertpeUiEvent.toPkj(): Pekerja = Pekerja(
    id_pekerja = id_pekerja,
    nama_pekerja= nama_pekerja,
    jabatan= jabatan,
    kontak_pekerja = kontak_pekerja

)

// Fungsi untuk mengubah data Mahasiswa menjadi InsertUiState
fun Pekerja.toInsertpeUiStatePkj(): InsertpeUiState = InsertpeUiState( // Mahasiswa > insertUiEvent > Masuk ke InsertUiState
    insertpeUiEvent = toInsertpeUiEvent() // Memanggil fungsi toInsertUiEvent untuk mengonversi data Mahasiswa
)

// Fungsi untuk mengubah data Mahasiswa menjadi data InsertUiEvent
fun Pekerja.toInsertpeUiEvent(): InsertpeUiEvent = InsertpeUiEvent(
    id_pekerja = id_pekerja,
    nama_pekerja= nama_pekerja,
    jabatan= jabatan,
    kontak_pekerja = kontak_pekerja


)