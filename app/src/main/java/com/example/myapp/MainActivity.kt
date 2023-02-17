package com.example.myapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.myapp.board.BoardFragment
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    lateinit var providerClient: FusedLocationProviderClient
    lateinit var apiClient: GoogleApiClient
    private var latitude: Double?=null
    private var longitude: Double?=null

    private val homeFragment = MapFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("onCreate 실행","1")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){
            if(it.all{permission->permission.value == true}){
                apiClient.connect()
            }
            else{
                Toast.makeText(this,"권한 거부",Toast.LENGTH_SHORT).show()
            }}

        providerClient = LocationServices.getFusedLocationProviderClient(this)
        apiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            requestPermissionLauncher.launch(
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
            )
        }
        else{
            apiClient.connect()
        }


        // 하단 탭이 눌렸을 때 화면을 전환하기 위해선 이벤트 처리하기 위해 BottomNavigationView 객체 생성
        var bnv_main = findViewById(R.id.bnv_main) as BottomNavigationView

        // OnNavigationItemSelectedListener를 통해 탭 아이템 선택 시 이벤트를 처리
        // navi_menu.xml 에서 설정했던 각 아이템들의 id를 통해 알맞은 프래그먼트로 변경하게 한다.
        bnv_main.run { setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.first -> {
                    // 다른 행래그먼트 화면으로 이동하는 기능
                    // val boardFragment = ChattingFragment()
                    // supportFragmentManager.beginTransaction().replace(R.id.fl_container, boardFragment).commit()
                    val boardFragment = BoardFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fl_container, boardFragment).commit()

                    // Log.d("여기는 onCreate",currentLocation.latitude.toString()))
                    //val intent: Intent = Intent(context,LocationFinding::class.java)
                    //startActivity(intent)
                }
                R.id.second -> {
                    Log.d("1fragment 실행","1")
                    var bundle = Bundle()
                    Log.d("정보 보낼 준비 실행","$latitude+$longitude")
                    bundle.putDouble("latitude",latitude!!.toDouble())
                    bundle.putDouble("longitude",longitude!!.toDouble())
                    homeFragment.arguments = bundle
                    supportFragmentManager.beginTransaction().replace(R.id.fl_container, homeFragment).commit()

                }
                R.id.third -> {
                    val settingFragment = SettingFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fl_container, settingFragment).commit()
                }
                R.id.forth -> {
                    val settingFragment = SettingFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fl_container, settingFragment).commit()
                }
            }
            true
        }
            selectedItemId = R.id.first
        }



    }

    override fun onConnectionSuspended(p0: Int) {
        Log.d("onConnectionSuspended 실행","1")
        TODO("Not yet implemented")
    }


    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
        Log.d("연결 실패 실행","1")
    }

    override fun onConnected(p0: Bundle?) {
        Log.d("onConnected 실행", "1")

        // if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
        providerClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                latitude = location.latitude
                longitude = location.longitude
            }
        }
        //  }
    }
}

