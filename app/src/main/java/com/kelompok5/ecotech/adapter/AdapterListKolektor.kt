package com.kelompok5.ecotech.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.model.KolektorModel

class AdapterListKolektor(private val kolektorList: List<KolektorModel>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<AdapterListKolektor.KolektorViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(kolektor: KolektorModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KolektorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kolektor, parent, false)
        return KolektorViewHolder(view)
    }

    override fun onBindViewHolder(holder: KolektorViewHolder, position: Int) {
        val kolektor = kolektorList[position]
        holder.tvNamaKolektor.text = kolektor.nama
        holder.tvAlamatKolektor.text = kolektor.alamat
        holder.itemView.setOnClickListener {
            listener.onItemClick(kolektor)
        }
    }

    override fun getItemCount() = kolektorList.size

    inner class KolektorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNamaKolektor: TextView = itemView.findViewById(R.id.tvNamaKolektor)
        val tvAlamatKolektor: TextView = itemView.findViewById(R.id.tvAlamatKolektor)
    }
}
