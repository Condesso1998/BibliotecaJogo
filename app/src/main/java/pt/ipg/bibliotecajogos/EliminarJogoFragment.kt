package pt.ipg.bibliotecajogos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import pt.ipg.bibliotecajogos.databinding.FragmentEliminarJogoBinding

class EliminarJogoFragment : Fragment() {
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
    }
}