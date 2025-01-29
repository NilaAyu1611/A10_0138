package com.example.uaspam.ui.home.viewmodel.pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Pekerja
import com.example.uaspam.repository.PekerjaRepository
import kotlinx.coroutines.launch

// ViewModel untuk mengatur data dan logika form update mahasiswa
class UpdatePekerjaViewModel(private val pkj: PekerjaRepository) : ViewModel() {

    // Data untuk menyimpan keadaan form (seperti input dari pengguna)
    var updatepeUiState by mutableStateOf(UpdatepeUiState())
        private set

    // Fungsi untuk mendapatkan data mahasiswa berdasarkan NIM
    fun getPekerjaById(id_pekerja: String) {
        viewModelScope.launch {
            try {
                val pekerja = pkj.getPekerjaById(id_pekerja)
                updatepeUiState = updatepeUiState.copy(
                    updatepeUiEvent = UpdatepeUiEvent(
                        id_pekerja =pekerja.id_pekerja,
                        nama_pekerja = pekerja.nama_pekerja,
                        jabatan = pekerja.jabatan,
                        kontak_pekerja = pekerja.kontak_pekerja
                    )
                )
            } catch (e: Exception) {
                // Handle error jika diperlukan
            }
        }
    }

    // Fungsi untuk mengubah data form ketika ada input dari pengguna
    fun updatePkjState(updatepeUiEvent: UpdatepeUiEvent) {
        updatepeUiState = UpdatepeUiState(updatepeUiEvent = updatepeUiEvent) // Perbarui data berdasarkan event
    }

    // Fungsi untuk memuat data mahasiswa ke dalam form untuk di-update
    fun loadPekerjaData(pekerja: Pekerja) {
        updatepeUiState = pekerja.toUpdatepeUiStatePkj()
    }

    // Fungsi untuk memperbarui data mahasiswa ke database
    fun updatePkj() {
        viewModelScope.launch {
            try {
                val pekerja = updatepeUiState.updatepeUiEvent.toPkj()
                pkj.updatePekerja(updatepeUiState.updatepeUiEvent.id_pekerja, pekerja)
                println("Update berhasil: $pekerja")
            } catch (e: Exception) {
                e.printStackTrace() // Log error jika ada
            }
        }
    }

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

