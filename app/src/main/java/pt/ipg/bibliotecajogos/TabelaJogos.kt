package pt.ipg.bibliotecajogos

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaJogos(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE " + NOME_TABELA + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titulo TEXT NOT NULL, " +
                CAMPO_DATA_PUB + " INTEGER, " + // Add a comma here
                "id_categoria INTEGER NOT NULL, " +
                "FOREIGN KEY (id_categoria) REFERENCES " + TabelaCategorias.NOME_TABELA +
                "(" + BaseColumns._ID + ") ON DELETE RESTRICT)");

    }

    override fun consulta(
        colunas: Array<String>,
        selecao: String?,
        argsSelecao: Array<String>?,
        groupby: String?,
        having: String?,
        orderby: String?
    ): Cursor {
        val sql = SQLiteQueryBuilder()
        sql.tables = "$NOME_TABELA INNER JOIN ${TabelaCategorias.NOME_TABELA} ON ${TabelaCategorias.CAMPO_ID}=$CAMPO_FK_CATEGORIA"

        return sql.query(db, colunas, selecao, argsSelecao, groupby, having, orderby)
    }

    companion object {
        const val NOME_TABELA = "jogos"

        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"
        const val CAMPO_TITULO = "titulo"
        const val CAMPO_DATA_PUB = "data_publicacao"
        const val CAMPO_FK_CATEGORIA = "id_categoria"
        const val CAMPO_DESC_CATEGORIA = TabelaCategorias.CAMPO_DESCRICAO

        val CAMPOS = arrayOf(CAMPO_ID, CAMPO_TITULO, CAMPO_DATA_PUB, CAMPO_FK_CATEGORIA, CAMPO_DESC_CATEGORIA)
    }
}
