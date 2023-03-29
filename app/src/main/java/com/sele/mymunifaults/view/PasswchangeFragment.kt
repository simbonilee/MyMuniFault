package com.sele.mymunifaults.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.sele.mymunifaults.R
import com.sele.mymunifaults.databinding.FragmentPasswchangeBinding
import com.sele.mymunifaults.databinding.FragmentProfileBinding
import com.sele.mymunifaults.main_page


class PasswchangeFragment : Fragment()  {
    private lateinit var FirebaseAuth: FirebaseAuth
    private lateinit var binding: FragmentPasswchangeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        FirebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance()
        binding = FragmentPasswchangeBinding.inflate(inflater, container, false)
        binding.BtnUpdate.setOnClickListener{
            val passwordone = binding.passOneEditT.text.toString()
            val passwordtwo = binding.passTwoEditT.text.toString()
            val user = FirebaseAuth.currentUser
            if (passwordone.isNotEmpty() && passwordtwo.isNotEmpty()) {
                if (passwordone == passwordtwo) {
                    user!!.updatePassword(passwordone).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(activity, "Update successful", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(activity, it.exception.toString(), Toast.LENGTH_SHORT).show()
                         }
                        }
                      }
                else {
                    Toast.makeText(activity, "passwords don't match", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(activity, "Edit fields can not be empty!!", Toast.LENGTH_SHORT).show()
            }

        }


        return binding.root
    }



}