package edu.towson.cosc435.meegan.semesterprojectpantrypal

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
//import com.google.gson.Gson

class EdamamService(private val appId: String, private val appKey: String) {
    private val httpClient = OkHttpClient()

    fun getFoodData(query: String): FoodDataResponse? {
        val url = "https://api.edamam.com/api/food-database/v2/parser".toHttpUrlOrNull()
            ?.newBuilder()
            ?.addQueryParameter("ingr", query)

            //The app_id and app_key are hidden in the local.properties file and would normally be called like this:
            //?.addQueryParameter("app_id", appId)
            //?.addQueryParameter("app_key", appKey)
            //However, for the sake of the project, I have included them here.

            ?.addQueryParameter("app_id", "1f0f700a")
            ?.addQueryParameter("app_key", "aad5cce9a9aaf24d46302c7f59ecd2ad")
            ?.build()

        val request = url?.let {
            Request.Builder()
                .url(it)
                .get()
                .build()
        }

        val response: Response? = request?.let { httpClient.newCall(it).execute() }
        val responseBody = response?.body?.string()

        return if (response!= null && response.isSuccessful && responseBody != null) {
            val gson = Gson()
            gson.fromJson(responseBody, FoodDataResponse::class.java)
        } else {
            null
        }
    }
}

data class FoodDataResponse(
    val text: String,
    val parsed: List<ParsedFood>,
    val hints: List<FoodHint>
)

data class ParsedFood(
    val food: Food
)

data class FoodHint(
    val food: Food,
    val measures: List<Measure>
)

data class Food(
    val foodId: String,
    val label: String,
    val knownAs: String,
    val nutrients: Nutrients,
    val category: String,
    val categoryLabel: String,
    val image: String
)

data class Measure(
    val uri: String,
    val label: String,
    val weight: Double,
    val qualified: List<Qualified> = emptyList()
)

data class Qualified(
    val qualifiers: List<Qualifier>,
    val weight: Double
)

data class Qualifier(
    val uri: String,
    val label: String
)

data class Nutrients(
    val ENERC_KCAL: Double,
    val PROCNT: Double,
    val FAT: Double,
    val CHOCDF: Double,
    val FIBTG: Double
)



