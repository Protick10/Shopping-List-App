package pro.inc.shoppinglist.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

    var sItems by remember { mutableStateOf(listOf<ShoppingListItem>()) }
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

//                ShoppingListItem(it, {}, {})

                item -> // This is the lambda function that takes one argument and returns nothing
                if (item.isEditingmode){
                    ShoppingItemEditor(item = item, onEditComplete = {
                        editedName, editedQuantity -> // This is a lambda function that takes two arguments and returns nothing
                        sItems = sItems.map { it.copy(isEditingmode = false) } // Set all items to not be in editing mode
                        val editedItem = sItems.find { it.id == item.id } // Find the item with the id of the item being edited
                        editedItem?.let {
                            it.name = editedName
                            it.quantity = editedQuantity
                        }
                    })


                }

                else{
                    ShoppingListItem(item = item,
                        onEditClick ={
                            sItems = sItems.map { it.copy(isEditingmode = it.id == item.id) }
                        },
                        onDeleteClick = {
                            sItems = sItems - item
                        })
                }
            }

        }

    }


    if (showDialog){
//        AlertDialog(onDismissRequest = { showDialog= false}) {
////            Text("Hello World!")
//        }
//
//        AlertDialog(onDismissRequest = {showDialog= false }, confirmButton = { /*TODO*/ }) {
////            Text("Hello World!")
////        }
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Button(onClick = {
                        if (itemName.isNotBlank()){
                            val newItem = ShoppingListItem(
                                id = sItems.size + 1,
                                name = itemName,
                                quantity = itemQuantity.toInt()
                            )
                            sItems = (sItems + newItem)
                            showDialog = false
                            itemName = ""
                            itemQuantity = ""
                        }
                    }){
                        Text("Add")
                    }

                    Button(onClick = { showDialog = false }){
                        Text("Cancel")
                    }
                }
            },
            title = {
                Text(
                    "Add Shopping Item",
                    style = MaterialTheme.typography.headlineSmall
                )
            },
//            containerColor = MaterialTheme.colorScheme.surface,
            text = {
                Column {
                    OutlinedTextField(value = itemName,
                        onValueChange = { itemName = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        label = { Text("Name") }
                    )
                    OutlinedTextField(value = itemQuantity,
                        onValueChange = { itemQuantity = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
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


@Composable
fun ShoppingItemEditor(item: ShoppingListItem, onEditComplete: (String, Int) -> Unit){

    var editedName by remember{ mutableStateOf(item.name)}
    var editedQuantity by remember{ mutableStateOf(item.quantity.toString())}
    var isEditMode by remember{ mutableStateOf(item.isEditingmode)}

    Row (
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(Color.LightGray),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column (

        ){
            // This is the text field for the name
            BasicTextField(value = editedName, 
                onValueChange = { editedName = it },
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp), // wrapContentSize() is used to make the TextField take only the space it needs

            )
            // This is the text field for the quantity
            BasicTextField(value = editedQuantity,
                onValueChange = { editedQuantity = it },
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp), // wrapContentSize() is used to make the TextField take only the space it needs

            )

        }

        Button(onClick = {
            isEditMode = false
            onEditComplete(editedName, editedQuantity.toIntOrNull() ?: 1)
        })
        {
            Text(text = "Save")
        }
    }

}

@Composable
fun ShoppingListItem(
    item: ShoppingListItem,  // This is the item to be displayed
    onEditClick: () -> Unit,  // This is the action to be performed when the edit button is clicked. It is a lambda function that takes no argument and returns nothing.
    onDeleteClick: () -> Unit
){
    Row (
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(
                border = BorderStroke(1.dp, Color.Cyan),
                shape = RoundedCornerShape(20)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = item.name, modifier = Modifier.padding(8.dp))
        Text(text = "Qty: ${item.quantity.toString()}", modifier = Modifier.padding(8.dp))
        Row (
            modifier = Modifier.padding(8.dp)
        ) {
            IconButton(onClick = onEditClick) {

                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }

            IconButton(onClick = onDeleteClick) {

                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        }
    }
}

