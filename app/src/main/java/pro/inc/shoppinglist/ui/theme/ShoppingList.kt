package pro.inc.shoppinglist.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class ShoppingListItem(
    var id: Int,
    var name: String,
    var quantity: Int,
    var isEditingmode: Boolean = false
)


@Composable
fun ShoppingListApp(){

    var sItems by remember { mutableStateOf(mutableListOf<ShoppingListItem>()) }
    var showDialog by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ){

        Spacer(modifier = Modifier.height(45.dp))
        
        Button(onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ){
            Text("Add Item")
        }

        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ){
            items(sItems){

            }

        }

    }


    if (showDialog){
//        AlertDialog(onDismissRequest = { showDialog= false}) {
////            Text("Hello World!")
//        }
//
//        AlertDialog(onDismissRequest = {showDialog= false }, confirmButton = { /*TODO*/ }) {
//            Text("Hello World!")
//        }
        AlertDialog(onDismissRequest = { showDialog = false}, confirmButton = {},
            title = { Text("Add Shopping Item",
                style = MaterialTheme.typography.headlineSmall
                ) },
//            containerColor = MaterialTheme.colorScheme.surface,
            text = {
                Column {
                    OutlinedTextField(value = itemName,
                        onValueChange = { itemName = it},
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        label = { Text("Name") }
                        )
                    OutlinedTextField(value = itemQuantity,
                        onValueChange = { itemQuantity = it},
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        label = { Text("Quantity") }

                    )
                }
            },
            containerColor = Color(0xFFB0BEC5).copy(alpha = 0.8f), // Ash color with transparency
//            titleContentColor = MaterialTheme.colorScheme.onSurface,
//            textContentColor = MaterialTheme.colorScheme.onSurface
            )



    }
}