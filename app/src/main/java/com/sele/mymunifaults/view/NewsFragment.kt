package com.sele.mymunifaults.view

import android.content.Intent
import android.os.Bundle
import android.text.method.MovementMethod
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.sele.mymunifaults.R
import com.sele.mymunifaults.databinding.FragmentNewsBinding
import com.sele.mymunifaults.databinding.FragmentWaterpageBinding
import com.sele.mymunifaults.main_page

class NewsFragment : Fragment() {
    private lateinit var binding:FragmentNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        binding.articleBodyone.movementMethod =ScrollingMovementMethod()
        binding.articleBodytwo.movementMethod =ScrollingMovementMethod()
        binding.articleBodythree.movementMethod =ScrollingMovementMethod()
        binding.articleBodyfour.movementMethod =ScrollingMovementMethod()


        return binding.root

    }

}


