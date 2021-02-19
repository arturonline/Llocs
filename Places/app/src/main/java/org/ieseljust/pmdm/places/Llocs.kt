package org.ieseljust.pmdm.places

import com.orm.SugarRecord

class Lloc : SugarRecord {
    var nom:String=""
    var descripcio:String=""
    var telefon : String=""
    var web: String=""
    var img: String=""
    var latitud: Double=0.0
    var longitud: Double=0.0

    constructor() {}

    constructor(nom:String,
                descripcio:String,
                telefon : String,
                web: String,
                img: String,
                latitud: Double,
                longitud: Double){
        this.nom=nom
        this.descripcio=descripcio
        this.telefon=telefon
        this.web=web
        this.img=img
        this.latitud=latitud
        this.longitud=longitud

    }
}

object Llocs {
    var llocs:ArrayList<Lloc> = ArrayList()

    init {
        llocs = SugarRecord.listAll<Lloc>(Lloc::class.java).
        toCollection(ArrayList())
    }


    fun add(lloc: Lloc){
        var esta: Boolean = false
        llocs.forEach{
            if (it == lloc) {
                esta = true
                return@forEach
            }
        }
        if (!esta) {
            llocs.add(lloc)
            SugarRecord.save(lloc)
        }
    }

    fun modifica(originalName: String, lloc: Lloc) {
        // Recorrem tots els llocs
        SugarRecord.listAll<Lloc>(Lloc::class.java).toCollection(ArrayList()).forEach buscaId@{
            if (it.nom==originalName){
                it.nom=lloc.nom
                it.descripcio=lloc.descripcio
                it.telefon=lloc.telefon
                it.web=lloc.web
                it.img=lloc.img
                it.latitud=lloc.latitud
                it.longitud=lloc.longitud
                it.save()
                return@buscaId
            }
        }
        // Refresquem llocs per refrescar la vista
        llocs=SugarRecord.listAll<Lloc>(Lloc::class.java).toCollection(ArrayList())
    }

    fun remove(lloc: Lloc){
        // TO-DO: Elimina un lloc
        llocs.remove(lloc)
        SugarRecord.delete(lloc)
    }

    fun getAt(position: Int):Lloc{
        // TO-DO: Obté un lloc en la posició donada
        return llocs[position]
    }

    fun getSize():Int{
        // TO-DO: Retorna la lonngitud de llocs
        return llocs.size
    }

    fun contains(nom:String):Boolean{
        // TO-DO: Diu si un lloc està o no
        llocs.forEach {
            if (it.nom == nom) return true;
        }
        return false
    }

    fun trobarLloc(nom: String): Lloc? {
        for (lloc in llocs) {
            if (lloc.nom == nom) {
                return lloc;
            }
        }
        return null
    }

}