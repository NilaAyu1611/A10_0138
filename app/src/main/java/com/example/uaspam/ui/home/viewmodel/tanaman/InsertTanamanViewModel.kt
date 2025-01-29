package com.example.uaspam.ui.home.viewmodel.tanaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Tanaman
import com.example.uaspam.repository.TanamanRepository
import kotlinx.coroutines.launch


// ViewModel untuk mengatur data dan logika form tambah tnm
class InsertTanamanViewModel(private val tnm: TanamanRepository): ViewModel() {

    // Data untuk menyimpan keadaan form (seperti input dari pengguna)
    var insertUiState by mutableStateOf(InsertUiState())
        private set

    // Fungsi untuk mengubah data form ketika ada input dari pengguna
    fun updateInsertTnmState(insertUiEvent: InsertUiEvent) {
        insertUiState = InsertUiState(insertUiEvent = insertUiEvent) // Perbarui data berdasarkan event
    }

    // Fungsi untuk menambahkan data tanaman ke database
    suspend fun insertTnm() {
        viewModelScope.launch { // Menjalankan proses di latar belakang (tidak mengganggu UI)
            try {
                // Mengambil data dari form dan mengirimnya ke repository
                tnm.insertTanaman(insertUiState.insertUiEvent.toTnm())
            }catch (e:Exception) {
                e.printStackTrace() // Menangani error jika terjadi masalah
            }
        }
    }
}


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