package pt.ipg.bibliotecajogos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import pt.ipg.bibliotecajogos.databinding.FragmentListaCategoriasBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ListaCategoriasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaCategoriasFragment : Fragment() {
    private var _binding: FragmentListaCategoriasBinding? = null

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_categorias, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapterCategorias = AdapterCategorias()
        binding.recyclerViewCategorias.adapter = adapterCategorias
        binding.recyclerViewCategorias.layoutManager = LinearLayoutManager(requireContext())
    }

    companion object {
    }
}