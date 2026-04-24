package com.moviles.unaroom.ui.screens.classrooms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moviles.unaroom.data.Classroom
import com.moviles.unaroom.ui.components.ClassroomCard
import com.moviles.unaroom.ui.theme.AppBackground
import com.moviles.unaroom.ui.theme.AppNavUnselected
import com.moviles.unaroom.ui.theme.AppPrimary
import com.moviles.unaroom.ui.theme.AppSecondaryText

// Bottom bar privado
@Composable
private fun AppBottomBar() {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        NavigationBarItem(selected = true,  onClick = {}, icon = { Icon(Icons.Filled.Home, "Home") },
            label = { Text("Home") }, colors = navigationBarItemColors())
        NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Outlined.CalendarToday, "Calendar") },
            label = { Text("Calendar") }, colors = navigationBarItemColors())
        NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Outlined.Person, "Profile") },
            label = { Text("Profile") }, colors = navigationBarItemColors())
    }
}

@Composable
private fun navigationBarItemColors() = NavigationBarItemDefaults.colors(
    selectedIconColor   = AppPrimary,
    selectedTextColor   = AppPrimary,
    unselectedIconColor = AppNavUnselected,
    unselectedTextColor = AppNavUnselected,
    indicatorColor      = AppBackground
)

// Pantalla principal
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassroomsScreen(
    modifier: Modifier = Modifier,
    classrooms: List<Classroom> = mockClassrooms,
    successMessage: String? = null,
    onSuccessMessageShown: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(successMessage) {
        if (successMessage != null) {
            snackbarHostState.showSnackbar(successMessage)
            onSuccessMessageShown()
        }
    }

    Scaffold(
        modifier       = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title   = { Text("Available Classrooms", style = MaterialTheme.typography.headlineMedium) },
                actions = { IconButton(onClick = onLogoutClick) {
                    Icon(
                        Icons.AutoMirrored.Outlined.Logout, "Logout", tint = AppPrimary)
                }},
                colors  = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}, containerColor = AppPrimary, contentColor = AppBackground) {
                Icon(Icons.Filled.Add, "Add classroom")
            }
        },
        bottomBar   = { AppBottomBar() },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    snackbarData = data, containerColor = AppPrimary, contentColor = AppBackground)
            }
        }
    ) { padding ->
        if (classrooms.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) {
                Text("No classrooms available", color = AppSecondaryText,
                    style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier        = Modifier.fillMaxSize().padding(padding),
                contentPadding  = PaddingValues(start = 14.dp, end = 14.dp, top = 12.dp, bottom = 96.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(classrooms) { classroom -> ClassroomCard(classroom) }
            }
        }
    }
}

private val mockClassrooms = listOf(
    Classroom("Aula A101",        capacity = 30,  location = "Building 1"),
    Classroom("Aula B205",        capacity = 25,  location = "Building 2"),
    Classroom("Lecture Hall 101", capacity = 150, location = "Building 3"),
    Classroom("Aula C310",        capacity = 24,  location = "Building 1"),
    Classroom("Meeting Room 201", capacity = 12,  location = "Building 2")
)

