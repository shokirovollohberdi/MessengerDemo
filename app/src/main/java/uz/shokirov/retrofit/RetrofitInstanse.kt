package uz.shokirov.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.shokirov.models.Constants.Companion.BASE_URL
import uz.shokirov.notification.NotificationAPI

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val api by lazy {
            retrofit.create(NotificationAPI::class.java)
        }
    }
}