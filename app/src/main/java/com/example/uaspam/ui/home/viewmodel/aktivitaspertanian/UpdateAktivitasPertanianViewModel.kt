package com.example.uaspam.ui.home.viewmodel.aktivitaspertanian

import com.example.uaspam.model.AktivitasPertanian


// Menyimpan state form update mahasiswa
data class UpdateakUiState(
    val updateakUiEvent: UpdateakUiEvent = UpdateakUiEvent() // State default berisi objek kosong dari UpdateUiEvent
)

// Menyimpan data input pengguna untuk form update mahasiswa
data class UpdateakUiEvent(
    val id_aktivitas: String = "",
    val id_tanaman: String = "",
    val id_pekerja: String = "",
    val tanggal_aktivitas: String = "",
    val deskripsi_aktivitas: String = ""
)

// Fungsi untuk mengubah data UpdateUiEvent menjadi Mahasiswa
fun UpdateakUiEvent.toAk(): AktivitasPertanian = AktivitasPertanian( // UpdateUiEvent > Mahasiswa > Simpan data Mhs ke db
    id_aktivitas=id_aktivitas,
    id_tanaman=id_tanaman,
    id_pekerja = id_pekerja,
    tanggal_aktivitas= tanggal_aktivitas,
    deskripsi_aktivitas= deskripsi_aktivitas
)

// Fungsi untuk mengubah data Mahasiswa menjadi UpdateUiState
fun AktivitasPertanian.toUpdateakUiStateAk(): UpdateakUiState = UpdateakUiState( // Mahasiswa > updateUiEvent > Masuk ke UpdateUiState
    updateakUiEvent = toUpdateakUiEvent() // Memanggil fungsi toUpdateUiEvent untuk mengonversi data Mahasiswa
)

// Fungsi untuk mengubah data Mahasiswa menjadi data UpdateUiEvent
fun AktivitasPertanian.toUpdateakUiEvent(): UpdateakUiEvent = UpdateakUiEvent(
    id_aktivitas=id_aktivitas,
    id_tanaman=id_tanaman,
    id_pekerja = id_pekerja,
    tanggal_aktivitas= tanggal_aktivitas,
    deskripsi_aktivitas= deskripsi_aktivitas
)

