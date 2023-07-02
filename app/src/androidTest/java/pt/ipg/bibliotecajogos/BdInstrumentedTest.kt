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
        //getAppContext().deleteDatabase(BdJogosOpenHelper.NOME_BASE_DADOS)
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

        val jogo1 = Jogo("Dark Souls III", null,categoria)
        insereJogo(bd, jogo1)

        val jogo2 = Jogo("Metal Gear V", null,categoria)
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

        val categoria = Categoria("Contos")
        insereCategoria(bd, categoria)

        val livro1 = Jogo("Todos os Contos", null, categoria)
        insereJogo(bd, livro1)

        val dataPub = Calendar.getInstance()
        dataPub.set(2016, 4, 1)

        val livro2 = Jogo("Contos de Grimm", dataPub, categoria)
        insereJogo(bd, livro2)

        val tabelaJogos = TabelaJogos(bd)

        val cursor = tabelaJogos.consulta(
            TabelaJogos.CAMPOS,
            "${TabelaJogos.CAMPO_ID}=?",
            arrayOf(livro1.id.toString()),
            null,
            null,
            null
        )

        assert(cursor.moveToNext())

        val jogoBD = Jogo.fromCursor(cursor)

        assertEquals(livro1, jogoBD)

        val cursorTodosLivros = tabelaJogos.consulta(
            TabelaJogos.CAMPOS,
            null, null, null, null,
            TabelaJogos.CAMPO_TITULO
        )

        assert(cursorTodosLivros.count > 1)
    }

    @Test
    fun consegueAlterarCategorias() {
        val bd = getWritableDatabase()

        val categoria = Categoria("...")
        insereCategoria(bd, categoria)

        categoria.descricao = "Poesia"

        val registosAlterados = TabelaCategorias(bd).altera(
            categoria.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(categoria.id.toString())
        )

        assertEquals(1, registosAlterados)
    }

    @Test
    fun consegueAlterarLivros() {
        val bd = getWritableDatabase()

        val categoria1 = Categoria("Ação")
        insereCategoria(bd, categoria1)

        val categoria2 = Categoria("MMO")
        insereCategoria(bd, categoria2)

        val jogo = Jogo("...", null,categoria1)
        insereJogo(bd, jogo)

        val novaDataPub = Calendar.getInstance()
        novaDataPub.set(1968, 1, 1)

        jogo.categoria = categoria1
        jogo.titulo = "Dark Souls III"
        jogo.dataPublicacao = novaDataPub


        val registosAlterados = TabelaJogos(bd).altera(
            jogo.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(jogo.id.toString())
        )

        assertEquals(1, registosAlterados)
    }

    @Test
    fun consegueApagarCategorias() {
        val bd = getWritableDatabase()

        val categoria = Categoria("...")
        insereCategoria(bd, categoria)

        val registosEliminados = TabelaCategorias(bd).elimina(
            "${BaseColumns._ID}=?",
            arrayOf(categoria.id.toString())
        )

        assertEquals(1, registosEliminados)
    }

    @Test
    fun consegueApagarLivros() {
        val bd = getWritableDatabase()

        val categoria = Categoria("Programação")
        insereCategoria(bd, categoria)

        val jogo = Jogo("...", null, categoria)
        insereJogo(bd, jogo)

        val registosEliminados = TabelaJogos(bd).elimina(
            "${BaseColumns._ID}=?",
            arrayOf(jogo.id.toString())
        )

        assertEquals(1, registosEliminados)
    }

}