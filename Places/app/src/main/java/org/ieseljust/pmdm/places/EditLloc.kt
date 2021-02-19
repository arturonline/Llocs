package org.ieseljust.pmdm.places

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.ieseljust.pmdm.places.databinding.LlocEditBinding
import org.ieseljust.pmdm.places.ui.home.HomeFragment
import java.io.IOException


class EditLloc : AppCompatActivity(), LocationListener {
    private lateinit var binding: LlocEditBinding
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    private var tmpUri: String=""
    private var latitud: Double = 0.0
    private var longitud: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LlocEditBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var isEditing = false
        var originalName = ""
        // TO-DO: Gestió de l'edició
        // Capturem intent en cas de haber-se produit

        val nomLlocAEditar = intent.getStringExtra("nom")?:""

        if (nomLlocAEditar!="") {
            binding.editTextNom .setText(nomLlocAEditar)
            isEditing = true
            originalName = nomLlocAEditar
        }

        val desc = intent.extras?.getString("desc")?:""
        if(desc!="") binding.editTextDesc.setText(desc)

        val tel = intent.extras?.getString("tel")?:""
        if(desc!="") binding.editTextTel.setText(tel)

        val web = intent.extras?.getString("web")?:""
        if(web != "") binding.editTextWeb.setText(web)

        tmpUri = intent.extras?.getString("img")?:""
        if (tmpUri!="") {
            // Si tenim una imatge definida, l'obrim i obtenim el seu bitmap per
            // afegir-lo a l'ImageView addLlocImg.
            // Si no tenim els permissos suficients concedits quan hem get la Intent
            val inputStream = contentResolver.openInputStream(Uri.parse(tmpUri))
            val bitmap = BitmapFactory.decodeStream(inputStream)
            binding.addLlocImg.setImageBitmap(bitmap)
        }

        longitud = intent.extras?.getDouble("longitud")?:0.0
        latitud = intent.extras?.getDouble("latitud")?:0.0
        binding.BtCoordinates.text="("+String.format("%.3f", latitud)+", "+String.format("%.3f", longitud)+")"

        binding.imgSelector.setOnClickListener{
            val openDocumentIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            openDocumentIntent.addCategory(Intent.CATEGORY_OPENABLE)
            openDocumentIntent.type = "image/*"
            openDocumentIntent.flags = (Intent.FLAG_GRANT_READ_URI_PERMISSION
                    or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            startActivityForResult(openDocumentIntent, 1)
        }

        binding.btSave.setOnClickListener {
            val nom = binding.editTextNom.text.toString()
            val desc = binding.editTextDesc.text.toString()
            val tel = binding.editTextTel.text.toString()
            val web = binding.editTextWeb.text.toString()

            if (nom!="") {
                var nouLloc=Lloc(nom, desc, tel, web, tmpUri, latitud, longitud)
                // Si està editant, borrem primer l'anterior
                if (isEditing) Llocs.modifica(originalName, nouLloc)
                else Llocs.add(nouLloc)

                HomeFragment.allowRefresh=true
                finish()
            }
        }

        binding.btCancel.setOnClickListener {
            finish()
        }

        binding.BtCoordinates.setOnClickListener {
            // Invoca al mètode getLocation per obtenir les
            // coordenades GPS
            getLocation()
        }
        binding.btMaps.setOnClickListener {
            val uri:String="geo:"+latitud+","+longitud+"?z=20"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Si l'activitat s'ha resolt amb èxit, comprovem el codi de la
        // petició, i segons la petició sabem com interpretar la resposta.
        if (resultCode== Activity.RESULT_OK && requestCode==1){

            // En data tenim les dades de la resposta
            if (data != null) {
                // Guardem el string de la Uri en tmpUri, ja que una vegada la bolquem a
                // l'ImageView, no sabem com recuperar-la
                tmpUri=data.dataString.toString()

                val uri: Uri? = data.data

                try {
                    binding.addLlocImg.setImageURI(uri)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }

    }

    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // Estes comprovacions comproven si tenim o no permisos, però no sembla que donen permis
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    locationPermissionCode
            )
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }

    override fun onLocationChanged(location: Location) {
        latitud=location.latitude
        longitud=location.longitude
        binding.BtCoordinates.text="("+String.format("%.3f", latitud)+", "+String.format("%.3f", longitud)+")"
    }

}