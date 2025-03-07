package com.munir.githubissuetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.munir.githubissuetracker.ui.theme.GitHubIssueTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubIssueTrackerTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(navController, Modifier.padding(innerPadding))
                }
            }
        }
    }
}
@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = "repo_selection", modifier = modifier) {

        composable("repo_selection") {
            RepoSelectionScreen {
                navController.navigate("issue_list")
            }
        }

        composable("issue_list") {
            IssueListScreen {
                navController.navigate("issue_details")
            }
        }

        composable("issue_details") {
            //IssueDetailsScreen()
        }
    }
}
