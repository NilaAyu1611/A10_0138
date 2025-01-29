package com.example.uaspam.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uaspam.R
import com.example.uaspam.navigation.DestinasiNavigasi
import com.example.uaspam.ui.home.view.aktivitaspertanian.DestinasiHomeAktivitasPertanian
import com.example.uaspam.ui.home.view.panen.DestinasiHomePanen
import com.example.uaspam.ui.home.view.pekerja.DestinasiHomePekerja
import com.example.uaspam.ui.home.view.tanaman.DestinasiHomeTanaman


object DestinasiHalamanUtama : DestinasiNavigasi {
    override val route = "homes"
    override val titleRes = "Homes"
}

@Composable
fun PertanianApp(
    navController: NavController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) } // State for Bottom Navigation

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFFc8e6c9)
            ) {
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home"
                        )
                    },
                    label = { Text("Home") },
                    selected = selectedTab == 0,
                    onClick = {
                        selectedTab = 0
                        navController.navigate(DestinasiHalamanUtama.route)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.Black,
                        indicatorColor = Color(0xFF407002)
                    )
                )


                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "Tanaman"
                        )
                    },
                    label = { Text("Tanaman") },
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                        navController.navigate(DestinasiHomeTanaman.route)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        indicatorColor = Color(0xFF81c784)
                    )
                )

                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Pekerja"
                        )
                    },
                    label = { Text("Pekerja") },
                    selected = selectedTab == 2,
                    onClick = {
                        selectedTab = 2
                        navController.navigate(DestinasiHomePekerja.route)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        indicatorColor = Color(0xFF81c784)
                    )
                )



                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = "Panen"
                        )
                    },
                    label = { Text("Panen") },
                    selected = selectedTab == 3,
                    onClick = {
                        selectedTab = 3
                        navController.navigate(DestinasiHomePanen.route)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        indicatorColor = Color(0xFF81c784)
                    )
                )

                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = "Aktivitas"
                        )
                    },
                    label = { Text("Aktivitas") },
                    selected = selectedTab == 4,
                    onClick = {
                        selectedTab = 4
                        navController.navigate(DestinasiHomeAktivitasPertanian.route)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        indicatorColor = Color(0xFF81c784)
                    )
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF0D2F01),
                            Color(0xFF728304),
                            Color(0xFF062501)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.farm),
                    contentDescription = "Farm Image",
                    modifier = Modifier
                        .size(300.dp) // Ukuran gambar sedikit lebih kecil
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape) // Menambahkan border putih di sekitar gambar
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "🌾Welcome to NACW FARM🌿",
                    style = TextStyle(
                        color = Color(0xFFffffff),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                )
            }
        }
    }
}