package com.map.trackmapapplication

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),OnMapReadyCallback {
    val PERMISSON_ALL= 1
    private val PERMISSIONS = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION)
    private  var mMap:GoogleMap?= null
    lateinit var mapView:MapView
    private val MAP_VIEW_BUNDLE_KEY="MapViewBundleKey"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mapView= findViewById<MapView>(R.id.mapView)
        var mapViewBundle:Bundle?= null
        if(savedInstanceState != null){
            mapViewBundle= savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
        }
        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this)
        if(!hasPermissions(this,PERMISSIONS)){
            ActivityCompat.requestPermissions(this,PERMISSIONS,PERMISSON_ALL)
        }
    }

    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean = permissions.all{
        ActivityCompat.checkSelfPermission(context,it) == PackageManager.PERMISSION_GRANTED
    }


    override fun onMapReady(googleMap: GoogleMap?) {
        mapView.onResume()
        mMap= googleMap
            if (ActivityCompat.checkSelfPermission(
                    this,Manifest.permission.ACCESS_FINE_LOCATION
            )!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
                return
            }
        mMap!!.isMyLocationEnabled= true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var mapViewBundle= outState.getBundle(MAP_VIEW_BUNDLE_KEY)
        if (mapViewBundle==null){
            mapViewBundle= Bundle()
            outState.putBundle(MAP_VIEW_BUNDLE_KEY,mapViewBundle)
        }
        mapView.onSaveInstanceState(mapViewBundle)
    }

}