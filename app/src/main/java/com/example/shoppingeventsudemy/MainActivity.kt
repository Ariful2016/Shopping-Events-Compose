package com.example.shoppingeventsudemy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoppingeventsudemy.destinations.AddEventRoute
import com.example.shoppingeventsudemy.destinations.EventDetailsRoute
import com.example.shoppingeventsudemy.destinations.HomeRoute
import com.example.shoppingeventsudemy.ui.addevent.AddEventPage
import com.example.shoppingeventsudemy.ui.eventdetails.EventDetailsPage
import com.example.shoppingeventsudemy.ui.home.HomePage
import com.example.shoppingeventsudemy.ui.theme.ShoppingEventsUdemyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingApp()
        }
    }
}

@Composable
fun ShoppingApp(modifier: Modifier = Modifier) {
    ShoppingEventsUdemyTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = HomeRoute
        ) {
            composable<HomeRoute> {
                HomePage(
                    navigateToAddEvent = {
                        navController.navigate(route = AddEventRoute)
                    },
                    navigateToEventDetails = {id, name ->
                        navController.navigate(route = EventDetailsRoute(id, name))
                    },
                    modifier = modifier
                )
            }

            composable<AddEventRoute> {
                AddEventPage(
                    navigateBack = { navController.popBackStack() },
                    navigateUp = { navController.navigateUp() },
                    modifier = modifier
                )
            }

            composable<EventDetailsRoute> {
                EventDetailsPage(
                    navigateBack = { navController.popBackStack() },
                    navigateUp = { navController.navigateUp() },
                    modifier = modifier
                )
            }
        }
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
    ShoppingEventsUdemyTheme {
        Greeting("Android")
    }
}