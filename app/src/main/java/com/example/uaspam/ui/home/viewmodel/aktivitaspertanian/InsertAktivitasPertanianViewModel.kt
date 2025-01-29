package com.example.uaspam.ui.home.viewmodel.aktivitaspertanian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.AktivitasPertanian
import com.example.uaspam.repository.AktivitaspertanianRepository
import kotlinx.coroutines.launch

// ViewModel untuk mengatur data dan logika form tambah mahasiswa
class InsertAktivitasPertanianViewModel(private val ak: AktivitaspertanianRepository): ViewModel() {

    // Data untuk menyimpan keadaan form (seperti input dari pengguna)
    var insertakUiState by mutableStateOf(InsertakUiState())
        private set

    // Fungsi untuk mengubah data form ketika ada input dari pengguna
    fun updateInsertAkState(insertakUiEvent: InsertakUiEvent) {
        insertakUiState = InsertakUiState(insertakUiEvent = insertakUiEvent) // Perbarui data berdasarkan event
    }

    // Fungsi untuk menambahkan data mahasiswa ke database
    suspend fun insertAk() {
        viewModelScope.launch { // Menjalankan proses di latar belakang (tidak mengganggu UI)
            try {
                // Mengambil data dari form dan mengirimnya ke repository
                ak.insertAktivitasPertanian(insertakUiState.insertakUiEvent.toAk())
            }catch (e:Exception) {
                e.printStackTrace() // Menangani error jika terjadi masalah
            }
        }
    }
}

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