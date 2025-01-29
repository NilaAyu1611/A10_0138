package com.example.uaspam.ui.home.viewmodel.tanaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Tanaman
import com.example.uaspam.repository.TanamanRepository
import kotlinx.coroutines.launch

// ViewModel untuk mengatur data dan logika form update tnm
class UpdateTanamanViewModel(private val tnm: TanamanRepository) : ViewModel() {

    // Data untuk menyimpan keadaan form (seperti input dari pengguna)
    var updateUiState by mutableStateOf(UpdateUiState())
        private set

    // Fungsi untuk mendapatkan data mahasiswa berdasarkan NIM
    fun getTanamanById(id_tanaman: String) {
        viewModelScope.launch {
            try {
                val tanaman = tnm.getTanamanById(id_tanaman)
                updateUiState = updateUiState.copy(
                    updateUiEvent = UpdateUiEvent(
                        id_tanaman = tanaman.id_tanaman,
                        nama_tanaman = tanaman.nama_tanaman,
                        periode_tanam = tanaman.periode_tanam,
                        deskripsi_tanaman = tanaman.deskripsi_tanaman
                    )
                )
            } catch (e: Exception) {
                // Handle error jika diperlukan
            }
        }
    }

    // Fungsi untuk mengubah data form ketika ada input dari pengguna
    fun updateTnmState(updateUiEvent: UpdateUiEvent) {
        updateUiState = UpdateUiState(updateUiEvent = updateUiEvent) // Perbarui data berdasarkan event
    }

    // Fungsi untuk memuat data mahasiswa ke dalam form untuk di-update
    fun loadTanamanData(tanaman: Tanaman) {
        updateUiState = tanaman.toUpdateUiStateTnm()
    }

    // Fungsi untuk memperbarui data mahasiswa ke database
    fun updateTnm() {
        viewModelScope.launch {
            try {
                val tanaman = updateUiState.updateUiEvent.toTnm()
                tnm.updateTanaman(updateUiState.updateUiEvent.id_tanaman, tanaman)
                println("Update berhasil: $tanaman")
            } catch (e: Exception) {
                e.printStackTrace() // Log error jika ada
            }
        }
    }

}


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