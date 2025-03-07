package com.munir.githubissuetracker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun RepoSelectionScreen(onNavigateToIssueList: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Select Repository", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        var searchQuery by remember { mutableStateOf("") }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search by keyword or tag") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(5) { index ->
                RepoItem(name = "Repo $index", onClick = { onNavigateToIssueList() })
            }
        }
    }
}

@Composable
fun RepoItem(name: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.elevatedCardElevation(4.dp) // Material 3
    ) {
        Text(text = name, modifier = Modifier.padding(16.dp), fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun RepoSelectionScreenPreview() {
    RepoSelectionScreen(onNavigateToIssueList = {}) // Empty lambda for preview
}
