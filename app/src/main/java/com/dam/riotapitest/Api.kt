package com.dam.riotapitest


import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import services.ClientApi
import services.PlatformRoutes
import services.endpoints.ChampionInfo
import services.endpoints.ChampionMasteryDTO
import services.endpoints.SummonerDTO
import services.interceptors.TokenProvider
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.concurrent.Executors

class Api {


    fun getSummonerIdByName(name: String): String {
        var sumId : String = ""
        ClientApi.summonerV4(PlatformRoutes.EUW1).getSummonerByName(name).enqueue(object : Callback<SummonerDTO>{
            override fun onResponse(call: Call<SummonerDTO>, response: Response<SummonerDTO>) {
                response.body()?.let { summonerDTO ->
                    sumId= summonerDTO.id
                }
            }

            override fun onFailure(call: Call<SummonerDTO>, t: Throwable) {
                t.printStackTrace()
            }
        }
        )
        return sumId
    }

    fun initApi(){
        // OR simply put
        ClientApi.apply {
            tokenProvider = object : TokenProvider {
                override fun getToken(): String {
                    return "RGAPI-d82bdde8-9bca-44c9-9b5c-3e30617afbe7"
                }
            }
        }
    }
    // Utilizamos un Executor para realizar la llamada a la API en un hilo separado
    private val executor = Executors.newSingleThreadExecutor()

    fun getEncryptedSummonerIdByName(name: String): String {
        var sumId: String? = null

        executor.execute {
            try {
                val response = ClientApi.summonerV4(PlatformRoutes.EUW1).getSummonerByName(name).execute()
                if (response.isSuccessful) {
                    val summonerDTO = response.body()
                    sumId = summonerDTO?.id
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Esperamos un tiempo razonable para que la llamada de red se complete
        Thread.sleep(2000) // Ajusta el tiempo de espera según tus necesidades

        // Verificamos si sumId es null (si hubo un error) o si tiene un valor
        return sumId ?: "Error obteniendo el Summoner ID"
    }


    fun getChampionIdByName(nombreCampeon: String, context : Context): Long {
        try {
            val inputStream = context.resources.openRawResource(R.raw.championid)
            val reader = BufferedReader(InputStreamReader(inputStream))

            var line: String?
            while (reader.readLine().also { line = it } != null) {
                val partes = line!!.trim().split(":")
                if (partes.size == 2) {
                    val id = partes[0].trim()
                    val nombre = partes[1].trim().removeSurrounding("\"")
                    if (nombre.equals(nombreCampeon)) {
                        return id.toLong()
                    }
                }
            }

            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 33
    }

    fun getMasteryPoints(sumName: String, champName: String, context: Context): Int {
        var masPoints : Int? = null

        executor.execute {
            try {
                val response = ClientApi.championMasteryV4(PlatformRoutes.EUW1).getChampionMasteriesBySummonerAndChampion(getEncryptedSummonerIdByName(sumName),getChampionIdByName(champName,context)).execute()
                if (response.isSuccessful) {
                    val masteryDTO = response.body()
                    masPoints = masteryDTO?.championPoints
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        Thread.sleep(2000)

        return masPoints ?: 0
    }


}