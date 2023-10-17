package com.vinhtm.selectitemrecyclerviewwithcharanphabet

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.vinhtm.selectitemrecyclerviewwithcharanphabet.R
import com.vinhtm.selectitemrecyclerviewwithcharanphabet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private lateinit var mAdapterAlbums: AlbumsAdapter
    private lateinit var mAdapterQuickScrollAlbums: QuickScrollAlbumsAdapter
    private var scrolledByUser = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this


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
                object : LinearSmoothScroller(this) {
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

            }

            override fun onClickCheckBox(position: Int) {
                //show option left
            }
        })

    }
}