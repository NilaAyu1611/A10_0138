package com.example.uaspam.ui.home

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.uaspam.AgricApplications
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

    }
}