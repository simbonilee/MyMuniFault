package com.sele.mymunifaults

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.sele.mymunifaults.databinding.ActivityForgotPasswordBinding
import com.sele.mymunifaults.databinding.ActivityLoginInterfaceBinding

class forgot_password : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var FirebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance()


        binding.BtnReset.setOnClickListener {
            val email = binding.forgotPassET.text.toString()
            if (email.isNotEmpty() ) {
                FirebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "please check your emails!!", Toast.LENGTH_SHORT).show()
                        //val intent = Intent(this, login_interface::class.java)
                        //startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                }
            else {
                Toast.makeText(this, "Email field can not be empty!!", Toast.LENGTH_SHORT).show()
            }
            }
            }
    }



            //.addOnFailureListener {
             //   Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()


