package pro.inc.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import pro.inc.shoppinglist.ui.theme.ShoppingListApp
import pro.inc.shoppinglist.ui.theme.ShoppingListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingListTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }

}

@Composable
fun Navigation(){
    val navController = rememberNavController()
    val viewModel: LocationViewModel = viewModel()
    val context = LocalContext.current //Basically what it says is give me the context of the current activity that I'm in.
    val locationUtils = LocationUtils(context)


    NavHost(navController, startDestination ="shoppinglistscreen" ) {
        composable("shoppinglistscreen"){
            ShoppingListApp(
                locationUtils = locationUtils,
                viewModel = viewModel,
                navController = navController,
                context = context,

                address = viewModel.address.value.firstOrNull()?.formatted_address?:"No bal"
            )
        }

        dialog("locationscreen"){backstack ->

            viewModel.location.value?.let {it1 ->

                LocationSelectionScreen(location = it1 , onLocationSelected = {
                    viewModel.fetchAddress("${it.latitude},${it.longitude}")
                    navController.popBackStack()  //So that will basically.

//                    Attempt to pop the controllers back stack, analogous to when the user presses the systems back button
//
//                    when the associated navigation host has focus.
                })



            }

        }

    }
}







