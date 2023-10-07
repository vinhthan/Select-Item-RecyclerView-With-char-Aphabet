package com.example.selectitemrecyclerviewwithcharanphabet

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView

class AlbumsAdapter: RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder>() {
    var listAlbums = mutableListOf<MusicEntity>()

    var selectedPos = -1
    var isMarkAll: Boolean? = false

    interface ItemOnClickAlbumsList{
        fun onclickItem(position: Int)
        fun onClickCheckBox(position: Int)
    }

    private var itemOnClickAlbumsList: ItemOnClickAlbumsList? = null

    fun setOnClickItem(onclick: ItemOnClickAlbumsList) {
        itemOnClickAlbumsList = onclick
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMarkAll(mark: Boolean) {
        isMarkAll = mark
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_albums, parent, false)
        return AlbumsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listAlbums.size
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.bind(listAlbums[position])
        listAlbums[position].isCheck = isMarkAll

        holder.albumsCard.isSelected = position == selectedPos

        if (position == selectedPos) {
            /*holder.tvSongTitle.setTypeface(holder.tvSongTitle.typeface, Typeface.BOLD)
            holder.imgBgSelected.visibility = View.VISIBLE*/
            holder.layoutStatesItemAblums.visibility = View.VISIBLE
            holder.imgBgStatesItemAlbums.visibility = View.VISIBLE

        } else {
            /*holder.tvSongTitle.setTypeface(holder.tvSongTitle.typeface, Typeface.NORMAL)
            holder.imgBgSelected.visibility = View.GONE*/
            holder.layoutStatesItemAblums.visibility = View.GONE
            holder.imgBgStatesItemAlbums.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            val positions = position
            if (positions != RecyclerView.NO_POSITION) {
                notifyItemChanged(selectedPos)
                selectedPos = position
                notifyItemChanged(selectedPos)
            }
            itemOnClickAlbumsList?.onclickItem(position)
        }

        if (listAlbums[position].isCheck == true) {
            holder.checkBoxAlbums.setImageResource(R.drawable.ico_check)
        } else {
            holder.checkBoxAlbums.setImageResource(R.drawable.bg_checkbox)
        }

        holder.checkBoxAlbums.setOnClickListener {
            itemOnClickAlbumsList?.onClickCheckBox(position)
            if (listAlbums[position].isCheck == false) {
                listAlbums[position].isCheck = true
                holder.checkBoxAlbums.setImageResource(R.drawable.ico_check)
            } else {
                listAlbums[position].isCheck = false
                holder.checkBoxAlbums.setImageResource(R.drawable.bg_checkbox)
            }

        }
    }

    class AlbumsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvAlbumsName = itemView.findViewById<TextView>(R.id.tv_albums_name)
        var imgAlbums = itemView.findViewById<ImageView>(R.id.img_albums)
        var checkBoxAlbums = itemView.findViewById<ImageView>(R.id.checkbox_albums)
        var albumsCard = itemView.findViewById<MaterialCardView>(R.id.albums_card)
        var layoutStatesItemAblums = itemView.findViewById<ConstraintLayout>(R.id.layout_states_item_albums)
        var imgBgStatesItemAlbums = itemView.findViewById<ImageView>(R.id.img_bg_states_item_albums)
        fun bind(musicEntity: MusicEntity) {
            tvAlbumsName.text = musicEntity.album

            if (musicEntity.videoUri != null) {
                if (bindingAdapterPosition % 2 == 0) {
                    Glide.with(itemView).load("").error(R.drawable.img_fake_thumbnail_1).into(imgAlbums)
                } else {
                    Glide.with(itemView).load("").error(R.drawable.img_fake_thumbnail_2).into(imgAlbums)
                }
            }

        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}