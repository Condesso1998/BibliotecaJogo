package pt.ipg.bibliotecajogos

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import pt.ipg.bibliotecajogos.databinding.FragmentListaJogosBinding

private const val ID_LOADER_JOGOS = 0


class ListaJogosFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentListaJogosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var jogoSelecionado : Jogo? = null
        set(value) {
            field = value

            val mostrarEliminarAlterar = (value != null)

            val activity = activity as MainActivity
            activity.mostraOpcaoMenu(R.id.action_editar, mostrarEliminarAlterar)
            activity.mostraOpcaoMenu(R.id.action_eliminar, mostrarEliminarAlterar)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaJogosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private var adapterJogos: AdapterJogos? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterJogos = AdapterJogos(this)
        binding.recyclerViewJogos.adapter = adapterJogos
        binding.recyclerViewJogos.layoutManager = LinearLayoutManager(requireContext())

        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_JOGOS, null, this)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_lista_jogos
    }

    companion object {
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            JogosContentProvider.ENDERECO_JOGOS,
            TabelaJogos.CAMPOS,
            null, null,
            TabelaJogos.CAMPO_TITULO
        )
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterJogos!!.cursor = null
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if (adapterJogos != null) {
            adapterJogos!!.cursor = null
        }
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean {
        return when (item.itemId) {
            R.id.action_adicionar -> {
                adicionaLivro()
                true
            }
            R.id.action_editar -> {
                editarLivro()
                true
            }
            R.id.action_eliminar -> {
                eliminarLivro()
                true
            }
            else -> false
        }
    }

    private fun eliminarLivro() {
        val acao = ListaJogosFragmentDirections.actionListaJogosFragmentToEliminarJogoFragment(jogoSelecionado!!)
        findNavController().navigate(acao)
    }

    private fun editarLivro() {
        val acao = ListaJogosFragmentDirections.actionListaJogosFragmentToNovoJogoFragment(jogoSelecionado!!)
        findNavController().navigate(acao)
    }

    private fun adicionaLivro() {
        findNavController().navigate(R.id.action_ListaJogosFragment_to_novoJogoFragment)
    }
}