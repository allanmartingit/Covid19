package com.example.covid19

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {


  private var listaBoletim = arrayListOf<Boletim>()
  private var adapter = Adapter(listaBoletim)
  private var asyncTask : BoletimTask? = null
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(findViewById(R.id.appBar))

    CarregaDados()
    rvDados.layoutManager = LinearLayoutManager(applicationContext)
    rvDados.itemAnimator = DefaultItemAnimator()
    rvDados.adapter = adapter

  }

  fun showProgress(show: Boolean){
    if(show){
      txtMsg.text = "Loading..."
    }else{
      txtMsg.visibility = if(show) View.VISIBLE else View.GONE
      progressBar.visibility = if(show) View.VISIBLE else View.GONE
    }
  }

  fun CarregaDados(){
    listaBoletim.clear()
    if(listaBoletim.isNotEmpty()){
      showProgress(false)
    }else{
      if(asyncTask==null){
        if(BoletimHttp.hasConnection(this)){
          //startDownload()
          if(asyncTask?.status!=AsyncTask.Status.RUNNING){
            asyncTask = BoletimTask()
            asyncTask?.execute()
          }
        }else{
          progressBar.visibility = View.GONE
        }
      }else if(asyncTask?.status==AsyncTask.Status.RUNNING){
        showProgress(true)
      }
    }
  }

  inner class BoletimTask: AsyncTask<Void, Void, List<Boletim?>>(){

    override fun onPreExecute() {
      super.onPreExecute()
      showProgress(true)
    }


    override fun doInBackground(vararg params: Void?): List<Boletim>? {
      return BoletimHttp.loadBoletim()
    }

    private fun updateBoletins(result: List<Boletim>?){
      if(result != null){
        listaBoletim.clear()
        listaBoletim.addAll(result.reversed())
      }else{
        txtMsg.text = "Erro ao Carregar"
      }
      adapter.notifyDataSetChanged()
      asyncTask = null
    }

    override fun onPostExecute(result: List<Boletim?>?) {
      super.onPostExecute(result)
      showProgress(false)
      updateBoletins(result as List<Boletim>?)
    }

  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

    R.id.menu_refresh -> {
      CarregaDados()
      true
    }
    else -> {
      super.onOptionsItemSelected(item)
    }
  }

}
