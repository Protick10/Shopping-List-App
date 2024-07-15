package pro.inc.shoppinglist

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Looper


import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import java.util.Locale

class LocationUtils(val context: Context) {

    // FusedLocationProviderClient - Main class for receiving location updates.
    //So this is the main entry point for interacting with the fused location provider.
    //So basically this fuse location provider client will fuse the location together so we can work with
    private val _fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission") //lint error is suppressed because we are checking for permission in the calling function.so don't worry about missing permission we are handling it.
    fun requestLocationUpdates(viewModel: LocationViewModel) {

        val locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                locationResult.lastLocation?.let {
                    val location = LocationData(it.latitude, it.longitude)

                    viewModel.updateLocation(location)
                }
            }
        }

        //This is the method that we use to request location updates.
        //So we pass in the location request and the location callback.

        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()

        _fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper()) //loop.getMainLooper() is the main thread looper.
    }

    fun hasLocationPermission(context: Context): Boolean{
        return ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }



    //This function will take the location data and return the address of that location.
//    fun reverseGeocodeLocation(location: LocationData) : String{
//        val  geocoder = Geocoder(context, Locale.getDefault()) //locale.getDefault() will get the default locale of the device. locale is a class that represents a specific geographical, political, or cultural region.
//        val coordinates = LatLng(location.latitude, location.longitude)
//        val address:MutableList<Address>? =
//            geocoder.getFromLocation(coordinates.latitude, coordinates.longitude, 1)
//        return if (address?.isNotEmpty() == true){
//            address[0].getAddressLine(0)
//        }else{
//            "Unknown Location"
//        }
//    }


}