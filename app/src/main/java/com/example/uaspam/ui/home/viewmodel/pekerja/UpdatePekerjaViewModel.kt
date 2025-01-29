package com.example.uaspam.ui.home.viewmodel.pekerja

import com.example.uaspam.model.Pekerja

class UpdatePekerjaViewModel {
}

// Menyimpan state form update
data class UpdatepeUiState(
    val updatepeUiEvent: UpdatepeUiEvent = UpdatepeUiEvent() // State default berisi objek kosong dari UpdateUiEvent
)

// Menyimpan data input pengguna untuk form update
data class UpdatepeUiEvent(
    val id_pekerja: String = "",
    val nama_pekerja: String = "",
    val jabatan: String = "",
    val kontak_pekerja: String = ""
)

// Fungsi untuk mengubah data UpdateUiEvent menjadi
fun UpdatepeUiEvent.toPkj(): Pekerja = Pekerja( // UpdateUiEvent > Pekerja > Simpan data Mhs ke db
    id_pekerja = id_pekerja,
    nama_pekerja=nama_pekerja,
    jabatan=jabatan,
    kontak_pekerja = kontak_pekerja
)

// Fungsi untuk mengubah data Mahasiswa menjadi UpdateUiState
fun Pekerja.toUpdatepeUiStatePkj(): UpdatepeUiState = UpdatepeUiState( // Mahasiswa > updateUiEvent > Masuk ke UpdateUiState
    updatepeUiEvent = toUpdatepeUiEvent() // Memanggil fungsi toUpdateUiEvent untuk mengonversi data Mahasiswa
)

// Fungsi untuk mengubah data Mahasiswa menjadi data UpdateUiEvent
fun Pekerja.toUpdatepeUiEvent(): UpdatepeUiEvent = UpdatepeUiEvent(
    id_pekerja = id_pekerja,
    nama_pekerja=nama_pekerja,
    jabatan=jabatan,
    kontak_pekerja = kontak_pekerja
)

