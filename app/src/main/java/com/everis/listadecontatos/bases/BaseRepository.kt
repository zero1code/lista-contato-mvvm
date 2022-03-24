package com.everis.listadecontatos.bases

import android.database.sqlite.SQLiteDatabase
import com.everis.listadecontatos.helpers.HelperDB

open class BaseRepository(
    var helperDB: HelperDB? = null
) {
    val readDatabase: SQLiteDatabase?
    get() {
        return helperDB?.readableDatabase
    }

    val writeDatabase: SQLiteDatabase?
        get() {
            return helperDB?.writableDatabase
        }
}