package com.example.uaspam.ui.home

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.uaspam.AgricApplications
import com.example.uaspam.ui.home.viewmodel.aktivitaspertanian.DetailAktivitasPertanianViewModel
import com.example.uaspam.ui.home.viewmodel.aktivitaspertanian.HomeAktivitasPertanianViewModel
import com.example.uaspam.ui.home.viewmodel.aktivitaspertanian.InsertAktivitasPertanianViewModel
import com.example.uaspam.ui.home.viewmodel.aktivitaspertanian.UpdateAktivitasPertanianViewModel
import com.example.uaspam.ui.home.viewmodel.panen.HomePanenViewModel
import com.example.uaspam.ui.home.viewmodel.panen.InsertPanenViewModel
import com.example.uaspam.ui.home.viewmodel.panen.UpdatePanenViewModel
import com.example.uaspam.ui.home.viewmodel.pekerja.DetailPekerjaViewModel
import com.example.uaspam.ui.home.viewmodel.pekerja.HomePekerjaViewModel
import com.example.uaspam.ui.home.viewmodel.pekerja.InsertPekerjaViewModel
import com.example.uaspam.ui.home.viewmodel.pekerja.UpdatePekerjaViewModel
import com.example.uaspam.ui.home.viewmodel.tanaman.DetailTanamanViewModel
import com.example.uaspam.ui.home.viewmodel.tanaman.HomeTanamanViewModel
import com.example.uaspam.ui.home.viewmodel.tanaman.InsertTanamanViewModel
import com.example.uaspam.ui.home.viewmodel.tanaman.UpdateTanamanViewModel

object PenyediaViewModel {

    val Factory = viewModelFactory {
        initializer { HomeTanamanViewModel(AgricApplications().container.tanamanRepository) }
        initializer { InsertTanamanViewModel(AgricApplications().container.tanamanRepository) }
        initializer { UpdateTanamanViewModel(AgricApplications().container.tanamanRepository) }
        initializer { DetailTanamanViewModel(AgricApplications().container.tanamanRepository) }

        initializer { HomePekerjaViewModel(AgricApplications().container.pekerjaRepository) }
        initializer { InsertPekerjaViewModel(AgricApplications().container.pekerjaRepository) }
        initializer { UpdatePekerjaViewModel(AgricApplications().container.pekerjaRepository) }
        initializer { DetailPekerjaViewModel(AgricApplications().container.pekerjaRepository) }


        initializer { HomePanenViewModel(AgricApplications().container.panenRepository) }
        initializer { InsertPanenViewModel(AgricApplications().container.panenRepository) }
        initializer { UpdatePanenViewModel(AgricApplications().container.panenRepository) }


        initializer { HomeAktivitasPertanianViewModel(AgricApplications().container.aktivitaspertanianRepository) }
        initializer { InsertAktivitasPertanianViewModel(AgricApplications().container.aktivitaspertanianRepository) }
        initializer { UpdateAktivitasPertanianViewModel(AgricApplications().container.aktivitaspertanianRepository) }
        initializer { DetailAktivitasPertanianViewModel(AgricApplications().container.aktivitaspertanianRepository) }

    }

    fun CreationExtras.AgricApplications(): AgricApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as AgricApplications)
}