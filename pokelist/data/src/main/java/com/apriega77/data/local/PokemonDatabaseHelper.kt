package com.apriega77.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.sqlite.transaction
import com.apriega77.domain.model.Pokemon
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PokemonDatabaseHelper @Inject constructor(
    @ApplicationContext context: Context
) : SQLiteOpenHelper(context, "pokemon_db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE pokemon (
                name TEXT PRIMARY KEY,
                url TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS pokemon")
        onCreate(db)
    }

    fun insertPokemonList(pokemonList: List<Pokemon>) {
        val db = writableDatabase
        db.transaction {
            try {
                for (pokemon in pokemonList) {
                    val values = ContentValues().apply {
                        put("name", pokemon.name)
                    }
                    insertWithOnConflict("pokemon", null, values, SQLiteDatabase.CONFLICT_REPLACE)
                }
            } finally {
            }
        }
    }

    fun getPokemonList(offset: Int, limit: Int): List<Pokemon> {
        val db = readableDatabase
        val list = mutableListOf<Pokemon>()
        val cursor = db.rawQuery(
            "SELECT * FROM pokemon LIMIT ? OFFSET ?",
            arrayOf(limit.toString(), offset.toString())
        )
        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            list.add(Pokemon(name))
        }
        cursor.close()
        return list
    }
}