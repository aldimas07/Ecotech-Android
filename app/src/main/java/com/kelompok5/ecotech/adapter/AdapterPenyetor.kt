package com.kelompok5.ecotech.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.model.DataItem

class AdapterPenyetor(private val dataList: List<DataItem>) :
        RecyclerView.Adapter<AdapterPenyetor.ViewHolder>() {
            class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                val tvNamaPenyetor: TextView = itemView.findViewById(R.id.tvNamaPenyetor)
                val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.tvNamaPenyetor.text = "Nama Penyetor: ${item.namaPenyetor}"
        holder.tvStatus.text = "Status: ${item.status}"
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}