
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object SportsAPI {
    val retrofitService: SportsAPIService by lazy { retrofit.create(SportsAPIService::class.java) }
}


private const val BASE_URL =
    "https://www.thesportsdb.com/api/v1/json/4013017/"


private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()