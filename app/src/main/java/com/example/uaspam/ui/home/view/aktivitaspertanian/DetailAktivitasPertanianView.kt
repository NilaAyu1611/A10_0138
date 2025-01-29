package com.example.uaspam.ui.home.view.aktivitaspertanian

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.navigation.DestinasiNavigasi
import com.example.uaspam.ui.customwidget.CustomTopAppBar
import com.example.uaspam.ui.home.PenyediaViewModel
import com.example.uaspam.ui.home.viewmodel.aktivitaspertanian.DetailAktivitasPertanianViewModel
import com.example.uaspam.ui.home.viewmodel.aktivitaspertanian.DetailakUiState

object DestinasiDetailAktivitasPertanian : DestinasiNavigasi {
    override val route = "item_detail"
    override val titleRes = "Detail Aktivitas"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAktivitasPertanianView(
    id_aktivitas: String,
    navigateBack: () -> Unit,
    navigateEditAktivitas: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailAktivitasPertanianViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(id_aktivitas) {
        viewModel.getAktivitasPertanianById(id_aktivitas)
    }

    val detailakUiState by viewModel.detailakUiState.collectAsState(initial = DetailakUiState.Loading)

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailAktivitasPertanian.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            if (detailakUiState is DetailakUiState.Success) {
                FloatingActionButton(
                    onClick = { navigateEditAktivitas((detailakUiState as DetailakUiState.Success).aktivitasPertanian.id_aktivitas) },
                    shape = RoundedCornerShape(50),
                    containerColor = Color(0xFF005F2F),
                    contentColor = Color.White,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Aktivitas"
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF826B0D), Color(0xFF022F1F), Color(0xFF909B00))
                    )
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when (val state = detailakUiState) {
                is DetailakUiState.Loading -> {
                    CircularProgressIndicator(color = Color.White)
                }
                is DetailakUiState.Success -> {
                    DetailAktivitasContent(
                        state.aktivitasPertanian.id_aktivitas,
                        state.aktivitasPertanian.id_tanaman,
                        state.aktivitasPertanian.id_pekerja,
                        state.aktivitasPertanian.tanggal_aktivitas,
                        state.aktivitasPertanian.deskripsi_aktivitas
                    )
                }
                is DetailakUiState.Error -> {
                    Text("Error: Gagal memuat data Aktivitas Pertanian", color = Color.Red)
                }
            }
        }
    }
}

@Composable
fun DetailAktivitasContent(
    id_aktivitas: String,
    id_tanaman: String,
    id_pekerja: String,
    tanggal_aktivitas: String,
    deskripsi_aktivitas: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            ComponentDetailAktivitas("ID Aktivitas", id_aktivitas)
            ComponentDetailAktivitas("ID Tanaman", id_tanaman)
            ComponentDetailAktivitas("ID Pekerja", id_pekerja)
            ComponentDetailAktivitas("Tanggal Aktivitas", tanggal_aktivitas)
            ComponentDetailAktivitas("Deskripsi Aktivitas", deskripsi_aktivitas)
        }
    }
}

@Composable
fun ComponentDetailAktivitas(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF005F2F)
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.DarkGray
        )
        Divider(color = Color(0xFF005F2F), thickness = 1.dp, modifier = Modifier.padding(top = 4.dp))
    }
}
