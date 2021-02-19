package org.ieseljust.pmdm.places.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.ieseljust.pmdm.places.EditLloc
import org.ieseljust.pmdm.places.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    companion object { var allowRefresh:Boolean=true }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
            val root = inflater.inflate(R.layout.fragment_home, container, false)

            val RVLlocs: RecyclerView = root.findViewById(R.id.RVLlocs)
            RVLlocs.layoutManager = LinearLayoutManager(context)
            homeViewModel.adaptador.observe(viewLifecycleOwner, Observer {
                RVLlocs.adapter = it
            })
            homeViewModel.llocClicked.observe(viewLifecycleOwner, Observer {
                lloc ->
                lloc?.let {
                    homeViewModel.llocClicked.value = null
                    val intent = Intent(context, EditLloc::class.java)
                    intent.putExtra("nom", lloc.nom)
                    intent.putExtra("desc", lloc.descripcio)
                    intent.putExtra("tel", lloc.telefon)
                    intent.putExtra("web", lloc.web)
                    intent.putExtra("img", lloc.img)
                    intent.putExtra("longitud", lloc.longitud)
                    intent.putExtra("latitud", lloc.latitud)
                    startActivity(intent)
                }
            })

            homeViewModel.llocLongClicked.observe(viewLifecycleOwner, Observer {
                lloc ->
                lloc?.let {
                    homeViewModel.llocLongClicked.value = null
                    val elMeuDialegModal = BorrarAlertDialog (lloc.nom, {
                        homeViewModel.removeLloc(lloc) })
                    elMeuDialegModal.show(this.parentFragmentManager, "confirmDialog")
                }
            })

        return root
    }
    override fun onResume() {
        super.onResume()
        if (allowRefresh) {
            allowRefresh = false
            requireParentFragment().childFragmentManager.beginTransaction().detach(this)
                    .attach(this).commit();
        }

    }
}
