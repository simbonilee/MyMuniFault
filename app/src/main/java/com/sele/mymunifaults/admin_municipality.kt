package com.sele.mymunifaults

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_admin_municipality.*

class admin_municipality : AppCompatActivity() {
    private lateinit var databaseRef: DatabaseReference
    private lateinit var FirebaseAuth: FirebaseAuth
    lateinit var logoutIcon: ImageView
  //  private lateinit var binding: ActivityAdminMunicipalityBinding
   // lateinit var ImageUri: Uri

    private lateinit var recyclerView: RecyclerView
    private lateinit var faultList: ArrayList<fault>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_municipality)
        recyclerView = findViewById(R.id.faultlist)
        logoutIcon = findViewById(R.id.Logout_icon)
        logoutIcon.setOnClickListener{
            val intent = Intent(this, login_interface::class.java)
            startActivity(intent)}
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        faultList = arrayListOf<fault>()
        getUserdata()
        FirebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance()

    }

    private fun getUserdata() {
        databaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mymunifaults-default-rtdb.firebaseio.com/")
        val UserRef = databaseRef.child("faults")
        UserRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    for(faultSnapshot in dataSnapshot.children ){
                        val fault = faultSnapshot.getValue(fault::class.java)
                        faultList.add(fault!!)
                    }
                    recyclerView.adapter = myAdapter(faultList)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}