package com.example.uaspam.ui.home.view.pekerja

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.uaspam.ui.home.viewmodel.pekerja.DetailPekerjaViewModel
import com.example.uaspam.ui.home.viewmodel.pekerja.DetailpeUiState


object DestinasiDetailPekerja : DestinasiNavigasi {
    override val route = "item_detail"
    override val titleRes = "Detail Pekerja"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPekerjaView(
    id_pekerja: String,
    navigateBack: () -> Unit,
    navigateEditPekerja: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailPekerjaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(id_pekerja) {
        viewModel.getPekerjaById(id_pekerja)
    }

    val detailpeUiState: DetailpeUiState by viewModel.detailpeUiState.collectAsState(initial = DetailpeUiState.Loading)

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailPekerja.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            if (detailpeUiState is DetailpeUiState.Success) {
                FloatingActionButton(
                    onClick = { navigateEditPekerja((detailpeUiState as DetailpeUiState.Success).pekerja.id_pekerja) },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Pekerja",
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when (val state = detailpeUiState) {
                is DetailpeUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is DetailpeUiState.Success -> {
                    val pekerja = state.pekerja
                    DetailPekerjaContent(pekerja.nama_pekerja, pekerja.id_pekerja, pekerja.jabatan, pekerja.kontak_pekerja)
                }
                is DetailpeUiState.Error -> {
                    Text("Error: Gagal memuat data pekerja")
                }
            }
        }
    }
}

@Composable
fun DetailPekerjaContent(
    nama_pekerja: String,
    id_pekerja: String,
    jabatan: String,
    kontak_pekerja: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF6a5b7d),
                        Color(0xFFcfa0e6)
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(8.dp) // Menambahkan bayangan
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            ComponentDetailPekerja("Nama Pekerja", nama_pekerja)
            Spacer(modifier = Modifier.height(12.dp))
            ComponentDetailPekerja("ID Pekerja", id_pekerja)
            Spacer(modifier = Modifier.height(12.dp))
            ComponentDetailPekerja("Jabatan", jabatan)
            Spacer(modifier = Modifier.height(12.dp))
            ComponentDetailPekerja("Kontak Pekerja", kontak_pekerja)
        }
    }
}

@Composable
fun ComponentDetailPekerja(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "$label:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFf5f5f5) // Warna teks untuk label lebih cerah
        )
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFFf5f5f5) // Warna teks untuk nilai lebih cerah
        )
    }
}

