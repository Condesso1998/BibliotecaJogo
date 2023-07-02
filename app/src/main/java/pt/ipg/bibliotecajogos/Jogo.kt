package pt.ipg.bibliotecajogos

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.Calendar

data class Jogo(
    var titulo: String,
    var dataPublicacao: Calendar? = null,
    var categoria: Categoria,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaJogos.CAMPO_TITULO, titulo)
        valores.put(TabelaJogos.CAMPO_DATA_PUB, dataPublicacao?.timeInMillis)
        valores.put(TabelaJogos.CAMPO_FK_CATEGORIA, categoria.id)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor) : Jogo {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posTitulo = cursor.getColumnIndex(TabelaJogos.CAMPO_TITULO)
            val posDataPub = cursor.getColumnIndex(TabelaJogos.CAMPO_DATA_PUB)
            val posCategoriaFK = cursor.getColumnIndex(TabelaJogos.CAMPO_FK_CATEGORIA)
            val posDescCateg = cursor.getColumnIndex(TabelaJogos.CAMPO_DESC_CATEGORIA)

            val id = cursor.getLong(posId)
            val titulo = cursor.getString(posTitulo)

            var dataPub: Calendar?

            if (cursor.isNull(posDataPub)) {
                dataPub = null
            } else {
                dataPub = Calendar.getInstance()
                dataPub.timeInMillis = cursor.getLong(posDataPub)
            }

            val categoriaId = cursor.getLong(posCategoriaFK)
            val desricaoCategoria = cursor.getString(posDescCateg)

            return Jogo(titulo, dataPub, Categoria(desricaoCategoria, categoriaId), id)

        }
    }
}