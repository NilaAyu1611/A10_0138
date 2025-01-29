package com.example.uaspam.ui.home.viewmodel.panen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Panen
import com.example.uaspam.repository.PanenRepository
import kotlinx.coroutines.launch

// ViewModel untuk mengatur data dan logika form tambah mahasiswa
class InsertPanenViewModel(private val pn: PanenRepository): ViewModel() {

    // Data untuk menyimpan keadaan form (seperti input dari pengguna)
    var insertpaUiState by mutableStateOf(InsertpaUiState())
        private set


    /**
     * Inisialisasi `id_tanaman` dari parameter navigasi.
     * Fungsi ini dipanggil saat layar di-load dan `id_tanaman` diberikan dari layar sebelumnya.
     */
    fun initializeWithIdTanaman(id_tanaman: String) {
        insertpaUiState = insertpaUiState.copy(
            insertpaUiEvent = insertpaUiState.insertpaUiEvent.copy(id_tanaman = id_tanaman)
        )
    }

    // Fungsi untuk mengubah data form ketika ada input dari pengguna
    fun updateInsertPnState(insertpaUiEvent: InsertpaUiEvent) {
        insertpaUiState = InsertpaUiState(insertpaUiEvent = insertpaUiEvent) // Perbarui data berdasarkan event
    }

    // Fungsi untuk menambahkan data mahasiswa ke database
    suspend fun insertPn() {
        viewModelScope.launch { // Menjalankan proses di latar belakang (tidak mengganggu UI)
            try {
                // Mengambil data dari form dan mengirimnya ke repository
                pn.insertPanen(insertpaUiState.insertpaUiEvent.toPn())
            }catch (e:Exception) {
                e.printStackTrace() // Menangani error jika terjadi masalah
            }
        }
    }
}


// Menyimpan state form input
data class InsertpaUiState(
    val insertpaUiEvent: InsertpaUiEvent = InsertpaUiEvent()  // State default berisi objek kosong dari InsertUiEvent
)

// Menyimpan data input pengguna untuk form
data class InsertpaUiEvent(
    val id_panen: String = "",
    val id_tanaman: String = "",
    val tanggal_panen: String = "",
    val jumlah_panen: String = "",
    val keterangan: String = ""

)


// Fungsi untuk mengubah data InsertUiEvent menjadi Panen
fun InsertpaUiEvent.toPn(): Panen = Panen(
    id_panen = id_panen,
    id_tanaman= id_tanaman,
    tanggal_panen= tanggal_panen,
    jumlah_panen = jumlah_panen,
    keterangan = keterangan
)

// Fungsi untuk mengubah data panen menjadi InsertUiState
fun Panen.toInsertpaUiStatePkj(): InsertpaUiState = InsertpaUiState( // Mahasiswa > insertUiEvent > Masuk ke InsertUiState
    insertpaUiEvent = toInsertpaUiEvent() // Memanggil fungsi toInsertUiEvent untuk mengonversi data Mahasiswa
)

// Fungsi untuk mengubah data panen menjadi data InsertUiEvent
fun Panen.toInsertpaUiEvent(): InsertpaUiEvent = InsertpaUiEvent(
    id_panen = id_panen,
    id_tanaman= id_tanaman,
    tanggal_panen= tanggal_panen,
    jumlah_panen = jumlah_panen,
    keterangan = keterangan
)