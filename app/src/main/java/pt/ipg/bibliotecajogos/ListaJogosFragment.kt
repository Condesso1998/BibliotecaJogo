package pt.ipg.bibliotecajogos

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import pt.ipg.bibliotecajogos.databinding.FragmentListaJogosBinding

private const val ID_LOADER_JOGOS = 0

private val adapterJogos1: AdapterJogos
    get() {
        val adapterJogos = AdapterJogos()
        return adapterJogos
    }

/**
 * A simple [Fragment] subclass.
 * Use the [ListaLivrosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaJogosFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentListaJogosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaJogosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val adapterJogos = AdapterJogos()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapterJogos = AdapterJogos()
        binding.recyclerViewJogos.adapter = adapterJogos
        binding.recyclerViewJogos.layoutManager = LinearLayoutManager(requireContext())

        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_JOGOS, null, this)
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
        adapterJogos.cursor = null
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterJogos.cursor = data
    }
}