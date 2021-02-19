package org.ieseljust.pmdm.places.ui.home

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import org.ieseljust.pmdm.places.Lloc
import org.ieseljust.pmdm.places.Llocs

/**
 * classe per presentar un dialeg de confirmació a l'hora de borrar un favorit
 */
class BorrarAlertDialog(
    private var llocNom: String,
    private var removelloc: (Lloc) -> Unit
) : DialogFragment() {

    // private val lloc = lloc
    //private val v = v

    /**
     * Definim aquesta interficie per a comunicar-nos en la activitat
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val title = "Borrar Lloc"
            val content = "Segur que voleu borrar el Lloc"
            val builder: AlertDialog.Builder = AlertDialog.Builder(
                requireActivity())
            builder.setTitle(title).setMessage(content)
                .setPositiveButton(android.R.string.ok) { _, _ ->

                    Llocs.trobarLloc(llocNom)?.let { it1 -> removelloc(it1) }
                    Log.i("INFORMACIO", "Ha fet click a borrar lloc")
                }
                .setNegativeButton(android.R.string.cancel) { _, _ ->
                    Log.i("INFORMACIO", "ha cancel·lat l'esborrat del lloc")
                }
            return builder.create()
        } ?: throw IllegalStateException("L'activitat no pot ser nul·la")
    }
}