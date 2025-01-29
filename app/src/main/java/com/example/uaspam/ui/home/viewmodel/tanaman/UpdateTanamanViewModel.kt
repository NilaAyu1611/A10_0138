package com.example.uaspam.ui.home.viewmodel.tanaman

import com.example.uaspam.model.Tanaman

// Menyimpan state form update mahasiswa
data class UpdateUiState(
    val updateUiEvent: UpdateUiEvent = UpdateUiEvent() // State default berisi objek kosong dari UpdateUiEvent
)

// Menyimpan data input pengguna untuk form update mahasiswa
data class UpdateUiEvent(
    val id_tanaman: String = "",
    val nama_tanaman: String = "",
    val periode_tanam: String = "",
    val deskripsi_tanaman: String = ""
)

// Fungsi untuk mengubah data UpdateUiEvent menjadi Mahasiswa
fun UpdateUiEvent.toTnm(): Tanaman = Tanaman( // UpdateUiEvent > Mahasiswa > Simpan data Mhs ke db
    id_tanaman = id_tanaman,
    nama_tanaman=nama_tanaman,
    periode_tanam=periode_tanam,
    deskripsi_tanaman = deskripsi_tanaman
)

// Fungsi untuk mengubah data Mahasiswa menjadi UpdateUiState
fun Tanaman.toUpdateUiStateTnm(): UpdateUiState = UpdateUiState( // Mahasiswa > updateUiEvent > Masuk ke UpdateUiState
    updateUiEvent = toUpdateUiEvent() // Memanggil fungsi toUpdateUiEvent untuk mengonversi data Mahasiswa
)

// Fungsi untuk mengubah data Mahasiswa menjadi data UpdateUiEvent
fun Tanaman.toUpdateUiEvent(): UpdateUiEvent = UpdateUiEvent(
    id_tanaman = id_tanaman,
    nama_tanaman=nama_tanaman,
    periode_tanam=periode_tanam,
    deskripsi_tanaman = deskripsi_tanaman
)