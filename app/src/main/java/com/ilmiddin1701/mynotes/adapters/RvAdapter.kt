package com.ilmiddin1701.mynotes.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.ilmiddin1701.mynotes.databinding.ItemRvBinding
import com.ilmiddin1701.mynotes.models.GetNoteResponse

class RvAdapter(var rvAction: RvAction, var list: ArrayList<GetNoteResponse>): Adapter<RvAdapter.Vh>() {

    inner class Vh(private var itemRv: ItemRvBinding): RecyclerView.ViewHolder(itemRv.root){
        @SuppressLint("SetTextI18n")
        fun onBind(getNoteResponse: GetNoteResponse, position: Int) {
            itemRv.tvSarlavha.text = getNoteResponse.sarlavha
            itemRv.btnDelete.setOnClickListener {
                rvAction.deleteClick(getNoteResponse, position)
            }
            itemRv.root.setOnClickListener {
                rvAction.itemClick(getNoteResponse, position)
            }
            if (getNoteResponse.bajarildi) {
                itemRv.tvBajarildi.text = "Bajarilgan"
            } else {
                itemRv.tvBajarildi.text = "Bajarilmagan"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    interface RvAction {
        fun deleteClick(getNoteResponse: GetNoteResponse, position: Int)
        fun itemClick(getNoteResponse: GetNoteResponse, position: Int)
    }
}