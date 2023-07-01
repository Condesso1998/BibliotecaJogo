package pt.ipg.bibliotecajogos

import android.database.sqlite.SQLiteDatabase

class TabelaCategorias(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, descricao TEXT NOT NULL)")
    }

    companion object {
        const val NOME_TABELA = "categorias"
    }
}