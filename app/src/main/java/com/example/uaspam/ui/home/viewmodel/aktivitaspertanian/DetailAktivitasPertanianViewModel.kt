package com.example.uaspam.ui.home.viewmodel.aktivitaspertanian

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.AktivitasPertanian
import com.example.uaspam.repository.AktivitaspertanianRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailAktivitasPertanianViewModel(private val akRepo: AktivitaspertanianRepository) : ViewModel() {

    private val _detailakUiState = MutableStateFlow<DetailakUiState>(DetailakUiState.Loading)
    val detailakUiState: StateFlow<DetailakUiState> = _detailakUiState.asStateFlow()

    fun getAktivitasPertanianById(id_aktivitas: String) {
        viewModelScope.launch {
            try {
                val aktivitasPertanian = akRepo.getAktivitasPertanianById(id_aktivitas)
                if (aktivitasPertanian != null) {
                    _detailakUiState.value = DetailakUiState.Success(aktivitasPertanian)
                } else {
                    _detailakUiState.value = DetailakUiState.Error("Data Aktivitas Pertanian tidak ditemukan.")
                }
            } catch (e: Exception) {
                _detailakUiState.value = DetailakUiState.Error(e.localizedMessage ?: "Terjadi kesalahan.")
            }
        }
    }

    fun deleteAk(id_aktivitas: String) {
        viewModelScope.launch {
            try {
                akRepo.deleteAktivitasPertanian(id_aktivitas)
                _detailakUiState.value = DetailakUiState.Error("Data Aktivitas Pertanian telah dihapus.")
            } catch (e: Exception) {
                _detailakUiState.value = DetailakUiState.Error(e.localizedMessage ?: "Gagal menghapus data.")
            }
        }
    }
}

sealed class DetailakUiState {
    object Loading : DetailakUiState()
    data class Success(val aktivitasPertanian: AktivitasPertanian) : DetailakUiState()
    data class Error(val message: String) : DetailakUiState()
}



