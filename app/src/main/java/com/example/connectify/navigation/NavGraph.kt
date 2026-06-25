package com.example.connectify.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    object ScreenA : Screen("screen_a")
    object ScreenB : Screen("screen_b")
    object ScreenC : Screen("screen_c")
    object ScreenD : Screen("screen_d/{name}") {
        fun createRoute(name: String) = "screen_d/$name"
    }
}

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.ScreenA.route,
        enterTransition = {
            fadeIn(animationSpec = tween(500)) + slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            fadeOut(animationSpec = tween(500)) + slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(500)) + slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(500)) + slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        }
    ) {
        composable(route = Screen.ScreenA.route) {
            ScreenA(navController)
        }
        composable(route = Screen.ScreenB.route) {
            ScreenB(navController)
        }
        composable(route = Screen.ScreenC.route) {
            ScreenC(navController)
        }
        composable(
            route = Screen.ScreenD.route,
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: "User"
            ScreenD(navController, name)
        }
    }
}

@Composable
fun ScreenA(navController: NavHostController) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1A1A1A), Color.Black)
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(1000)) + expandVertically(tween(1000))
        ) {
            Text(
                text = "Connectify Home",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            )
        }
        
        Spacer(modifier = Modifier.height(48.dp))

        NavButton("Explore Screen B", MaterialTheme.colorScheme.primary) {
            navController.navigate(Screen.ScreenB.route)
        }
        NavButton("Navigate to C", MaterialTheme.colorScheme.secondary) {
            navController.navigate(Screen.ScreenC.route)
        }
        NavButton("Hello Screen (Args)", MaterialTheme.colorScheme.tertiary) {
            navController.navigate(Screen.ScreenD.createRoute("Connectify User"))
        }
    }
}

@Composable
fun ScreenB(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Feature Details",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
        ) {
            Text("Back with popBackStack()")
        }
    }
}

@Composable
fun ScreenC(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Settings & Config",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.tertiary
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                navController.navigate(Screen.ScreenA.route) {
                    popUpTo(Screen.ScreenA.route) { inclusive = true }
                }
            },
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Reset Stack to Home")
        }
    }
}

@Composable
fun ScreenD(navController: NavHostController, name: String) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(60.dp))
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = name.take(1), fontSize = 48.sp, color = MaterialTheme.colorScheme.primary)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Welcome, $name", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedButton(onClick = { navController.popBackStack() }) {
            Text("Return")
        }
    }
}

@Composable
fun NavButton(text: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text = text, fontWeight = FontWeight.SemiBold)
    }
}
