package com.everis.listadecontatos.feature.listacontatos.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.everis.listadecontatos.R
import com.everis.listadecontatos.application.ContatoApplication
import com.everis.listadecontatos.bases.BaseActivity
import com.everis.listadecontatos.feature.contato.ContatoActivity
import com.everis.listadecontatos.feature.listacontatos.adapter.ContatoAdapter
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO
import com.everis.listadecontatos.feature.listacontatos.repository.ListaContatoRepository
import com.everis.listadecontatos.feature.listacontatos.viewmodel.ListaContatosViewModel
import com.everis.listadecontatos.helpers.HelperDB
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


class ListaContatoActivity : BaseActivity() {

    private var adapter:ContatoAdapter? = null
    var viewModel: ListaContatosViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(!intent.getBooleanExtra("isMock",false))viewModel = ListaContatosViewModel(
            HelperDB(this)
        )
        setupToolBar(toolBar, "Lista de contatos",false)
        setupListView()
        setupOnClicks()
    }

    private fun setupOnClicks(){
        fab.setOnClickListener { onClickAdd() }
        ivBuscar.setOnClickListener { onClickBuscar() }
    }

    private fun setupListView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        if(!intent.getBooleanExtra("isMock",false))onClickBuscar()
    }

    private fun onClickAdd(){
        val intent = Intent(this,ContatoActivity::class.java)
        startActivity(intent)
    }

    private fun onClickItemRecyclerView(index: Int){
        val intent = Intent(this,ContatoActivity::class.java)
        intent.putExtra("index", index)
        startActivity(intent)
    }

    fun onClickBuscar(){
        val busca = etBuscar.text.toString()
        progress.visibility = View.VISIBLE
        viewModel?.getListaContatos(
            busca,
            onSuccess = {listaFiltrada ->
                runOnUiThread {
                    adapter = ContatoAdapter(this,listaFiltrada) {onClickItemRecyclerView(it)}
                    recyclerView.adapter = adapter
                    progress.visibility = View.GONE
                    Toast.makeText(this,"Buscando por $busca",Toast.LENGTH_SHORT).show()
                }
            }, onError = { ex ->
                runOnUiThread {
                    AlertDialog.Builder(this)
                        .setTitle("Atenção")
                        .setMessage("Erro ao completar solicitação")
                        .setPositiveButton("OK") { alert, _ ->
                            alert.dismiss()
                        }.show()

                }
            }
        )
    }

}
