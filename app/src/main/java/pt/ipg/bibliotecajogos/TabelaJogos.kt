package pt.ipg.bibliotecajogos

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaJogos(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE " + NOME_TABELA + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titulo TEXT NOT NULL, " +
                "id_categoria INTEGER NOT NULL, " +
                "FOREIGN KEY (id_categoria) REFERENCES " + TabelaCategorias.NOME_TABELA +
                "(" + BaseColumns._ID + ") ON DELETE RESTRICT)");

    }

    companion object {
        const val NOME_TABELA = "jogos"
    }
}
