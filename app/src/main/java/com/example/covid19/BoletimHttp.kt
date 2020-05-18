package com.example.covid19

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

object BoletimHttp {
    val Json_URL="https://raw.githubusercontent.com/ramonsl/ws-covid/master/db.json"



    fun hasConnection(ctx: Context): Boolean{
        val cm = ctx.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val info =  cm.activeNetworkInfo
        return info != null && info.isConnected
    }

    fun loadBoletim(): List<Boletim>?{

        val client = OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()

        val request = Request.Builder()
            .url(Json_URL)
            .build()

        val response = client.newCall(request).execute()
        val jsonString = response.body?.string()
        val json = JSONArray(jsonString)

        return readBoletim(json)

    }




    fun readBoletim(json: JSONArray) : List<Boletim>? {
        val boletins = arrayListOf<Boletim>()
        try {
            for (i in 0 .. json.length()-1) {
                var js = json.getJSONObject(i)
                val dia = formatarData(js.getString("boletim").substring(0, 10))
                var boletim = Boletim(
                    js.getString("Suspeitos").toInt(),
                    js.getString("Confirmados").toInt(),
                    js.getString("Descartados").toInt(),
                    js.getString("Monitoramento").toInt(),
                    js.getString("Curados").toInt(),
                    js.getString("mortes").toInt(),
                    dia,
                    js.getString("boletim").substring(11, 16)
                )
                boletins.add(boletim)
            }
        } catch (e: IOException) {
            Log.e("Erro", "Impossivel ler JSON")
        }

        return boletins
    }

    fun formatarData(data: String): String {
        val diaString = data
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        var date = LocalDate.parse(diaString)
        var formattedDate = date.format(formatter)
        return formattedDate
    }



}