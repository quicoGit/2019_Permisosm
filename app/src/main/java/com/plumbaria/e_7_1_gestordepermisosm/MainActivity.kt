package com.plumbaria.e_7_1_gestordepermisosm

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private companion object {

        val PERMISOS_INICIALES : Array<String> =
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_CONTACTS
            )

        val PERMISOS_CAMARA : Array<String> =
            arrayOf(
                Manifest.permission.CAMERA
            )

        val PERMISOS_CONTACTOS : Array<String> =
            arrayOf(
                Manifest.permission.READ_CONTACTS
            )

        val PERMISOS_LOCALIZACION : Array<String> =
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            )

        val PETICION_INICIAL = 123;
        val PETICION_CAMARA = PETICION_INICIAL+ 1
        val PETICION_CONTACTOS = PETICION_INICIAL + 2
        val PETICION_LOCALIZACION = PETICION_INICIAL + 3

    }

    private lateinit var localizacion:TextView
    private lateinit var camara:TextView
    private lateinit var internet:TextView
    private lateinit var contactos:TextView
    private lateinit var almacenamiento:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        localizacion = findViewById(R.id.location_value)
        camara = findViewById(R.id.camera_value)
        internet = findViewById(R.id.internet_value)
        contactos = findViewById(R.id.contacts_value)
        almacenamiento = findViewById(R.id.storage_value)

        if (!hayPermisoLocalizacion() || !hayPermisoContactos()) {
            ActivityCompat.requestPermissions(
                this,
                PERMISOS_INICIALES, PETICION_INICIAL
            )
        }
    }

    override fun onResume() {
        super.onResume()
        actualizaTabla()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.camera -> {
                if (hayPermisoCamara()){
                    accionCamara()
                } else {
                    ActivityCompat.requestPermissions(this,
                        PERMISOS_CAMARA,
                        PETICION_CAMARA)
                }
                return true
            }
            R.id.contacts -> {
                if (hayPermisoContactos()){
                    accionContactos()
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        PERMISOS_CONTACTOS,
                        PETICION_CONTACTOS
                    )
                }
                return true
            }
            R.id.location -> {
                if (hayPermisoLocalizacion()) {
                    accionLocalizacion()
                } else {
                    ActivityCompat.requestPermissions(this,
                        PERMISOS_LOCALIZACION,
                        PETICION_LOCALIZACION
                    )
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        actualizaTabla()
        when (requestCode) {
            PETICION_CAMARA -> {
                if (hayPermisoCamara()) {
                    accionCamara()
                } else {
                    error()
                }
            }
            PETICION_CONTACTOS -> {
                if (hayPermisoContactos()) {
                    accionContactos()
                } else {
                    error()
                }
            }
            PETICION_LOCALIZACION -> {
                if (hayPermisoLocalizacion()) {
                    accionLocalizacion()
                } else {
                    error()
                }
            }
        }
    }


    private fun hayPermiso(permiso:String):Boolean {
        return (
                ContextCompat.checkSelfPermission(this, permiso) ==
                        PackageManager.PERMISSION_GRANTED
                )
    }

    private fun hayPermisoLocalizacion():Boolean {
        return(hayPermiso(Manifest.permission.ACCESS_FINE_LOCATION))
    }

    private fun hayPermisoCamara():Boolean {
        return (hayPermiso(Manifest.permission.CAMERA))
    }

    private fun hayPermisoContactos():Boolean {
        return (hayPermiso(Manifest.permission.READ_CONTACTS))
    }

    private fun actualizaTabla() {
        localizacion.text = hayPermisoLocalizacion().toString()
        camara.text = hayPermisoCamara().toString()
        internet.text = hayPermiso(Manifest.permission.INTERNET).toString()
        contactos.text = hayPermisoContactos().toString()
        almacenamiento.text = hayPermiso(Manifest.permission.WRITE_EXTERNAL_STORAGE).toString()
    }

    private fun error() {
        Toast.makeText(this, R.string.toast_error, Toast.LENGTH_LONG).show()
    }

    private fun accionCamara() {
        Toast.makeText(this, R.string.toast_camara, Toast.LENGTH_LONG).show()
    }

    private fun accionContactos () {
        Toast.makeText(this, R.string.toast_contactos, Toast.LENGTH_LONG).show()
    }

    private fun accionLocalizacion() {
        Toast.makeText(this, R.string.toast_localizacion, Toast.LENGTH_LONG).show()
    }
}
