package com.kelompok5.ecotech.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.model.ItemLokasi
import java.util.ArrayList

class AdapterLokasi(private val mList: ArrayList<ItemLokasi>) : RecyclerView.Adapter<AdapterLokasi.ModelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterLokasi.ModelViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.content_location, parent,false)
        return ModelViewHolder(v)
    }


    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: AdapterLokasi.ModelViewHolder, position: Int) {
        val mItem = mList[position]
        holder.namatempat.text = mItem.nama
        holder.alamatTempat.text = mItem.alamat
    }

    inner class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namatempat: TextView = itemView.findViewById(R.id.nama_tempat)
        val alamatTempat: TextView = itemView.findViewById(R.id.alamat_tempat)
    }
}