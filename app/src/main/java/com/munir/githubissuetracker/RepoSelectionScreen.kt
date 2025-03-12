package com.munir.githubissuetracker


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RepoSelectionScreen(
    onNavigateToIssueList: (String, String) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var repo by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var recentSearches by remember { mutableStateOf(listOf("android-compose", "jetpack-navigation", "ktor-server")) }
    var userAvatarUrl by remember { mutableStateOf("https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png") }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "GitHub Issue Tracker",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Enter GitHub username and repository name to fetch issues.",
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // GitHub Avatar Display
        Image(
            painter = rememberAsyncImagePainter(userAvatarUrl),
            contentDescription = "GitHub Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("GitHub Username") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
           // leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = repo,
            onValueChange = { repo = it },
            label = { Text("Repository Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }),
           // leadingIcon = { Icon(Icons.Default.Star, contentDescription = "Repository Icon") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (showError) {
            Text(
                text = "Both fields are required!",
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Gradient Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF00C853), Color(0xFFB2FF59)) // Green Gradient
                    )
                )
                .clickable {
                    if (username.isBlank() || repo.isBlank()) {
                        showError = true
                    } else {
                        showError = false
                        onNavigateToIssueList(username, repo)
                    }
                },
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text(
                text = "Fetch Issues",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Recent Searches
        if (recentSearches.isNotEmpty()) {
            Text(
                text = "Recent Searches",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))

            recentSearches.forEach { repoName ->
                RecentSearchItem(repoName) {
                    username = "example-user"
                    repo = repoName
                }
            }
        }
    }
}

@Composable
fun RecentSearchItem(repoName: String, onSelect: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onSelect() },
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Text(
            text = repoName,
            modifier = Modifier.padding(16.dp),
            fontSize = 15.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RepoSelectionScreenPreview() {
    RepoSelectionScreen { _, _ -> }
}
