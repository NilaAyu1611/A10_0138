package com.example.uaspam.ui.home.view.tanaman

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
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
import com.example.uaspam.ui.home.viewmodel.tanaman.DetailTanamanViewModel
import com.example.uaspam.ui.home.viewmodel.tanaman.DetailUiState


object DestinasiDetail : DestinasiNavigasi {
    override val route = "item_detail"
    override val titleRes = "Detail Tanaman"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTanamanView(
    id_tanaman: String,
    navigateBack: () -> Unit,
    navigateEdit: (String) -> Unit,
    navigateToInsertPanen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailTanamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(id_tanaman) {
        viewModel.getTanamanById(id_tanaman)
    }

    val detailUiState: DetailUiState by viewModel.detailUiState.collectAsState(initial = DetailUiState.Loading)

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            if (detailUiState is DetailUiState.Success) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Tombol Edit Tanaman
                    FloatingActionButton(
                        onClick = { navigateEdit((detailUiState as DetailUiState.Success).tanaman.id_tanaman) },
                        shape = MaterialTheme.shapes.medium,
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Tanaman",
                            tint = Color.White
                        )
                    }

                    // Tombol Tambah Panen
                    FloatingActionButton(
                        onClick = { navigateToInsertPanen() },
                        shape = MaterialTheme.shapes.medium,
                        containerColor = MaterialTheme.colorScheme.secondary
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Tambah Panen",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF062501), Color(0xFF848112), Color(0xFF022F1F))
                    )
                )
        ) {
            when (val state = detailUiState) {
                is DetailUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is DetailUiState.Success -> {
                    val tanaman = state.tanaman
                    DetailTanamanContent(
                        tanaman.nama_tanaman,
                        tanaman.id_tanaman,
                        tanaman.periode_tanam,
                        tanaman.deskripsi_tanaman
                    )
                }
                is DetailUiState.Error -> {
                    Text("Error: Gagal memuat data tanaman", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Composable
fun DetailTanamanContent(
    nama_tanaman: String,
    id_tanaman: String,
    periode_tanam: String,
    deskripsi_tanaman: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF586F4B),  // Dark Green
                        Color(0xFFA0B694)   // Light Green
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),  // Membuat Card transparan
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailTanaman("Nama Tanaman", nama_tanaman)
            Spacer(modifier = Modifier.height(12.dp))
            ComponentDetailTanaman("ID Tanaman", id_tanaman)
            Spacer(modifier = Modifier.height(12.dp))
            ComponentDetailTanaman("Periode Tanam", periode_tanam)
            Spacer(modifier = Modifier.height(12.dp))
            ComponentDetailTanaman("Deskripsi Tanaman", deskripsi_tanaman)
        }
    }
}

@Composable
fun ComponentDetailTanaman(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
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
