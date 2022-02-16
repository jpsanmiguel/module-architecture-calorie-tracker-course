package sanmi.labs.tracker_data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import sanmi.labs.tracker_data.remote.dto.SearchDto

interface OpenFoodApi {

    @GET("cgi/search.pl?search_simple=1&json=1&action=process&fields=product_name,nutriments,image_front_thumb_url")
    suspend fun searchFood(
        @Query("search_terms") searchTerms: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
    ): SearchDto

    companion object {
        const val BASE_URL = "https://us.openfoodfacts.org/"
    }
}