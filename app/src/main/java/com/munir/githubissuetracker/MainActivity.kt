package com.munir.githubissuetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.munir.githubissuetracker.ui.theme.GitHubIssueTrackerTheme
import com.apollographql.apollo3.ApolloClient


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
    val githubToken = System.getenv("GITHUB_TOKEN") ?: ""
    val apolloClient = remember {
        ApolloClient.Builder()
            .serverUrl("https://api.github.com/graphql")
            .addHttpHeader("Authorization", "Bearer $githubToken")
            .build()
    }

    val viewModel = remember { RepoViewModel(apolloClient) }



        NavHost(navController = navController, startDestination = "repo_selection", modifier = modifier) {
            composable("repo_selection") {
                RepoSelectionScreen(viewModel, onNavigateToIssueList = { username, repo ->
                    navController.navigate("issue_list/$username/$repo")
                })
            }


        composable("issue_list/{username}/{repo}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            val repo = backStackEntry.arguments?.getString("repo") ?: ""

            IssueListScreen(username, repo, viewModel) {
                navController.navigate("issue_details")
            }
        }

        composable("issue_details") {
            // IssueDetailsScreen()
        }
    }
}
