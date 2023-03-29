package com.sele.mymunifaults.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.sele.mymunifaults.databinding.FragmentProfileBinding
import kotlinx.android.synthetic.main.activity_registration.*
import java.util.*


class ProfileFragment : Fragment(){
    private lateinit var databaseRef: DatabaseReference
    private lateinit var FirebaseAuth: FirebaseAuth
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        databaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mymunifaults-default-rtdb.firebaseio.com/")
        FirebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance()
        val userid: String = FirebaseAuth.getUid().toString()
        val UserRef = databaseRef.child("users").child(userid)
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val Name = dataSnapshot.child("name").getValue(String::class.java)
                val Surname = dataSnapshot.child("surname").getValue(String::class.java)
                val Email = dataSnapshot.child("email").getValue(String::class.java)
                val Number = dataSnapshot.child("number").getValue(String::class.java)
                Log.d("TAG", Name.toString())
                Log.d("TAG", Surname.toString())
                Log.d("TAG", Email.toString())
                Log.d("TAG", Number.toString())
                binding.profileNameEditT.setText(Name)
                binding.ProfSurnameEditT.setText(Surname)
                binding.profileEmailEditT.setText(Email)
                binding.profileNumberEditT.setText(Number)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("DB ERROR", databaseError.message) //Don't ignore errors!
            }
        }
        UserRef.addListenerForSingleValueEvent(valueEventListener)
        binding.Updatebutton.setOnClickListener {
            val email = binding.profileEmailEditT.text.toString()
            val number = binding.profileNumberEditT.text.toString()
            if (email.isNotEmpty() && number.isNotEmpty()) {
                val userid: String = FirebaseAuth.getUid().toString()
                val user = FirebaseAuth.currentUser
                user!!.updateEmail(email).addOnCompleteListener {
                    if (it.isSuccessful) {

                        databaseRef.child("users").child(userid).child("email").setValue(email)
                        databaseRef.child("users").child(userid).child("number").setValue(number)
                        Toast.makeText(activity, "Update successful", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

            }
            else{
                Toast.makeText(activity, " edit fields cannot be blank",Toast.LENGTH_SHORT).show()
            }


        }
        return binding.root
    }

}




