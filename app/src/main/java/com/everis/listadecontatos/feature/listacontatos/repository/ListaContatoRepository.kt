package com.everis.listadecontatos.feature.listacontatos.repository

import com.everis.listadecontatos.bases.BaseRepository
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO
import com.everis.listadecontatos.helpers.HelperDB
import com.everis.listadecontatos.helpers.HelperDB.Companion.COLUMNS_ID
import com.everis.listadecontatos.helpers.HelperDB.Companion.COLUMNS_NOME
import com.everis.listadecontatos.helpers.HelperDB.Companion.COLUMNS_TELEFONE
import com.everis.listadecontatos.helpers.HelperDB.Companion.TABLE_NAME
import java.sql.SQLDataException

open class ListaContatoRepository(
    helperDBPar: HelperDB? = null
) : BaseRepository(helperDBPar) {
    open fun requestListaContato(
        busca: String,
        onSuccess: ((List<ContatosVO>) -> Unit),
        onError: ((Exception) -> Unit)
    ) {
        try {

            val db = readDatabase
            var lista = mutableListOf<ContatosVO>()
            var where: String? = null
            var args: Array<String> = arrayOf()

            where = "$COLUMNS_NOME LIKE ?"
            args = arrayOf("%$busca%")

            var cursor = db?.query(TABLE_NAME, null, where, args, null, null, null)
            if (cursor == null) {
                db?.close()
                onError(SQLDataException("Nao foi possível fazer a query."))
                return
            }
            while (cursor.moveToNext()) {
                var contato = ContatosVO(
                    cursor.getInt(cursor.getColumnIndex(COLUMNS_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMNS_NOME)),
                    cursor.getString(cursor.getColumnIndex(COLUMNS_TELEFONE))
                )
                lista.add(contato)
            }
            db?.close()
            onSuccess(lista)
        } catch (ex: Exception) {
            onError(ex)
        }
    }
}