package pt.ipg.bibliotecajogos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ListaJogosFragment : Fragment() {

    companion object {
        fun newInstance() = ListaJogosFragment()
    }

    private lateinit var viewModel: ListaJogosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lista_jogos, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListaJogosViewModel::class.java)
        // TODO: Use the ViewModel
    }

}