package com.sele.mymunifaults
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import android.location.Geocoder
import android.renderscript.ScriptGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.sele.mymunifaults.databinding.ActivityUserlocationBinding
import com.sele.mymunifaults.view.electricitypage
import com.sele.mymunifaults.view.otherServicesPage
import java.util.*

class userlocation : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityUserlocationBinding
    private lateinit var lastLocation:Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    //var navController: NavController? = null

    companion object {
        const val LOCATION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserlocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        setUpMap()

    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE)
            return
        }
        mMap.isMyLocationEnabled = true

        fusedLocationProviderClient.lastLocation.addOnSuccessListener(this) {
                location ->
            if (location !=null)
            {
                lastLocation = location
                val currentLatLong = LatLng(location.latitude,location.longitude)
                placeMarkerOnMap(currentLatLong)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong,12f))


            }
        }
    }

    private fun placeMarkerOnMap(currentLatLong: LatLng) {
        val markerOptions = MarkerOptions().position(currentLatLong).snippet(getAdress())
        markerOptions.title("$currentLatLong")
        binding.userAddress.setText(getAdress().toString())
        mMap.addMarker(markerOptions)


    }

   private fun getAdress(): String {
       val geoCoder = Geocoder(this, Locale.getDefault())
        val adress = geoCoder.getFromLocation(lastLocation.latitude,lastLocation.longitude,1)
        return  adress[0].getAddressLine(0)
    }

    fun onMarkerClick(p0: Marker?): Boolean =true

}








