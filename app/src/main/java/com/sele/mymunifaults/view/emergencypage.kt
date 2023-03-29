package com.sele.mymunifaults.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.net.Uri
import android.content.Intent
import com.sele.mymunifaults.R
import com.sele.mymunifaults.databinding.FragmentEmergencypageBinding


class emergencypage : Fragment() {
   private lateinit var binding: FragmentEmergencypageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentEmergencypageBinding.inflate(inflater, container, false)
        binding.btnPolice.setOnClickListener {
            val number = binding.policeNumber.text.toString()
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + number)
            startActivity(dialIntent)
        }

        binding.btnFire.setOnClickListener {
            val number = binding.fireNumber.text.toString()
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + number)
            startActivity(dialIntent)
        }
        binding.btnAmbulance.setOnClickListener {
            val number = binding.ambulanceNumber.text.toString()
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + number)
            startActivity(dialIntent)
        }

        binding.btnChildLine.setOnClickListener {
            val number = binding.ChildNumber.text.toString()
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + number)
            startActivity(dialIntent)
        }

        binding.btnCrimeStop.setOnClickListener {
            val number = binding.CrimeStopNumber.text.toString()
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + number)
            startActivity(dialIntent)
        }

        binding.btnGBV.setOnClickListener {
            val number = binding.gbvNumber.text.toString()
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + number)
            startActivity(dialIntent)
        }

        return binding.root
    }


}