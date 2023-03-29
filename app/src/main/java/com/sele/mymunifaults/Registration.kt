package com.sele.mymunifaults

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.sele.mymunifaults.databinding.ActivityRegistrationBinding
import java.util.*


class Registration : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var FirebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var muniSugestions = arrayOf("Mandela Bay","Matatiel", "Mnquma","Mbashe","Nquza hill","Emalahleni","Bufallo city","Walter Sisulu","Ngqushwa")
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,muniSugestions)
        val textView = findViewById<View>(R.id.MunicipalEditT) as AutoCompleteTextView
        textView.setAdapter(adapter)
        FirebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mymunifaults-default-rtdb.firebaseio.com/")
        binding.RegContinue.setOnClickListener {
            val email = binding.regEmailEditT.text.toString()
            val password = binding.RegPasswordEditT.text.toString()
            val number = binding.NumberEditT.text.toString()
            val name = binding.NameEditT.text.toString()
            val municipality = binding.MunicipalEditT.text.toString()
            val surname= binding.SurnameEditT.text.toString()
               if (email.isNotEmpty()&&password.isNotEmpty()&&surname.isNotEmpty()&&name.isNotEmpty()&&number.isNotEmpty()&&municipality.isNotEmpty() ) {
                   FirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                       if (it.isSuccessful) {
                           val userid: String = FirebaseAuth.getUid().toString()
                           database.child("users").child(userid).child("id").setValue(FirebaseAuth.getUid())
                           database.child("users").child(userid).child("email").setValue(email)
                           database.child("users").child(userid).child("name").setValue(name)
                           database.child("users").child(userid).child("surname").setValue(surname)
                           database.child("users").child(userid).child("number").setValue(number)
                           database.child("users").child(userid).child("municipality").setValue(municipality)

                               val intent = Intent(this, main_page::class.java)
                               startActivity(intent)
                           } else {
                               Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT)
                                   .show()
                           }
                       }
                   }
               else{
                       Toast.makeText(this, "Add your information!! edit texts cannot be blank",Toast.LENGTH_SHORT).show()
                   }


            }

        }
    }