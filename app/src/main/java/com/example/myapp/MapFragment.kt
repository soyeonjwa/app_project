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
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope

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
        googlemap = rootView.findViewById(R.id.mapView)
        googlemap?.onCreate(savedInstanceState);
        googlemap?.getMapAsync(this)
        // Inflate the layout for this fragment
        return rootView
    }


    override fun onMapReady(googleMap:GoogleMap) {
        Log.d("onMapReady 실행","onMapReady 실행")
   /*     val circle1KM = CircleOptions().center(currentLocation) //원점
            .radius(1000.0) //반지름 단위 : m
            .strokeWidth(0f) //선너비 0f : 선없음
            .fillColor(Color.parseColor("#880000ff")) //배경색*/



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