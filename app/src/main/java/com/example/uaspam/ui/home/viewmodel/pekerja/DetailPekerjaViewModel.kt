package com.example.uaspam.ui.home.viewmodel.pekerja

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Pekerja
import com.example.uaspam.repository.PekerjaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailPekerjaViewModel(private val pkjRepo: PekerjaRepository) : ViewModel() {

    private val _detailpeUiState = MutableStateFlow<DetailpeUiState>(DetailpeUiState.Loading)
    val detailpeUiState: StateFlow<DetailpeUiState> = _detailpeUiState.asStateFlow()

    fun getPekerjaById(id_pekerja: String) {
        viewModelScope.launch {
            try {
                val pekerja = pkjRepo.getPekerjaById(id_pekerja)
                if (pekerja != null) {
                    _detailpeUiState.value = DetailpeUiState.Success(pekerja)
                } else {
                    _detailpeUiState.value = DetailpeUiState.Error("Data Pekerja tidak ditemukan.")
                }
            } catch (e: Exception) {
                _detailpeUiState.value = DetailpeUiState.Error(e.localizedMessage ?: "Terjadi kesalahan.")
            }
        }
    }

    fun deletePkj(id_pekerja: String) {
        viewModelScope.launch {
            try {
                pkjRepo.deletePekerja(id_pekerja)
                _detailpeUiState.value = DetailpeUiState.Error("Data Pekerja telah dihapus.")
            } catch (e: Exception) {
                _detailpeUiState.value = DetailpeUiState.Error(e.localizedMessage ?: "Gagal menghapus data.")
            }
        }
    }
}

sealed class DetailpeUiState {
    object Loading : DetailpeUiState()
    data class Success(val pekerja: Pekerja) : DetailpeUiState()
    data class Error(val message: String) : DetailpeUiState()
}






