package com.sele.mymunifaults.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.sele.mymunifaults.R
import com.sele.mymunifaults.databinding.FragmentElectricitypageBinding
import com.sele.mymunifaults.databinding.FragmentRoadspageBinding
import com.sele.mymunifaults.userlocation
import java.util.*


class roadspage : Fragment() {

    lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var FirebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var binding: FragmentRoadspageBinding
    lateinit var ImageUri: Uri
    lateinit var category: String
    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        FirebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mymunifaults-default-rtdb.firebaseio.com/")
        binding = FragmentRoadspageBinding.inflate(inflater, container, false)
        var faultcategories = arrayOf("Potholes","Blocked roads", "Road markings","Damaged road","Road signs","speed hump","Tar","gravel road","other")
        var adapter = activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, faultcategories) }
        val textView : AutoCompleteTextView = binding.CategoryEditT
        textView.setAdapter(adapter)
        binding.seecurrentlocation.setOnClickListener {
            if(binding.seecurrentlocation.isChecked){
                val intent = Intent(activity, userlocation::class.java)
                startActivity(intent)

            }
        }
        binding.uselocation.setOnClickListener {
            if (binding.uselocation.isChecked) {
                if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                    getLocation()
                }
                else
                {
                    Toast.makeText(activity,"location permision is not granted",Toast.LENGTH_SHORT).show()
                }
            }
        }
        fusedLocationClient = getActivity()?.let {
            LocationServices.getFusedLocationProviderClient(
                it
            )
        }!!
        addphoto()

        binding.Roadsbtnfaultreport.setOnClickListener {
            val category = binding.CategoryEditT.text.toString()
            val location = binding.addressEditT.text.toString()
            val description= binding.faultDescriptionEditT.text.toString()
            // val image:String = ImageUri.toString()
            val userid: String = FirebaseAuth.getUid().toString()
            if (category.isNotEmpty()&&location.isNotEmpty()&&description.isNotEmpty() ){
                database.child("users").child(userid).child("municipality").child("RoadsFAULT").child("Category").setValue(category)
                database.child("users").child(userid).child("municipality").child("RoadsFAULT").child("Location").setValue(location)
                database.child("users").child(userid).child("municipality").child("RoadsFAULT").child("Description").setValue(description)
                //database.child("users").child(userid).child("municipality").child("FAULTS").child("FaultImageURI").setValue(image)
                uploadImage()
                Toast.makeText(activity, "Report sent successfully!!",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(activity, "Add report information!!",Toast.LENGTH_SHORT).show()
            }

        }

        return binding.root
    }
    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            var latitude = location?.latitude
            var longitude = location?.longitude
            val geoCoder = Geocoder(getContext(), Locale.getDefault())
            val adress = latitude?.let { longitude?.let { it1 ->
                geoCoder.getFromLocation(it,
                    it1,1)
            } }
            val current_address: String? = adress?.get(0)?.getAddressLine(0)
            binding.addressEditT.setText(current_address)
        }
    }
    private fun addphoto() {
        val pickphoto = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                ImageUri = it!!
                binding.image.setImageURI(ImageUri)
            })
        binding.Btnaddimage.setOnClickListener {
            if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                pickphoto.launch("image/*")
            }
            else
            {
                Toast.makeText(activity,"Read permision is not granted", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun uploadImage() {

        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://mymunifaults.appspot.com/")
        val userid: String = FirebaseAuth.getUid().toString()
        database.child("users").child(userid).child("municipality").child("RoadsFAULT").child("FaultImageURI").setValue(ImageUri.toString())
        val faultwords = category.split("[,.!?\\s]+".toRegex())
        val imagename = faultwords[1]

        storageReference.child("ReportImages/"+imagename+".jpg").putFile(ImageUri).addOnSuccessListener {
        }
            .addOnFailureListener{
                Toast.makeText(activity,"Image upload failed",Toast.LENGTH_SHORT).show()
            }
    }

}