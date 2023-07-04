package pt.ipg.bibliotecajogos

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import pt.ipg.bibliotecajogos.databinding.FragmentNovaCategoriaBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class NovaCategoriaFragment : Fragment() {
    private var categoria: Categoria? = null
    private var _binding: FragmentNovaCategoriaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNovaCategoriaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_guardar_cancelar

        val categoria = NovaCategoriaFragmentArgs.fromBundle(requireArguments()).categoria

        if (categoria != null) {
            activity.atualizaTitulo(R.string.editar_categoria_label)

            binding.editTextTitulo.setText(categoria.descricao)
        }else{
            activity.atualizaTitulo(R.string.nova_categoria_label)
        }

        this.categoria = categoria
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true
            }

            R.id.action_cancelar -> {
                voltaListaCategorias()
                true
            }

            else -> false
        }
    }

    private fun voltaListaCategorias() {
        findNavController().navigate(R.id.action_novaCategoriaFragment_to_listaCategoriasFragment)
    }

    private fun guardar() {
        val titulo = binding.editTextTitulo.text.toString()
        if (titulo.isBlank()) {
            binding.editTextTitulo.error = getString(R.string.titulo_obrigatorio)
            binding.editTextTitulo.requestFocus()
            return
        }

        if (categoria == null) {
            val categoria = Categoria(
                titulo
            )
            insereCategoria(categoria)
        } else {
            val categoria = categoria!!
            categoria.descricao = titulo

            alteraCategoria(categoria)
        }

    }

    private fun alteraCategoria(categoria: Categoria) {
        val enderecoCategoria = Uri.withAppendedPath(JogosContentProvider.ENDERECO_CATEGORIAS, categoria.id.toString())
        val categoriasAlteradas = requireActivity().contentResolver.update(enderecoCategoria, categoria.toContentValues(), null, null)

        if (categoriasAlteradas == 1) {
            Toast.makeText(requireContext(), R.string.categoria_guardada_com_sucesso, Toast.LENGTH_LONG).show()
            voltaListaCategorias()
        } else {
            binding.editTextTitulo.error = getString(R.string.erro_guardar_categoria)
        }
    }

    private fun insereCategoria(categoria: Categoria) {
        val id = requireActivity().contentResolver.insert(
            JogosContentProvider.ENDERECO_CATEGORIAS,
            categoria.toContentValues()
        )

        if (id == null) {
            binding.editTextTitulo.error = getString(R.string.erro_guardar_categoria)
            return
        }

        Toast.makeText(
            requireContext(),
            getString(R.string.categoria_guardada_com_sucesso),
            Toast.LENGTH_SHORT
        ).show()
        voltaListaCategorias()
    }
}