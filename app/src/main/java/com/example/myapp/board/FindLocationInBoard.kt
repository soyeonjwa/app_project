package com.example.myapp.board

import android.app.Activity
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.R
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener



class FindLocationInBoard : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_location_in_board)

        // Initializing the Places API
        // with the help of our API_KEY  getString(R.string.google_api_key)
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyBRoZ4lT6rZJlAw72BB9hYtvYjOcr4GatQ")
        }
        val placesClient = Places.createClient(this)


        val autocompleteSupportFragment = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment

        autocompleteSupportFragment.setPlaceFields(
            listOf(
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG
            )
        )
        autocompleteSupportFragment.setCountry("KR");

        autocompleteSupportFragment.setOnPlaceSelectedListener(object :PlaceSelectionListener{
            override fun onPlaceSelected(place: Place) {

                val name = place.name
                val address = place.address
                val latlng = place.latLng

                Log.d("위치 검색","Name: $name \nAddress: $address\nLatLng: $latlng")


                val intent = Intent(this@FindLocationInBoard,BoardRegister::class.java)
                intent.putExtra("Lat",latlng.latitude.toDouble())
                intent.putExtra("Lng",latlng.longitude.toDouble())
                intent.putExtra("address",address.toString())
                setResult(RESULT_OK,intent)
                finish()

            }
            override fun onError(status: Status) {
                //Toast.makeText(applicationContext,"Some error occurred", Toast.LENGTH_SHORT).show()
                Log.d("키보드??","$status")
            }
        })
    }

}