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
import pt.ipg.bibliotecajogos.databinding.FragmentEliminarJogoBinding

class EliminarJogoFragment : Fragment() {
    private lateinit var jogo: Jogo
    private var _binding: FragmentEliminarJogoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEliminarJogoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_eliminar

        jogo = EliminarJogoFragmentArgs.fromBundle(requireArguments()).jogo

        binding.textViewTitulo.text = jogo.titulo
        binding.textViewCategoria.text = jogo.categoria.descricao
        if (jogo.dataPublicacao != null) {
            binding.textViewDataPub.text = DateFormat.format("yyyy-MM-dd", jogo.dataPublicacao)
        }
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
                voltaListaJogos()
                true
            }
            else -> false
        }
    }

    private fun voltaListaJogos() {
        findNavController().navigate(R.id.action_eliminarJogoFragment_to_listaJogosFragment)
    }

    private fun eliminar() {
        val enderecoJogo = Uri.withAppendedPath(JogosContentProvider.ENDERECO_JOGOS, jogo.id.toString())
        val numJogosEliminados = requireActivity().contentResolver.delete(enderecoJogo, null, null)

        if (numJogosEliminados == 1) {
            Toast.makeText(requireContext(), getString(R.string.jogo_eliminado_com_sucesso), Toast.LENGTH_LONG).show()
            voltaListaJogos()
        } else {
            Snackbar.make(binding.textViewTitulo, getString(R.string.erro_eliminar_jogo), Snackbar.LENGTH_INDEFINITE)
        }
    }
}