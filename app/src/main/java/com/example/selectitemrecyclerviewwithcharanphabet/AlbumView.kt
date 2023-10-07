package com.example.selectitemrecyclerviewwithcharanphabet

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.selectitemrecyclerviewwithcharanphabet.databinding.LayoutAlbumsViewBinding

class AlbumView(context: Context, attributeSet: AttributeSet? = null) :
    BaseCustomView<LayoutAlbumsViewBinding>(context, attributeSet) {

    private lateinit var mAdapterAlbums: AlbumsAdapter
    private lateinit var mAdapterQuickScrollAlbums: QuickScrollAlbumsAdapter
    private var scrolledByUser = false

    companion object {
        var isSelectCheckBox = false
    }

    override fun layoutRes(): Int = R.layout.layout_albums_view

    override fun initView() {
        showAlbumsList()
    }


    private fun showAlbumsList() {
        mAdapterAlbums = AlbumsAdapter()
        mAdapterQuickScrollAlbums = QuickScrollAlbumsAdapter()
        binding.rvAlbums.adapter = mAdapterAlbums
        binding.rvQuickScrollAlbums.adapter = mAdapterQuickScrollAlbums

        mAdapterAlbums = AlbumsAdapter()
        mAdapterAlbums.listAlbums.add(MusicEntity(album = "A Album 1", videoUri = Uri.parse("abc")))
        mAdapterAlbums.listAlbums.add(MusicEntity(album = "C Album  1", videoUri = Uri.parse("abc")))
        mAdapterAlbums.listAlbums.add(MusicEntity(album = "E Album  1", videoUri = Uri.parse("abc")))
        mAdapterAlbums.listAlbums.add(MusicEntity(album = "G Album  1", videoUri = Uri.parse("abc")))
        mAdapterAlbums.listAlbums.add(MusicEntity(album = "I Album  1", videoUri = Uri.parse("abc")))
        mAdapterAlbums.listAlbums.add(MusicEntity(album = "K Album  1", videoUri = Uri.parse("abc")))
        mAdapterAlbums.listAlbums.add(MusicEntity(album = "L Album  1", videoUri = Uri.parse("abc")))
        mAdapterAlbums.listAlbums.add(MusicEntity(album = "M Album  1", videoUri = Uri.parse("abc")))
        mAdapterAlbums.listAlbums.add(MusicEntity(album = "P Album  1", videoUri = Uri.parse("abc")))
        mAdapterAlbums.listAlbums.add(MusicEntity(album = "Y Album  1", videoUri = Uri.parse("abc")))
        mAdapterAlbums.listAlbums.add(MusicEntity(album = "Z Album  1", videoUri = Uri.parse("abc")))
        mAdapterAlbums.listAlbums.sortBy {
            it.album
        }
        mAdapterAlbums.setHasStableIds(true)
        binding.rvAlbums.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            hasFixedSize()
            adapter = mAdapterAlbums
        }

        val charSet: MutableSet<Char> = mutableSetOf()
        for (music in mAdapterAlbums.listAlbums) {
            charSet.add(music.album!![0])
        }
        mAdapterQuickScrollAlbums.dataList = charSet.toList()
        mAdapterQuickScrollAlbums.onQuickScrollSelected = { char, position ->
            val oldSelectedPos = mAdapterQuickScrollAlbums.selectedPos
            mAdapterQuickScrollAlbums.selectedPos = position
            mAdapterQuickScrollAlbums.notifyItemChanged(oldSelectedPos)
            mAdapterQuickScrollAlbums.notifyItemChanged(position)
            val songPos = mAdapterAlbums.listAlbums.indexOfFirst { it.album!![0] == char }
            val smoothScroller: RecyclerView.SmoothScroller =
                object : LinearSmoothScroller(context) {
                    override fun getVerticalSnapPreference(): Int {
                        return SNAP_TO_START
                    }
                }
            smoothScroller.targetPosition = songPos
            binding.rvAlbums.layoutManager?.startSmoothScroll(smoothScroller)
        }
        binding.rvAlbums.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int
            ) {
                when (newState) {
                    RecyclerView.SCROLL_STATE_DRAGGING -> {
                        scrolledByUser = true
                    }

                    RecyclerView.SCROLL_STATE_IDLE -> {
                        if (scrolledByUser) {
                            val layoutManager =
                                binding.rvAlbums.layoutManager as LinearLayoutManager
                            val scrollPos = layoutManager.findFirstVisibleItemPosition()
                            val charPos =
                                mAdapterQuickScrollAlbums.dataList.indexOfFirst { it == mAdapterAlbums.listAlbums[scrollPos].album!![0] }
                            val oldSelectedPos = mAdapterQuickScrollAlbums.selectedPos
                            mAdapterQuickScrollAlbums.selectedPos = charPos
                            mAdapterQuickScrollAlbums.notifyItemChanged(oldSelectedPos)
                            mAdapterQuickScrollAlbums.notifyItemChanged(charPos)
                        }
                        scrolledByUser = false
                    }
                }
            }
        })

        mAdapterAlbums.setOnClickItem(object : AlbumsAdapter.ItemOnClickAlbumsList {
            override fun onclickItem(position: Int) {
                isSelectCheckBox = false
                binding.layoutAlbums.visibility = View.GONE
                binding.layoutInsideAlbums.visibility = View.VISIBLE
                binding.tvAlbumTitleInside.text = mAdapterAlbums.listAlbums[position].album
            }

            override fun onClickCheckBox(position: Int) {
                //show option left
                /*binding.tabList.visibility = View.GONE
                binding.tabSelected.visibility = View.VISIBLE*/
                isSelectCheckBox = true

            }
        })

        binding.imgBackAlbumInside.setOnClickListener {
            binding.layoutAlbums.visibility = View.VISIBLE
            binding.layoutInsideAlbums.visibility = View.GONE
        }

    }

}