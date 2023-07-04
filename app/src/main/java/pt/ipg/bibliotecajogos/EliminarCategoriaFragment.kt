package pt.ipg.bibliotecajogos

import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.bibliotecajogos.databinding.FragmentEliminarCategoriaBinding

class EliminarCategoriaFragment : Fragment() {
    private lateinit var categoria: Categoria
    private var _binding: FragmentEliminarCategoriaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEliminarCategoriaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_eliminar

        categoria = EliminarCategoriaFragmentArgs.fromBundle(requireArguments()).categoria

        binding.textViewTitulo.text = categoria.descricao
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_eliminar -> {
                eliminar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaLivros()
                true
            }
            else -> false
        }
    }

    private fun voltaListaLivros() {
        findNavController().navigate(R.id.action_eliminarCategoriaFragment_to_listaCategoriasFragment)
    }

    private fun eliminar() {
        val enderecoCategoria = Uri.withAppendedPath(JogosContentProvider.ENDERECO_CATEGORIAS, categoria.id.toString())
        val numCategoriasEliminadas = requireActivity().contentResolver.delete(enderecoCategoria, null, null)

        if (numCategoriasEliminadas == 1) {
            Toast.makeText(requireContext(), getString(R.string.categoria_eliminada_com_sucesso), Toast.LENGTH_LONG).show()
            voltaListaLivros()
        } else {
            Snackbar.make(binding.textViewTitulo, getString(R.string.erro_eliminar_categoria), Snackbar.LENGTH_INDEFINITE)
        }
    }
}