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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.graphics.Color

@Composable
fun IssueListScreen(
    username: String,
    repo: String,
    viewModel: RepoViewModel,
    onNavigate: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(username, repo) {
        viewModel.fetchIssues(username, repo)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Issues for $username/$repo",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        when (uiState) {
            is RepoUiState.Loading -> CircularProgressIndicator()
            is RepoUiState.Success -> {
                val issues = (uiState as RepoUiState.Success).issues
                LazyColumn {
                    items(issues) { issue ->
                        IssueItem(
                            title = issue.title,
                            tag = "General", // Adjust if you have tags
                            status = "Open", // Modify if status is available
                            description = issue.body,
                            onClick = onNavigate
                        )
                    }
                }
            }
            is RepoUiState.Error -> Text(
                text = (uiState as RepoUiState.Error).message,
                color = Color.Red
            )
            else -> {}
        }
    }
}

@Composable
fun IssueItem(title: String, tag: String, status: String, description: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.elevatedCardElevation(4.dp), // Material 3 elevation
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = "Tag: $tag", color = Color.Gray, fontSize = 14.sp)
            Text(
                text = "Status: $status",
                color = if (status == "Open") Color(0xFF388E3C) else Color(0xFFD32F2F), // Material 3 Green & Red
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = description,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp
            )
        }
    }
}



// Preview for Individual IssueItem
@Preview(showBackground = true)
@Composable
fun IssueItemPreview() {
    IssueItem(
        title = "Fix UI Bug",
        tag = "Bug",
        status = "Open",
        description = "App crashes when rotating the screen.",
        onClick = {}
    )
}
