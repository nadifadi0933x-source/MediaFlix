package com.mediaflix.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mediaflix.app.ui.components.MediaCard
import com.mediaflix.app.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController, viewModel: SearchViewModel = hiltViewModel()) {
    val query by viewModel.query.collectAsState()
    val results by viewModel.results.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.onQueryChange(it) },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            placeholder = { Text("جستجوی فیلم، سریال، انیمه، مانگا...") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "جستجو") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary, unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant)
        )

        if (isSearching) Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        else if (query.length < 2) Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("برای جستجو حداقل ۲ کاراکتر وارد کنید", color = MaterialTheme.colorScheme.onSurfaceVariant) }
        else if (results.isEmpty()) Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("نتیجه‌ای یافت نشد 😕", color = MaterialTheme.colorScheme.onSurfaceVariant) }
        else {
            LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                item { Text("${results.size} نتیجه برای \"$query\"", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 8.dp)) }
                items(results.chunked(3)) { row ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        row.forEach { item -> MediaCard(item = item, onClick = { navController.navigate("detail/${item.id}") }, modifier = Modifier.weight(1f)) }
                        repeat(3 - row.size) { Spacer(Modifier.weight(1f)) }
                    }
                }
            }
        }
    }
}