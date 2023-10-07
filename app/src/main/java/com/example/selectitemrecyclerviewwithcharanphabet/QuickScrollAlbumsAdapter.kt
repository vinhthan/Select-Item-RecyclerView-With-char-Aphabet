package com.example.selectitemrecyclerviewwithcharanphabet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lge.usbvideo.databinding.QuickScrollItemBinding

class QuickScrollAlbumsAdapter: RecyclerView.Adapter<QuickScrollAlbumsAdapter.QuickScrollItemViewholder>() {

    var selectedPos = -1

    var onQuickScrollSelected: ((Char, Int) -> Unit)? = null
    inner class QuickScrollItemViewholder(val binding: QuickScrollItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    var dataList: List<Char> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuickScrollItemViewholder {
        val binding = QuickScrollItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuickScrollItemViewholder(binding)
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: QuickScrollItemViewholder, position: Int) {
        with(holder) {
            val char = dataList[position]
            this.binding.text.text = char.uppercaseChar().toString()
            if (position == dataList.size-1) {
                binding.dot.visibility  = View.GONE
            } else {
                binding.dot.visibility = View.VISIBLE
            }
            binding.text.isSelected = selectedPos == position
            binding.quickScrollItem.setOnClickListener {
                onQuickScrollSelected?.invoke(char, position)
            }
        }
    }
}