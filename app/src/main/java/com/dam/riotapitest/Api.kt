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

    val EUW = 1
    val BR = 2
    val KR = 3
    val LA1 = 4
    val LA2 = 5
    val NA = 6
    val OCE = 7
    val TR = 8
    val RU = 9
    val EUN = 10
    val JP = 11


    fun initApi(){
        // OR simply put
        ClientApi.apply {
            tokenProvider = object : TokenProvider {
                override fun getToken(): String {
                    return "RGAPI-26015c48-f5f6-4198-b608-8b4522c82a3c"
                }
            }
        }
    }
    // Utilizamos un Executor para realizar la llamada a la API en un hilo separado
    private val executor = Executors.newSingleThreadExecutor()


    fun getEncryptedSummonerIdByName(name: String, server : PlatformRoutes): String {
        var sumId: String? = null

        executor.execute {
            try {
                val response = ClientApi.summonerV4(server).getSummonerByName(name).execute()
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

    fun getMasteryPoints(sumName: String, champName: String, context: Context,serverSelect : Int, callback: (Int) -> Unit) {
        var server: PlatformRoutes


        when (serverSelect) {
            0 -> {server = PlatformRoutes.EUW1}
            1 -> {server = PlatformRoutes.BR1}
            2 -> {server = PlatformRoutes.KR}
            3 -> {server = PlatformRoutes.LA1}
            4 -> {server = PlatformRoutes.LA2}
            5 -> {server = PlatformRoutes.NA1}
            6 -> {server = PlatformRoutes.OC1}
            7 -> {server = PlatformRoutes.TR1}
            8 -> {server = PlatformRoutes.RU}
            9 -> {server = PlatformRoutes.EUN1}
            10 -> {server = PlatformRoutes.JP1}
            else -> {server = PlatformRoutes.EUW1}
            }

        ClientApi.championMasteryV4(server).getChampionMasteriesBySummonerAndChampion(
            getEncryptedSummonerIdByName(sumName, server),
            getChampionIdByName(champName, context)
        ).enqueue(object : Callback<ChampionMasteryDTO> {
            override fun onResponse(call: Call<ChampionMasteryDTO>, response: Response<ChampionMasteryDTO>) {
                response.body()?.let { championMasteryDTO ->
                    val masPoints = championMasteryDTO.championPoints
                    callback(masPoints ?: 0)
                }
            }

            override fun onFailure(call: Call<ChampionMasteryDTO>, t: Throwable) {
                t.printStackTrace()
                println(t.message)
                callback(0)
            }
        })
    }



}