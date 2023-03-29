package com.sele.mymunifaults.view
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.sele.mymunifaults.R
import com.sele.mymunifaults.main_page

class HomeFragment : Fragment(),View.OnClickListener {
    var navController: NavController? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.btnEmergency).setOnClickListener(this)
        view.findViewById<Button>(R.id.btnElectricity).setOnClickListener(this)
        view.findViewById<Button>(R.id.btnWater).setOnClickListener(this)
        view.findViewById<Button>(R.id.btnRoad).setOnClickListener(this)
        view.findViewById<Button>(R.id.btnWasteManagement).setOnClickListener(this)
        view.findViewById<Button>(R.id.btntraffic).setOnClickListener(this)
        view.findViewById<Button>(R.id.btnHousing).setOnClickListener(this)
        view.findViewById<Button>(R.id.btnOtherServices).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnEmergency -> navController!!.navigate(R.id.action_Home_to_emergencypage2)
            R.id.btnElectricity -> navController!!.navigate(R.id.action_Home_to_electricitypage2)
            R.id.btnWater-> navController!!.navigate(R.id.action_Home_to_waterpage)
            R.id.btnRoad -> navController!!.navigate(R.id.action_Home_to_roadspage)
            R.id.btnHousing-> navController!!.navigate(R.id.action_Home_to_housingPage)
            R.id.btnWasteManagement -> navController!!.navigate(R.id.action_Home_to_wastePage)
            R.id.btntraffic -> navController!!.navigate(R.id.action_Home_to_trafificPage)
            R.id.btnOtherServices -> navController!!.navigate(R.id.action_Home_to_otherServicesPage)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}