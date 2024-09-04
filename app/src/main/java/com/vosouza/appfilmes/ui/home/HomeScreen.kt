package com.vosouza.appfilmes.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vosouza.appfilmes.R
import com.vosouza.appfilmes.ui.theme.black
import com.vosouza.appfilmes.ui.theme.orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(){
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("BRQ Movies", color = Color.White, fontSize = 28.sp) },
            actions = {
                var menuExpanded by remember {
                    mutableStateOf(false)
                }

                IconButton(onClick = { menuExpanded = !menuExpanded }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.menu_icon),
                        contentDescription = "Menu",
                        tint = Color.Unspecified
                    )
                }

                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false },
                ) {
                    DropdownMenuItem(
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier.padding(8.dp),
                                    imageVector = ImageVector.vectorResource(R.drawable.exit_icon),
                                    contentDescription = "Exit"
                                )
                                Text("Sair", color = Color.White, fontSize = 24.sp)
                            }
                        },
                        onClick = {

                        },
                    )
                }
            },
        )
    }, content = { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(black)
        ) {
            TabRow(selectedTabIndex = 0) {
                Tab(selected = true, onClick = { /* TODO: Todos os Filmes */ }) {
                    Text(
                        "Todos os Filmes",
                        modifier = Modifier.padding(16.dp),
                        color = orange,
                        fontWeight = FontWeight.Bold
                    )
                }
                Tab(selected = false, onClick = { /* TODO: Outros tabs */ }) {
                    Text(
                        "Outra Categoria",
                        modifier = Modifier.padding(16.dp),
                        color = Color.White
                    )
                }
            }


        }
    })
}