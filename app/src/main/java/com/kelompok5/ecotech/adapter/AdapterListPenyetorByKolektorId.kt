package com.kelompok5.ecotech.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.data.model.response.kolektor.allKolektor
import com.kelompok5.ecotech.data.model.response.orders.orderDataByID

class AdapterListPenyetorByKolektorId(
    private var penyetorList: MutableList<orderDataByID>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<AdapterListPenyetorByKolektorId.PenyetorViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(penyetor: orderDataByID)
    }

    inner class PenyetorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        fun bind(penyetor: orderDataByID) {
            val tvNamaPenyetor: TextView = itemView.findViewById(R.id.tvNamaPenyetor)
            val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
            tvNamaPenyetor.text = penyetor.penyetor_name
            tvStatus.text = penyetor.status
        }

        override fun onClick(v: View?) {
            itemClickListener.onItemClick(penyetorList[adapterPosition])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PenyetorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return PenyetorViewHolder(view)
    }

    override fun onBindViewHolder(holder: PenyetorViewHolder, position: Int) {
        holder.bind(penyetorList[position])
    }

    override fun getItemCount(): Int {
        return penyetorList.size
    }

    fun updateData(newKolektorList: List<orderDataByID>) {
        Log.d("AdapterListKolektor", "Updating data with ${newKolektorList.size} items")
        this.penyetorList.clear()
        this.penyetorList.addAll(newKolektorList)
        notifyDataSetChanged()
    }
}