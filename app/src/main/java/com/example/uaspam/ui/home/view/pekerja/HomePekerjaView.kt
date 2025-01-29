package com.example.uaspam.ui.home.view.pekerja

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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ThumbUp
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
import com.example.uaspam.model.Pekerja
import com.example.uaspam.navigation.DestinasiNavigasi
import com.example.uaspam.ui.customwidget.CustomTopAppBar
import com.example.uaspam.ui.home.PenyediaViewModel
import com.example.uaspam.ui.home.viewmodel.pekerja.HomePekerjaViewModel
import com.example.uaspam.ui.home.viewmodel.pekerja.HomepeUiState


object DestinasiHomePekerja: DestinasiNavigasi {
    override val route= "home pekerja"
    override val titleRes= "Daftar Pekerja"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePekerjaView(
    navigateBack: () -> Unit,
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomePekerjaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier,
        topBar = {
            CustomTopAppBar(
                title = DestinasiHomePekerja.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getPkj()
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
                    contentDescription = "Add Pekerja")
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
            HomeStatusPekerja(
                homepeUiState = viewModel.pkjUiState,
                retryAction = { viewModel.getPkj() },
                modifier = Modifier.fillMaxSize(),
                onDetailClick = onDetailClick,
                onDeleteClick = {
                    viewModel.deletePkj(it.id_pekerja)
                    viewModel.getPkj()
                }
            )
        }
    }
}

@Composable
fun HomeStatusPekerja(
    homepeUiState: HomepeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pekerja) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homepeUiState) {
        is HomepeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomepeUiState.Success -> {
            if (homepeUiState.pekerja.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tidak ada data Pekerja",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            } else {
                PkjLayout(
                    pekerja = homepeUiState.pekerja,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_pekerja) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is HomepeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
fun PkjLayout(
    pekerja: List<Pekerja>,
    modifier: Modifier = Modifier,
    onDetailClick: (Pekerja) -> Unit,
    onDeleteClick: (Pekerja) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pekerja) { pkj ->
            PkjCard(
                pekerja = pkj,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(pkj) },
                onDeleteClick = { onDeleteClick(pkj) }
            )
        }
    }
}

@Composable
fun PkjCard(
    pekerja: Pekerja,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pekerja) -> Unit = {}
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
            // Nama Pekerja tanpa Ikon
            Text(
                text = pekerja.nama_pekerja,
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
                // Ikon ID Pekerja
                Icon(
                    imageVector = Icons.Default.Info, // Ikon untuk ID Pekerja
                    contentDescription = "ID Pekerja",
                    modifier = Modifier.size(20.dp), // Ukuran ikon lebih proporsional
                    tint = MaterialTheme.colorScheme.onSurface // Gunakan warna gelap untuk ikon
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = pekerja.id_pekerja,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily.Serif, // Ganti dengan font Times New Roman
                        fontSize = 16.sp // Ukuran font lebih pas
                    ),
                    color = Color.White // Ganti warna teks menjadi putih agar kontras
                )
                Spacer(Modifier.weight(1f)) // Spacer untuk memberi ruang antara ID dan tombol delete
                IconButton(onClick = { onDeleteClick(pekerja) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Pekerja",
                        tint = MaterialTheme.colorScheme.error // Ikon delete tetap merah
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ikon Jabatan
                Icon(
                    imageVector = Icons.Default.ThumbUp, // Ikon untuk Jabatan
                    contentDescription = "Jabatan",
                    modifier = Modifier.size(20.dp), // Ukuran ikon lebih proporsional
                    tint = MaterialTheme.colorScheme.onSurface // Gunakan warna gelap untuk ikon
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = pekerja.jabatan,
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
                // Ikon Kontak
                Icon(
                    imageVector = Icons.Default.Phone, // Ikon untuk Kontak Pekerja
                    contentDescription = "Kontak Pekerja",
                    modifier = Modifier.size(20.dp), // Ukuran ikon lebih proporsional
                    tint = MaterialTheme.colorScheme.onSurface // Gunakan warna gelap untuk ikon
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = pekerja.kontak_pekerja,
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
