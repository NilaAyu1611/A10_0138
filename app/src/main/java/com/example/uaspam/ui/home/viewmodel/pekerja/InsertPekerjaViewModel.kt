package com.example.uaspam.ui.home.viewmodel.pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Pekerja
import com.example.uaspam.repository.PekerjaRepository
import kotlinx.coroutines.launch


// ViewModel untuk mengatur data dan logika form tambah mahasiswa
class InsertPekerjaViewModel(private val pkj: PekerjaRepository): ViewModel() {

    // Data untuk menyimpan keadaan form (seperti input dari pengguna)
    var insertpeUiState by mutableStateOf(InsertpeUiState())
        private set

    // Fungsi untuk mengubah data form ketika ada input dari pengguna
    fun updateInsertPkjState(insertpeUiEvent: InsertpeUiEvent) {
        insertpeUiState = InsertpeUiState(insertpeUiEvent = insertpeUiEvent) // Perbarui data berdasarkan event
    }

    // Fungsi untuk menambahkan data mahasiswa ke database
    suspend fun insertPkj() {
        viewModelScope.launch { // Menjalankan proses di latar belakang (tidak mengganggu UI)
            try {
                // Mengambil data dari form dan mengirimnya ke repository
                pkj.insertPekerja(insertpeUiState.insertpeUiEvent.toPkj())
            }catch (e:Exception) {
                e.printStackTrace() // Menangani error jika terjadi masalah
            }
        }
    }
}

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