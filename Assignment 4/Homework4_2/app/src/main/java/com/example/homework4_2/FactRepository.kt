package com.example.homework4_2

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.util.Date
import kotlin.random.Random
class FactRepository(val scope: CoroutineScope,
    private val dao: FactDAO) {

    private val client = HttpClient(Android)
    {
        install(ContentNegotiation)
        {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    val allFacts = dao.allFacts()

    suspend fun addItem(){
        try{
            val responseText: FunFact = client.get("https://uselessfacts.jsph.pl//api/v2/facts/random").body()
            dao.addFactData(FactData(responseText.text))
        }
        catch (e: Exception)
        {
            Log.e("FunFact Activity", "Error fetching", e)
        }
    }

    fun getFacts(): List<String> {
        var newList = listOf("")
        scope.launch {
            dao.allFacts().collect { factList ->
                for (fact in factList){
                    newList = newList + fact.text
                }
            }
        }
        return newList
    }


}