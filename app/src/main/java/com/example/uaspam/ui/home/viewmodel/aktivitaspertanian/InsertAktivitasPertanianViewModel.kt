package com.example.uaspam.ui.home.viewmodel.aktivitaspertanian

import com.example.uaspam.model.AktivitasPertanian

// Menyimpan state form input mahasiswa
data class InsertakUiState(
    val insertakUiEvent: InsertakUiEvent = InsertakUiEvent()  // State default berisi objek kosong dari InsertUiEvent
)


// Menyimpan data input pengguna untuk form mahasiswa
data class InsertakUiEvent(
    val id_aktivitas: String = "",
    val id_tanaman: String = "",
    val id_pekerja: String = "",
    val tanggal_aktivitas: String = "",
    val deskripsi_aktivitas: String = ""
)


// Fungsi untuk mengubah data InsertUiEvent menjadi Mahasiswa
fun InsertakUiEvent.toAk(): AktivitasPertanian = AktivitasPertanian(
    id_aktivitas=id_aktivitas,
    id_tanaman=id_tanaman,
    id_pekerja = id_pekerja,
    tanggal_aktivitas= tanggal_aktivitas,
    deskripsi_aktivitas= deskripsi_aktivitas
)

// Fungsi untuk mengubah data Mahasiswa menjadi InsertUiState
fun AktivitasPertanian.toInsertakUiStateAk(): InsertakUiState = InsertakUiState( // Mahasiswa > insertUiEvent > Masuk ke InsertUiState
    insertakUiEvent = toInsertakUiEvent() // Memanggil fungsi toInsertUiEvent untuk mengonversi data Mahasiswa
)

// Fungsi untuk mengubah data Mahasiswa menjadi data InsertUiEvent
fun AktivitasPertanian.toInsertakUiEvent(): InsertakUiEvent = InsertakUiEvent(
    id_aktivitas=id_aktivitas,
    id_tanaman=id_tanaman,
    id_pekerja = id_pekerja,
    tanggal_aktivitas= tanggal_aktivitas,
    deskripsi_aktivitas= deskripsi_aktivitas


)