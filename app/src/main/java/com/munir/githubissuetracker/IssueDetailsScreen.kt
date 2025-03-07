package com.munir.githubissuetracker

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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun IssueDetailsScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Issue Details",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.elevatedCardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Title: Bug in App", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "Status: Open",
                    color = Color(0xFF388E3C), // Material 3 green
                    fontWeight = FontWeight.Medium
                )
                Text(text = "Assignee: @Munira12345", fontWeight = FontWeight.Medium)
                Text(
                    text = "Description: The app crashes when opening on Android 12.",
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Comments", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        LazyColumn {
            items(3) { index ->
                CommentItem(user = "User$index", comment = "This issue still happens! Please fix it.")
            }
        }
    }
}

@Composable
fun CommentItem(user: String, comment: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = user, fontWeight = FontWeight.Bold)
            Text(text = comment, fontSize = 14.sp, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IssueDetailsScreenPreview() {
    val navController = rememberNavController()
    IssueDetailsScreen(navController)
}
