package com.everis.listadecontatos.feature.listacontatos.viewmodel

import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO
import com.everis.listadecontatos.feature.listacontatos.repository.ListaContatoRepository
import com.everis.listadecontatos.helpers.HelperDB

open class ListaContatosViewModel(
    var helperDB: HelperDB? = null,
    var repository: ListaContatoRepository? = null
) {
    open fun getListaContatos(
        busca: String,
        onSuccess: ((List<ContatosVO>) -> Unit),
        onError: ((Exception) -> Unit)
    ) {
        Thread {
            Thread.sleep(1500)
            repository?.requestListaContato(
                busca,
                onSuccess = { lista -> onSuccess(lista) },
                onError = { ex -> onError(ex) }
            )
        }.start()
    }
}