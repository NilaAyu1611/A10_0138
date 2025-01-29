package com.example.uaspam.ui.home.view.pekerja

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

    val detailpeUiState by viewModel.detailpeUiState.collectAsState(initial = DetailpeUiState.Loading)

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
                    shape = RoundedCornerShape(50),
                    containerColor = Color(0xFF005F2F),
                    contentColor = Color.White,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Pekerja"
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
            when (val state = detailpeUiState) {
                is DetailpeUiState.Loading -> {
                    CircularProgressIndicator(color = Color.White)
                }
                is DetailpeUiState.Success -> {
                    DetailPekerjaContent(
                        state.pekerja.nama_pekerja,
                        state.pekerja.id_pekerja,
                        state.pekerja.jabatan,
                        state.pekerja.kontak_pekerja
                    )
                }
                is DetailpeUiState.Error -> {
                    Text("Error: Gagal memuat data pekerja", color = Color.Red)
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
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            ComponentDetailPekerja("Nama Pekerja", nama_pekerja)
            ComponentDetailPekerja("ID Pekerja", id_pekerja)
            ComponentDetailPekerja("Jabatan", jabatan)
            ComponentDetailPekerja("Kontak Pekerja", kontak_pekerja)
        }
    }
}

@Composable
fun ComponentDetailPekerja(label: String, value: String, modifier: Modifier = Modifier) {
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
