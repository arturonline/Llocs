package org.ieseljust.pmdm.places.ui.home

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.ieseljust.pmdm.places.Lloc
import org.ieseljust.pmdm.places.Llocs

class HomeViewModel : ViewModel() {

    var llocLongClicked = MutableLiveData<Lloc?>()
    var llocClicked = MutableLiveData<Lloc?>()

    private fun llocClickedManager(lloc: Lloc, v: View){
        // Per establir el valor del liveData utilitzem value
        llocClicked.value=lloc
    }
    private fun llocLongClickedManager(lloc:Lloc, v: View): Boolean {
        // Per establir el valor del liveData utilitzem value
        llocLongClicked.value=lloc
        return true
    }

    private val _adaptador = MutableLiveData<AdaptadorLlocs>().apply {
        value = AdaptadorLlocs(Llocs,
            { lloc: Lloc, v: View -> llocClickedManager(lloc, v) },
            { lloc: Lloc, v: View -> llocLongClickedManager(lloc, v) }
        )
    }
    val adaptador:MutableLiveData<AdaptadorLlocs> =_adaptador

    fun removeLloc(lloc:Lloc){
        Llocs.remove(lloc)
        adaptador.value?.notifyDataSetChanged()
    }

}