package org.ieseljust.pmdm.places.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.ieseljust.pmdm.places.R

class AboutFragment : Fragment() {
    // Declarem el ViewModel per a la seua
    // inicialització posterior
    private lateinit var aboutViewModel: AboutViewModel

    // I definim el mètode que ens tornarà la vista quan
    // aquesta s'ha de crear
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inicialitzem aci el ViewModel a través de ViewModelProvider
        // I instanciem l'XML al contenidor
        aboutViewModel =
                ViewModelProvider(this).get(AboutViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_about, container, false)

        // Definim els observers per actualitzar automàticament els TextViews
        // (encara que aquests no es modificaran en temps d'exexució)
        val maintainerTextView: TextView = root.findViewById(R.id.nomAlumne)
        aboutViewModel.maintainer.observe(viewLifecycleOwner, Observer {
            maintainerTextView.text = it
        })

        val versionTextView: TextView = root.findViewById(R.id.aboutVersion)
        aboutViewModel.currentVersion.observe(viewLifecycleOwner, Observer {
            versionTextView.text = it
        })
        // I finalment retornem la vista
        return root
    }
}