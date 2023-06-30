package mobile.newsapp.data.api

import mobile.newsapp.data.model.Response
import retrofit2.http.GET

interface ApiServices {
    @GET("api/mobile/news/list")
    suspend fun getNewsList(): Response
}