package com.vinhtm.selectitemrecyclerviewwithcharanphabet

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

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

        //holder.albumsCard.isSelected = position == selectedPos

        if (position == selectedPos) {

        } else {

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

    }

    class AlbumsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvAlbumsName = itemView.findViewById<TextView>(R.id.tv_song_name)
        fun bind(musicEntity: MusicEntity) {
            tvAlbumsName.text = musicEntity.album

        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}