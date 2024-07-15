package pro.inc.shoppinglist

import retrofit2.http.GET
import retrofit2.http.Query


//So basically this interface that we just built, this geocoding API service is creating this URL or
//
//will will create a portion of the URL for us, which is this portion here plus the latitude and longitude
//
//and the key.
//
//So you see those here lat long and key.
//
//So these are the two things that I need to pass to this website.
interface GeocodingApiService {


    @GET("maps/api/geocode/json") //get is a annotation from retrofit which is used to get data from the server

    //so we have the url maps/api/geocode/json  + + latlng + &key


    //So whenever we call this function this suspend function we need to pass the latitude longitude as a
    //
    //string and the key as a string.
    //
    //And it will then create this URL for us or like this portion of the URL.
    //
    //And when we call or use this function, it will return a geocoding response, which is this entire code
    suspend fun getAddressFromCoordinates(
        @Query("latlng") latlng: String,  //query is a annotation from retrofit
        @Query("key") apiKey: String,

        ): GeocodingResponse
}