package com.example.uaspam.ui.home.view.panen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.R
import com.example.uaspam.model.Panen
import com.example.uaspam.navigation.DestinasiNavigasi
import com.example.uaspam.ui.customwidget.CustomTopAppBar
import com.example.uaspam.ui.home.PenyediaViewModel
import com.example.uaspam.ui.home.viewmodel.panen.HomePanenViewModel
import com.example.uaspam.ui.home.viewmodel.panen.HomepaUiState


object DestinasiHomePanen: DestinasiNavigasi {
    override val route= "home panen"
    override val titleRes= "Catatan Panen"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePanenView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onEditClick: (String) -> Unit = {},
    viewModel: HomePanenViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiHomePanen.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getPn()
                }
            )
        },
    ) {innerPadding ->
        HomeStatusPanen(
            homepaUiState = viewModel.pnUiState,
            retryAction = {viewModel.getPn()},
            modifier = Modifier
                .padding(innerPadding)
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF777109), Color(0xFF112C02), Color(0xFF495C02))
                    )
                )
                .fillMaxSize(),
            onEditClick = onEditClick, onDeleteClick = {
                viewModel.deletePn(it.id_panen)
                viewModel.getPn()
            }
        )
    }
}

@Composable
fun HomeStatusPanen(
    homepaUiState: HomepaUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Panen) -> Unit = {},
    onEditClick: (String) -> Unit
){
    when (homepaUiState){
        is HomepaUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomepaUiState.Success ->
            if(homepaUiState.panen.isEmpty()){
                return Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data Panen")
                }
            }else  {
                PnLayout(
                    panen = homepaUiState.panen,
                    modifier = modifier.fillMaxWidth(),
                    onEditClick = {
                        onEditClick(it.id_panen)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is HomepaUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

//tampilan home, menampilkan pean loading

@Composable
fun OnLoading(modifier: Modifier = Modifier){
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

// jika terjadi kesalahan maka bisa ulang kemnali

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
fun PnLayout(
    panen: List<Panen>,
    modifier: Modifier = Modifier,
    onEditClick: (Panen) -> Unit,
    onDeleteClick: (Panen) -> Unit
){
    LazyColumn (
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){

        items(panen){pn ->
            PnCard(
                panen= pn ,
                modifier = Modifier
                    .fillMaxWidth(),
                onEditClick = {
                    onEditClick(pn)
                },
                onDeleteClick = {
                    onDeleteClick(pn)
                }
            )
        }
    }
}


@Composable
fun PnCard(
    panen: Panen,
    modifier: Modifier = Modifier,
    onDeleteClick: (Panen) -> Unit = {},
    onEditClick: (Panen) -> Unit = {}
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = Color(0x80587156), // Warna latar belakang semi transparan
        tonalElevation = 8.dp // Elevasi untuk surface
    ) {
        Column(
            modifier = Modifier.padding(12.dp), // Padding yang lebih rapat
            verticalArrangement = Arrangement.spacedBy(3.dp) // Jarak antar elemen lebih kecil
        ) {
            // ID Panen
            Text(
                text = panen.id_panen,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontFamily = FontFamily.Serif, // Font Times New Roman
                    fontSize = 20.sp // Ukuran font proporsional
                ),
                color = Color.White // Warna teks putih
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "ID Tanaman",
                    modifier = Modifier.size(20.dp), // Ukuran ikon proporsional
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = panen.id_tanaman,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily.Serif, // Font Times New Roman
                        fontSize = 16.sp
                    ),
                    color = Color.White
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Tanggal Panen",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = panen.tanggal_panen,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontSize = 16.sp
                    ),
                    color = Color.White
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Jumlah Panen",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = panen.jumlah_panen,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontSize = 16.sp
                    ),
                    color = Color.White
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Keterangan",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = panen.keterangan,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontSize = 16.sp
                    ),
                    color = Color.White
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onEditClick(panen) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Panen",
                        tint = Color.Yellow
                    )
                }
                IconButton(onClick = { onDeleteClick(panen) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Panen",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
