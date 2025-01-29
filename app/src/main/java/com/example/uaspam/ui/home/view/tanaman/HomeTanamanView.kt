package com.example.uaspam.ui.home.view.tanaman

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.R
import com.example.uaspam.model.Tanaman
import com.example.uaspam.navigation.DestinasiNavigasi
import com.example.uaspam.ui.customwidget.CustomTopAppBar
import com.example.uaspam.ui.home.PenyediaViewModel
import com.example.uaspam.ui.home.viewmodel.tanaman.HomeTanamanViewModel
import com.example.uaspam.ui.home.viewmodel.tanaman.HomeUiState

object DestinasiHomeTanaman : DestinasiNavigasi {
    override val route = "home tanaman"
    override val titleRes = "Daftar Tanaman"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTanamanView(
    navigateBack: () -> Unit,
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeTanamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier,
        topBar = {
            CustomTopAppBar(
                title = DestinasiHomeTanaman.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getTnm()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = "Add Tanaman")
            }
        },
        containerColor = Color(0xFFf4f4f4) // Latar belakang terang yang elegan
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF062501), Color(0xFF848112), Color(0xFF022F1F))
                    )
                ) // Apply background gradient
        ) {
            HomeStatus(
                homeUiState = viewModel.tnmUiState,
                retryAction = { viewModel.getTnm() },
                modifier = Modifier.fillMaxSize(),
                onDetailClick = onDetailClick,
                onDeleteClick = {
                    viewModel.deleteTnm(it.id_tanaman)
                    viewModel.getTnm()
                }
            )
        }
    }
}


@Composable
fun HomeStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tanaman) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiState.Success -> {
            if (homeUiState.tanaman.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tidak ada data Tanaman",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            } else {
                TnmLayout(
                    tanaman = homeUiState.tanaman,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_tanaman) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.connecerror),
            contentDescription = "Connection Error",
            modifier = Modifier.size(150.dp)
        )
        Text(
            text = "Gagal memuat data, silakan coba lagi",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium
        )
        Button(
            onClick = retryAction,
            modifier = Modifier.background(MaterialTheme.colorScheme.primary),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text("Coba Lagi")
        }

    }
}

@Composable
fun TnmLayout(
    tanaman: List<Tanaman>,
    modifier: Modifier = Modifier,
    onDetailClick: (Tanaman) -> Unit,
    onDeleteClick: (Tanaman) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tanaman) { tnm ->
            TnmCard(
                tanaman = tnm,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(tnm) },
                onDeleteClick = { onDeleteClick(tnm) }
            )
        }
    }
}

@Composable
fun TnmCard(
    tanaman: Tanaman,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tanaman) -> Unit = {}
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = Color(0x80587156), // Warna latar belakang semi transparan
        tonalElevation = 8.dp // Elevasi untuk surface
    ) {
        Column(
            modifier = Modifier.padding(12.dp), // Mengurangi padding supaya lebih rapat
            verticalArrangement = Arrangement.spacedBy(3.dp) // Mengurangi jarak antar baris
        ) {
            // Nama Tanaman tanpa Ikon
            Text(
                text = tanaman.nama_tanaman,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontFamily = FontFamily.Serif, // Ganti dengan font Times New Roman
                    fontSize = 20.sp // Ukuran font yang lebih proporsional
                ),
                color = Color.White // Ganti warna teks menjadi putih agar kontras
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ikon ID Tanaman
                Icon(
                    imageVector = Icons.Default.Info, // Ikon untuk ID Tanaman
                    contentDescription = "ID Tanaman",
                    modifier = Modifier.size(20.dp), // Ukuran ikon lebih proporsional
                    tint = MaterialTheme.colorScheme.onSurface // Gunakan warna gelap untuk ikon
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = tanaman.id_tanaman,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily.Serif, // Ganti dengan font Times New Roman
                        fontSize = 16.sp // Ukuran font lebih pas
                    ),
                    color = Color.White // Ganti warna teks menjadi putih agar kontras
                )
                Spacer(Modifier.weight(1f)) // Spacer untuk memberi ruang antara ID dan tombol delete
                IconButton(onClick = { onDeleteClick(tanaman) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Tanaman",
                        tint = MaterialTheme.colorScheme.error // Ikon delete tetap merah
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ikon Periode Tanam
                Icon(
                    imageVector = Icons.Default.DateRange, // Ikon untuk Periode Tanam
                    contentDescription = "Periode Tanam",
                    modifier = Modifier.size(20.dp), // Ukuran ikon lebih proporsional
                    tint = MaterialTheme.colorScheme.onSurface // Gunakan warna gelap untuk ikon
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = tanaman.periode_tanam,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily.Serif, // Ganti dengan font Times New Roman
                        fontSize = 16.sp // Ukuran font lebih pas
                    ),
                    color = Color.White // Ganti warna teks menjadi putih agar kontras
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ikon Deskripsi Tanaman
                Icon(
                    imageVector = Icons.Default.ArrowForward, // Ikon untuk Deskripsi Tanaman
                    contentDescription = "Deskripsi Tanaman",
                    modifier = Modifier.size(20.dp), // Ukuran ikon lebih proporsional
                    tint = MaterialTheme.colorScheme.onSurface // Gunakan warna gelap untuk ikon
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = tanaman.deskripsi_tanaman,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily.Serif, // Ganti dengan font Times New Roman
                        fontSize = 16.sp // Ukuran font lebih pas
                    ),
                    color = Color.White // Ganti warna teks menjadi putih agar kontras
                )
            }
        }
    }
}
