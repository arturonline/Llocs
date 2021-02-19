package org.ieseljust.pmdm.places.ui.home

import org.ieseljust.pmdm.places.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorLlocs(
    private val llistaLlocs: Llocs,
    private val eventListener: (Lloc, View) -> Unit,
    private val longListener: (Lloc, View) -> Boolean
) :
    RecyclerView.Adapter<AdaptadorLlocs.FavoriteHolder>() {

    /**
     * ViewHolder necesari per al Adaptador.
     * Cada vegada que un nou item es fa visible mentre es fa scroll, esta classe
    s’assegura que l’element mostra el contingut que toca en cada posició de la llista.
     */
    class FavoriteHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val titol: TextView = item.findViewById(R.id.nomLloc)
        private val desc: TextView = item.findViewById(R.id.descLloc)
        private val miItem = item
        fun bind(lloc: Lloc, eventListener: (Lloc, View) -> Unit, longListener: (Lloc, View) -> Boolean) {
            titol.text = lloc.nom
            desc.text = lloc.descripcio
            miItem.setOnClickListener{ eventListener(lloc, miItem) }
            miItem.setOnLongClickListener{ longListener(lloc, miItem) }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FavoriteHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.lloc, viewGroup, false)

        return FavoriteHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val favorit = llistaLlocs.getAt(position)
        holder.bind(favorit, eventListener, longListener);

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = llistaLlocs.getSize()
}