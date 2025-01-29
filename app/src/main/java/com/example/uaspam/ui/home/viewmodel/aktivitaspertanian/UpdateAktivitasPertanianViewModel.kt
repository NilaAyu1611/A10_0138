package com.example.uaspam.ui.home.viewmodel.aktivitaspertanian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.AktivitasPertanian
import com.example.uaspam.repository.AktivitaspertanianRepository
import kotlinx.coroutines.launch

// ViewModel untuk mengatur data dan logika form update mahasiswa
class UpdateAktivitasPertanianViewModel(private val ak: AktivitaspertanianRepository) : ViewModel() {

    // Data untuk menyimpan keadaan form (seperti input dari pengguna)
    var updateakUiState by mutableStateOf(UpdateakUiState())
        private set

    // Fungsi untuk mendapatkan data mahasiswa berdasarkan id
    fun getAktivitasPertanianById(id_aktivitas: String) {
        viewModelScope.launch {
            try {
                val aktivitasPertanian = ak.getAktivitasPertanianById(id_aktivitas  )
                updateakUiState = updateakUiState.copy(
                    updateakUiEvent = UpdateakUiEvent(
                        id_aktivitas =aktivitasPertanian.id_aktivitas,
                        id_tanaman =aktivitasPertanian.id_tanaman,
                        id_pekerja =aktivitasPertanian.id_pekerja,
                        tanggal_aktivitas = aktivitasPertanian.tanggal_aktivitas,
                        deskripsi_aktivitas = aktivitasPertanian.deskripsi_aktivitas
                    )
                )
            } catch (e: Exception) {
                // Handle error jika diperlukan
            }
        }
    }

    // Fungsi untuk mengubah data form ketika ada input dari pengguna
    fun updateAkState(updateakUiEvent: UpdateakUiEvent) {
        updateakUiState = UpdateakUiState(updateakUiEvent = updateakUiEvent) // Perbarui data berdasarkan event
    }

    // Fungsi untuk memuat data mahasiswa ke dalam form untuk di-update
    fun loadAktivitasPertanianData(aktivitasPertanian: AktivitasPertanian) {
        updateakUiState = aktivitasPertanian.toUpdateakUiStateAk()
    }

    // Fungsi untuk memperbarui data mahasiswa ke database
    fun updateAk() {
        viewModelScope.launch {
            try {
                val aktivitasPertanian = updateakUiState.updateakUiEvent.toAk()
                ak.updateAktivitasPertanian(updateakUiState.updateakUiEvent.id_aktivitas, aktivitasPertanian)
                println("Update berhasil: $aktivitasPertanian")
            } catch (e: Exception) {
                e.printStackTrace() // Log error jika ada
            }
        }
    }

}


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

