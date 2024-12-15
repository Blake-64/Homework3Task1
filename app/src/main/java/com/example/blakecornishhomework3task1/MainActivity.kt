package com.example.blakecornishhomework3task1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.DrawerValue

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.blakecornishhomework3task1.ui.theme.BlakeCornishHomework3Task1Theme

import com.example.blakecornishhomework3task1.BitCoinsViewModel
import com.example.blakecornishhomework3task1.BitCoin
import com.example.blakecornishhomework3task1.BitCoinRepository
import com.example.blakecornishhomework3task1.BitCoinRepositoryImpl
import com.example.blakecornishhomework3task1.ui.theme.BitCoinList
import kotlinx.coroutines.launch


data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)



@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlakeCornishHomework3Task1Theme {

                val items = listOf(
                    BottomNavigationItem(
                        title = "Home",
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home,
                    ),

                    BottomNavigationItem(
                        title = "Favorite Bitcoin",
                        selectedIcon = Icons.Filled.Favorite,
                        unselectedIcon = Icons.Outlined.Favorite,
                    ),

                    BottomNavigationItem(
                        title = "Detailed Bitcoin",
                        selectedIcon = Icons.Filled.Email,
                        unselectedIcon = Icons.Outlined.Email,
                    )
                )

                myNavBar(items = items)
                myNavDrawer(items = items)

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun myNavBar(items: List<BottomNavigationItem>){
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),

        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            navController.navigate(item.title)
                        }, label = {
                            Text(text = item.title)
                        }, icon = {
                            Box(
                                modifier = Modifier.size(48.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        item.selectedIcon
                                    } else item.unselectedIcon,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = "Home"
            ) {
                composable("Home") {
                    HomeScreen()
                }
                composable("Favorite Bitcoin") {
                    FavoriteBitcoinScreen()
                }
                composable("Detailed Bitcoin") {
                    DetailedBitcoinScreen()
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun myNavDrawer(items: List<BottomNavigationItem>){

    BlakeCornishHomework3Task1Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ){
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            var selectedItemIndex by rememberSaveable {
                mutableStateOf(0)
            }
            val navController = rememberNavController()
            ModalNavigationDrawer(
                drawerContent = {
                    ModalDrawerSheet{
                        items.forEachIndexed{
                            index, item ->
                            
                            NavigationDrawerItem(
                                label = {Text(text = item.title)},
                                selected = index == selectedItemIndex,
                                onClick = {
                                    navController.navigate(item.title)
                                    selectedItemIndex = index
                                    scope.launch {
                                        drawerState.close()
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = if (index == selectedItemIndex) {
                                            item.selectedIcon
                                        } else item.unselectedIcon,
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            )
                        }
                    }
                }
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("top app bar")
                            },

                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = null
                                    )
                                }
                            }
                        )
                    }, content = { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            Text(text = "Main content")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {
        Text(text = "Home Screen")
    }
    BitCoinList()
}

@Composable
fun FavoriteBitcoinScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Favorite Bitcoin Screen")
    }
}

@Composable
fun DetailedBitcoinScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Detailed Bitcoin Screen")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BlakeCornishHomework3Task1Theme {
        HomeScreen()

        val items = listOf(
            BottomNavigationItem(
                title = "Home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
            ),

            BottomNavigationItem(
                title = "Favorite Bitcoin",
                selectedIcon = Icons.Filled.Favorite,
                unselectedIcon = Icons.Outlined.Favorite,
            ),

            BottomNavigationItem(
                title = "Detailed Bitcoin",
                selectedIcon = Icons.Filled.Email,
                unselectedIcon = Icons.Outlined.Email,
            )
        )

        myNavBar(items = items)
        myNavDrawer(items = items)
    }
}
