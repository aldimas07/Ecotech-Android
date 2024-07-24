package com.kelompok5.ecotech.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.adapter.AdapterListPenyetorByKolektorId.OnItemClickListener
import com.kelompok5.ecotech.data.model.response.orders.orderDataByID
import com.kelompok5.ecotech.data.model.response.orders.orderDataByIDPenyetor

class AdapterRiwayatOrder(
    private var kolektorlistforpenyetor: MutableList<orderDataByIDPenyetor>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<AdapterRiwayatOrder.KolektorViewHolder>(){

    interface OnItemClickListener {
        fun onItemClick(kolektor: orderDataByIDPenyetor)
    }

    inner class KolektorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        private val tvNamaPenyetor : TextView = itemView.findViewById(R.id.tvNamaPenyetor)
        private val tvStatus : TextView = itemView.findViewById(R.id.tvStatus)
        init {
            itemView.setOnClickListener(this)
        }

        fun bind(kolektor: orderDataByIDPenyetor) {
            tvNamaPenyetor.text = "Nama Kolektor: ${kolektor.kolektor_name}" //kolektor_name
            tvStatus.text = "Status: ${kolektor.status}"
        }

        override fun onClick(v: View?) {
            itemClickListener.onItemClick(kolektorlistforpenyetor[adapterPosition])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KolektorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return KolektorViewHolder(view)
    }

    override fun onBindViewHolder(holder: KolektorViewHolder, position: Int) {
        holder.bind(kolektorlistforpenyetor[position])
    }

    override fun getItemCount(): Int {
        return kolektorlistforpenyetor.size
    }

    fun updateData(newKolektorList: List<orderDataByIDPenyetor>) {
        Log.d("AdapterListKolektor", "Updating data with ${newKolektorList.size} items")
        this.kolektorlistforpenyetor.clear()
        this.kolektorlistforpenyetor.addAll(newKolektorList)
        notifyDataSetChanged()
    }

}