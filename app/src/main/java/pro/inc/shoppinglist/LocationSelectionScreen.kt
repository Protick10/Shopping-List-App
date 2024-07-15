package pro.inc.shoppinglist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

// this would be a pop up


@Composable
fun LocationSelectionScreen(
    location: LocationData,
    onLocationSelected: (LocationData) -> Unit){


    //user location is the location where the phone is currently located
    val userLocation = remember {
        mutableStateOf(LatLng(location.latitude, location.longitude))  //latlng is a class from google maps api. It is used to store latitude and longitude
    }


    //this is the camera position state..camera position is the position of the camera on the map.So we're going to take the position and we're going to set it up as
    // the camera position dot from lat
    val cameraPositionState = rememberCameraPositionState {
       position = CameraPosition.fromLatLngZoom(userLocation.value, 15f) //fromLatLngZoom is a function that takes a latlng and a zoom level and returns a camera position
    }

    //the cool thing is because we're using remember and state, that will happen dynamically.
    //
    //So if we change the location of the user, the camera position state will change and it will change
    //
    //the position and we will go to a different spot on the camera on the maps map.


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            cameraPositionState = cameraPositionState,
            onMapClick = {
                userLocation.value = it
            }
        ){
            //marker is the red dot that you see on the map
            Marker(state = MarkerState(userLocation.value))  //MarkerState is a class from google maps api. It is used to store the state of the marker..it  will take latlng and show the marker on the map on that location
        }

        var newLocation: LocationData

        Button(onClick = {
            //The location data here just needs a latitude and longitude.
            //
            //Right.
            //
            //And we're just passing that.
            //
            //And where do we get that from.
            //
            //We get that from the user location which is our remember variable that we are overriding or overwriting
            //
            //whenever we click on the map.
            //
            //Because the user location is overridden here the state is changed.
            //
            //At that point the value is changed.
            //
            //So we're taking that value.
            //
            //And now we're taking the latitude and it's longitude.
            //
            //And we're passing that to our location data data class creating an object of it and storing that inside
            //
            //of this new location okay.
            newLocation = LocationData(userLocation.value.latitude, userLocation.value.longitude)
            onLocationSelected(newLocation)  // that is our location selection screen event
        }) {
            Text(text ="Set Location")

        }

    }

}