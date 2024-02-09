package dev.pan.todoapp.feature_todo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import dev.pan.todoapp.feature_todo.presentation.todo_list.TodoListScreen
import dev.pan.todoapp.feature_todo.presentation.todo_list.TodoListViewModel
import dev.pan.todoapp.feature_todo.presentation.todo_new_update.TodoNewUpdateScreen
import dev.pan.todoapp.feature_todo.presentation.util.Screen
import dev.pan.todoapp.ui.theme.TodoAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val navController = rememberNavController()
                    val listViewModel: TodoListViewModel = hiltViewModel()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.TodoItemListScreen.route
                    ) {
                        composable(route = Screen.TodoItemListScreen.route) {
                            TodoListScreen(
                                navController = navController,
                                viewModel = listViewModel
                            )
                        }
                        composable(
                            route = Screen.TodoNewUpdateScreen.route + "?todoId={todoId}", // loads this screen and pass item that stored on this id from repo
                            arguments = listOf(
                                navArgument(
                                    name = "todoId" // name matches the one from view model to work
                                ) {
                                    type = NavType.IntType
                                    defaultValue =
                                        -1 // gets item from db, but if its new item that user created it'll get -1
                                }
                            )
                        ) {
                            TodoNewUpdateScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
