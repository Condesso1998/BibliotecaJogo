package pt.ipg.bibliotecajogos

import android.content.ContentValues

data class Jogo(
    var titulo: String,
    var idCategoria: Long,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaJogos.CAMPO_TITULO, titulo)
        valores.put(TabelaJogos.CAMPO_FK_CATEGORIA, idCategoria)

        return valores
    }
}