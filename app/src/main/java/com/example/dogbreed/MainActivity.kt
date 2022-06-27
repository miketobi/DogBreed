package com.example.dogbreed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dogbreed.features.all_breeds.AllBreedScreen
import com.example.dogbreed.features.breed_details.BreedDetailsScreen
import com.example.dogbreed.ui.theme.DogBreedTheme
import com.example.dogbreed.util.NavArgs
import com.example.dogbreed.util.Route
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogBreedTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Route.all_breeds_route) {
                    composable(Route.all_breeds_route) {
                        AllBreedScreen(navController = navController)
                    }

                    composable(
                        "${Route.breed_details_route}/{${NavArgs.breed_name}}/{${NavArgs.sub_breed_name}}",
                        arguments = listOf(navArgument(NavArgs.sub_breed_name) {
                            nullable = true
                        })
                    ) {
                        BreedDetailsScreen(navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DogBreedTheme {
        Greeting("Android")
    }
}