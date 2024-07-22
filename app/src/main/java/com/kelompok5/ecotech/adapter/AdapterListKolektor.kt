package com.kelompok5.ecotech.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.data.model.response.kolektor.allKolektor

class AdapterListKolektor(
    private var kolektorList: MutableList<allKolektor>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<AdapterListKolektor.KolektorViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(kolektor: allKolektor)
    }

    inner class KolektorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        fun bind(kolektor: allKolektor) {
            val tvNamaKolektor: TextView = itemView.findViewById(R.id.tvNamaKolektor)
            val tvAlamatKolektor: TextView = itemView.findViewById(R.id.tvAlamatKolektor)
            tvNamaKolektor.text = kolektor.name
            tvAlamatKolektor.text = kolektor.alamat
        }

        override fun onClick(v: View?) {
            itemClickListener.onItemClick(kolektorList[adapterPosition])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KolektorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kolektor, parent, false)
        return KolektorViewHolder(view)
    }

    override fun onBindViewHolder(holder: KolektorViewHolder, position: Int) {
        holder.bind(kolektorList[position])
    }

    override fun getItemCount(): Int {
        return kolektorList.size
    }

    fun updateData(newKolektorList: List<allKolektor>) {
        Log.d("AdapterListKolektor", "Updating data with ${newKolektorList.size} items")
        this.kolektorList.clear()
        this.kolektorList.addAll(newKolektorList)
        notifyDataSetChanged()
    }
}
