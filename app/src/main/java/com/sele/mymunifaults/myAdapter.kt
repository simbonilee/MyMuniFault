package com.sele.mymunifaults

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class myAdapter(private val faultList: ArrayList<fault>):RecyclerView.Adapter<myAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val itemView = LayoutInflater.from(parent.context).inflate(R.layout.faultitems,parent,false)
        return MyViewHolder(itemView)


    }

    override fun getItemCount(): Int {
        return faultList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem =faultList[position]
        holder.Category.text =currentitem.Category
        holder.Location.text =currentitem.Location
        holder.Description.text =currentitem.Description
        Picasso.get().load(currentitem.FaultImageURI).into(holder.Image)


    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val Category:TextView = itemView.findViewById(R.id.fault)
        val Location:TextView = itemView.findViewById(R.id.fault_adress)
        val Description : TextView = itemView.findViewById(R.id.fault_descipt)
        val Image :ImageView = itemView.findViewById(R.id.fault_image)
    }
}