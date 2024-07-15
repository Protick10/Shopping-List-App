package pro.inc.shoppinglist

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//So now let's combine what we did in the last video with setting up a retrofit object or a client that
//
//takes the base URL and adds whatever we prepare with this interface.
object RetrofitClient {

    private const val BASE_URL = "https://maps.googleapis.com/"

    fun create(): GeocodingApiService{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        return retrofit.create(GeocodingApiService::class.java)
    }
}