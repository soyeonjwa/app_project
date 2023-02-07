package com.example.myapp

import android.app.Activity;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.Intent
import android.content.pm.PackageManager;
import android.graphics.Color
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.myapp.board.BoardRegister
import com.google.android.gms.common.api.Status

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class MapFragment : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters

    private var googlemap: MapView?=null
    private var currentLocation: LatLng?=null
    private var autocompleteSupportFragment: AutocompleteSupportFragment?=null
    private var currentLocationBtn:FloatingActionButton ?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("onCreate 실행","onCreate 실행")
        super.onCreate(savedInstanceState)

        currentLocation = LatLng(36.35,127.38)

        if(arguments!=null){
            Log.d("정보 받음 실행","1")
            val latitude: Double = arguments?.getDouble("latitude")!!.toDouble()
            val longitude: Double = arguments?.getDouble("longitude")!!.toDouble()

            currentLocation = LatLng(latitude,longitude)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("onCreateView 실행","onCreateView 실행")
        var rootView: View=inflater.inflate(R.layout.fragment_map, container, false)

        currentLocationBtn =rootView.findViewById(R.id.current_location_btn)

        googlemap = rootView.findViewById(R.id.mapView)
        googlemap?.onCreate(savedInstanceState);
        googlemap?.getMapAsync(this)
        // Inflate the layout for this fragment

        if (!Places.isInitialized()) {
            Places.initialize(this.context!!, "AIzaSyBRoZ4lT6rZJlAw72BB9hYtvYjOcr4GatQ")
        }
        val placesClient = Places.createClient(this.context!!)


        autocompleteSupportFragment = childFragmentManager.findFragmentById(R.id.autocomplete_fragment2) as AutocompleteSupportFragment?

        autocompleteSupportFragment?.setPlaceFields(
            listOf(
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG
            )
        )
        autocompleteSupportFragment?.setCountry("KR");

        return rootView
    }


    override fun onMapReady(googleMap:GoogleMap) {
        /*     val circle1KM = CircleOptions().center(currentLocation) //원점
                 .radius(1000.0) //반지름 단위 : m
                 .strokeWidth(0f) //선너비 0f : 선없음
                 .fillColor(Color.parseColor("#880000ff")) //배경색*/


        autocompleteSupportFragment?.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {

                val name = place.name
                val address = place.address
                val latlng = place.latLng

                Log.d("위치 검색 실행","Name: $name \nAddress: $address\nLatLng: $latlng")

                googleMap.addMarker(MarkerOptions().position(latlng!!).title(name))
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlng))
                googleMap.moveCamera(CameraUpdateFactory.zoomTo(15f))
            }
            override fun onError(status: Status) {
                Log.d("키보드??","$status")
            }
        })

        currentLocationBtn?.setOnClickListener{
            googleMap.addMarker(MarkerOptions().position(currentLocation!!).title("현재 위치"))
            // googleMap.addCircle(circle1KM)
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
            googleMap.moveCamera(CameraUpdateFactory.zoomTo(15f))
        }

        googleMap.addMarker(MarkerOptions().position(currentLocation!!).title("현재 위치"))
        // googleMap.addCircle(circle1KM)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15f))
    }


    override fun onStart() {
        super.onStart()
        googlemap?.onStart()
    }

    override fun onStop() {
        super.onStop()
        googlemap?.onStop()
    }

    override fun onResume() {
        super.onResume()
        googlemap?.onResume()
    }

    override fun onPause() {
        super.onPause()
        googlemap?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        googlemap?.onLowMemory()
    }

    override fun onDestroy() {
        googlemap?.onDestroy()
        super.onDestroy()
    }
}
