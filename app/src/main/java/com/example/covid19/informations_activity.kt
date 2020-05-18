package com.example.covid19

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_informations_activity.*


class informations_activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informations_activity)

        val arrayBoletim = this.intent.getParcelableExtra<Boletim>("Boletim")

        appBar.title = "Boletim do dia "+arrayBoletim.data



        nSuspeitos.text = arrayBoletim.suspeitos.toString()
        nConfirmados.text = arrayBoletim.confirmados.toString()
        nDescartados.text = arrayBoletim.descartados.toString()
        nMonitoramentos.text = arrayBoletim.monitoramento.toString()
        nMortes.text = arrayBoletim.mortes.toString()
        nCurados.text = arrayBoletim.curados.toString()


        println(arrayBoletim.toString())

        appBar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(this@informations_activity, MainActivity::class.java)
                startActivity(intent)
            }
        })

    }
}
