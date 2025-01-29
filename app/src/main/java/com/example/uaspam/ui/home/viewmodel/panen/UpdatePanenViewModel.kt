package com.example.uaspam.ui.home.viewmodel.panen

import com.example.uaspam.model.Panen


// Menyimpan state form update mahasiswa
data class UpdatepaUiState(
    val updatepaUiEvent: UpdatepaUiEvent = UpdatepaUiEvent() // State default berisi objek kosong dari UpdateUiEvent
)

// Menyimpan data input pengguna untuk form update mahasiswa
data class UpdatepaUiEvent(
    val id_panen: String = "",
    val id_tanaman: String = "",
    val tanggal_panen: String = "",
    val jumlah_panen: String = "",
    val keterangan: String = ""
)


fun UpdatepaUiEvent.toPn(): Panen = Panen( // UpdateUiEvent > Mahasiswa > Simpan data Mhs ke db
    id_panen = id_panen,
    id_tanaman=id_tanaman,
    tanggal_panen=tanggal_panen,
    jumlah_panen = jumlah_panen,
    keterangan = keterangan
)

// Fungsi untuk mengubah data panen menjadi UpdateUiState
fun Panen.toUpdatepaUiStatePn(): UpdatepaUiState = UpdatepaUiState( // Mahasiswa > updateUiEvent > Masuk ke UpdateUiState
    updatepaUiEvent = toUpdatepaUiEvent() // Memanggil fungsi toUpdateUiEvent untuk mengonversi data Mahasiswa
)

// Fungsi untuk mengubah data pane menjadi data UpdateUiEvent
fun Panen.toUpdatepaUiEvent(): UpdatepaUiEvent = UpdatepaUiEvent(
    id_panen = id_panen,
    id_tanaman=id_tanaman,
    tanggal_panen=tanggal_panen,
    jumlah_panen = jumlah_panen,
    keterangan = keterangan
)

