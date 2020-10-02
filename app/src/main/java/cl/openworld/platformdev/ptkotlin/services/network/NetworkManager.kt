package cl.openworld.platformdev.ptkotlin.services.network

import cl.openworld.platformdev.ptkotlin.data.RemoteDataSource
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
val client = OkHttpClient.Builder().addInterceptor(logging).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl("http://platformdev.openworld.cl/api/")
    .client(client)
    .build()


object UserApi {
    val retrofitService: RemoteDataSource by lazy { retrofit.create(RemoteDataSource::class.java) }
}


