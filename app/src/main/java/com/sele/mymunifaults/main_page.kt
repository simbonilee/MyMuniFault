package com.sele.mymunifaults

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.Manifest
import android.os.Looper
import android.os.Build
import android.content.Intent
import android.content.pm.PackageManager
import android.app.AlertDialog
import android.content.Context
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import android.os.Bundle
import android.provider.Settings
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import android.net.Uri
import android.view.MenuItem
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.location.*
import com.google.firebase.database.*


class main_page : AppCompatActivity() {

    private lateinit var FirebaseAuth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var permisionLauncer: ActivityResultLauncher<Array<String>>
    private var isReadpermisionGranted = false
    private var isLocationGranted = false


    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        val drawer_layout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val menu_icon = findViewById<ImageView>(R.id.menu_icon)


        val nav_drawer = findViewById<NavigationView>(R.id.nav_drawer)
        nav_drawer.itemIconTintList = null
        menu_icon.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }
        val navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupWithNavController(nav_drawer, navController)
        val textTitle = findViewById<TextView>(R.id.menu_txt)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            textTitle.text = destination.label
        }


        val navigationView: NavigationView = findViewById(R.id.nav_drawer)
        val header: View = navigationView.getHeaderView(0)
        val navname: TextView = header.findViewById(R.id.navName)
        val namemail: TextView = header.findViewById(R.id.navEmail)
        databaseRef = FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://mymunifaults-default-rtdb.firebaseio.com/")
        FirebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance()
        val userid: String = FirebaseAuth.getUid().toString()
        val UserRef = databaseRef.child("users").child(userid)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val Name = dataSnapshot.child("name").getValue(String::class.java)
                val Surname = dataSnapshot.child("surname").getValue(String::class.java)
                val Email = dataSnapshot.child("email").getValue(String::class.java)
                Log.d("TAG", Email.toString())
                Log.d("TAG", Name.toString())
                Log.d("TAG", Surname.toString())
                navname.setText(Name + " " + Surname)
                namemail.setText(Email)

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("DB ERROR", databaseError.message) //Don't ignore errors!
            }
        }
        UserRef.addListenerForSingleValueEvent(valueEventListener)
        permisionLauncer = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
                permissions->
               isReadpermisionGranted=permissions[Manifest.permission.READ_EXTERNAL_STORAGE]?:isReadpermisionGranted
               isLocationGranted =permissions[Manifest.permission.READ_EXTERNAL_STORAGE]?:isLocationGranted
        }
        requestPermision()

    }


        private fun requestPermision(){
           val isReadpermision = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED
            val isLocationpermision = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
            isReadpermisionGranted = isReadpermision
            isLocationGranted = isLocationpermision

            val permissionRequest: MutableList<String> = ArrayList()
            if(!isReadpermisionGranted){
                permissionRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            if(!isLocationGranted){
                permissionRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
            }

            if(permissionRequest.isNotEmpty()){
                permisionLauncer.launch(permissionRequest.toTypedArray())
            }
        }

    }







