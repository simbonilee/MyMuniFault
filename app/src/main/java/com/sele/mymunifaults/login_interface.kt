package com.sele.mymunifaults

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.IdRes
import com.google.firebase.auth.FirebaseAuth
import com.sele.mymunifaults.databinding.ActivityLoginInterfaceBinding
import java.net.PasswordAuthentication

class login_interface : AppCompatActivity() {


    private lateinit var binding: ActivityLoginInterfaceBinding
    private lateinit var FirebaseAuth: FirebaseAuth
    lateinit var municipality_admin: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginInterfaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance()
        binding.Regbtn.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }
        binding.forgotpassword.setOnClickListener {
            val intent = Intent(this, forgot_password::class.java)
            startActivity(intent)
        }
        binding.lgnBtn.setOnClickListener {
            var email = binding.EmailEditT.text.toString()
            var password = binding.passwordEditT.text.toString()
            municipality_admin = "admin.municipality@gmail.com"
            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                        if (it.isSuccessful) {
                           if (email == municipality_admin){
                                val intent = Intent(this, admin_municipality::class.java)
                                startActivity(intent)
                                Toast.makeText(this, "municipality admin", Toast.LENGTH_SHORT).show()
                           }
                            else{
                            val intent = Intent(this, main_page::class.java)
                            startActivity(intent)
                            }
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            } else {
                Toast.makeText(this, "Edit fields can not be empty!!", Toast.LENGTH_SHORT).show()
            }

        }

    }

}


