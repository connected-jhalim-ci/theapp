package com.connected.theapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.connected.theapp.databinding.ItemUserBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.VH>() {

    private var data: List<SearchData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_user,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        with(holder.binding) {
            login.text = data[position].title
            avatar.contentDescription = data[position].imageUrl
        }
    }

    fun updateData(newData: List<SearchData>) {
        data = newData
        notifyDataSetChanged()
    }

    class VH(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

}
