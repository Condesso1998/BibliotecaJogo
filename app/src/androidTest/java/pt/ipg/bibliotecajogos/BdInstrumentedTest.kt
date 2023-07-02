package pt.ipg.bibliotecajogos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.util.Calendar

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BdInstrumentedTest {
    private fun getAppContext(): Context =
        InstrumentationRegistry.getInstrumentation().targetContext


    @Before
    fun apagaBaseDados() {
        getAppContext().deleteDatabase(BdJogosOpenHelper.NOME_BASE_DADOS)
    }


    @Test
    fun consegueAbrirBaseDados() {
        val openHelper = BdJogosOpenHelper(getAppContext())
        val bd = openHelper.readableDatabase
        assert(bd.isOpen)
    }


    private fun getWritableDatabase(): SQLiteDatabase {
        val openHelper = BdJogosOpenHelper(getAppContext())
        return openHelper.writableDatabase
    }


    @Test
    fun consegueInserirCategorias() {
        val bd = getWritableDatabase()

        val categoria = Categoria("Ação")
        insereCategoria(bd, categoria)
    }

    private fun insereCategoria(
        bd: SQLiteDatabase,
        categoria: Categoria
    ) {
        categoria.id = TabelaCategorias(bd).insere(categoria.toContentValues())
        assertNotEquals(-1, categoria.id)
    }

    @Test
    fun consegueInserirJogos() {
        val bd = getWritableDatabase()

        val categoria = Categoria("RPG")
        insereCategoria(bd, categoria)

        val jogo1 = Jogo("Dark Souls III", null,categoria.id)
        insereJogo(bd, jogo1)

        val jogo2 = Jogo("Metal Gear V", null,categoria.id)
        insereJogo(bd, jogo2)
    }

    private fun insereJogo(bd: SQLiteDatabase, jogo: Jogo) {
        jogo.id = TabelaJogos(bd).insere(jogo.toContentValues())
        assertNotEquals(-1, jogo.id)
    }

    @Test
    fun consegueLerCategorias() {
        val bd = getWritableDatabase()

        val categRomance = Categoria("Romance")
        insereCategoria(bd, categRomance)

        val categFiccao = Categoria("Ficção Científica")
        insereCategoria(bd, categFiccao)

        val tabelaCategorias = TabelaCategorias(bd)

        val cursor = tabelaCategorias.consulta(
            TabelaCategorias.CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(categFiccao.id.toString()),
            null,
            null,
            null
        )

        assert(cursor.moveToNext())

        val categBD = Categoria.fromCursor(cursor)

        assertEquals(categFiccao, categBD)

        val cursorTodasCategorias = tabelaCategorias.consulta(
            TabelaCategorias.CAMPOS,
            null, null, null, null,
            TabelaCategorias.CAMPO_DESCRICAO
        )

        assert(cursorTodasCategorias.count > 1)
    }

    @Test
    fun consegueLerJogos() {
        val bd = getWritableDatabase()

        val dataPub = Calendar.getInstance()
        dataPub.set(2016, 4, 1)

        val categoria = Categoria("Ação")
        insereCategoria(bd, categoria)

        val jogo1 = Jogo("Call of Duty I", dataPub, categoria.id)
        insereJogo(bd, jogo1)

        val jogo2 = Jogo("CS:GO", dataPub, categoria.id)
        insereJogo(bd, jogo2)

        val tabelaLivros = TabelaJogos(bd)

        val cursor = tabelaLivros.consulta(
            TabelaJogos.CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(jogo1.id.toString()),
            null,
            null,
            null
        )

        assert(cursor.moveToNext())

        val livroBD = Jogo.fromCursor(cursor)

        assertEquals(jogo1, livroBD)

        val cursorTodosLivros = tabelaLivros.consulta(
            TabelaJogos.CAMPOS,
            null, null, null, null,
            TabelaJogos.CAMPO_TITULO
        )

        assert(cursorTodosLivros.count > 1)
    }

}