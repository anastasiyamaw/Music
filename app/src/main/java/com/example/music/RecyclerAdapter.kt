package com.example.music

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.music.databinding.PlaylistBinding

class RecyclerAdapter(private var songs: ArrayList<String>,
                      private var authors: ArrayList<String>,
                      private val cellClickListener: CellClickListener
):
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private val images = arrayOf(R.drawable.image, R.drawable.image, R.drawable.image,
        R.drawable.image, R.drawable.image, R.drawable.image,
        R.drawable.image, R.drawable.image)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.playlist, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount():Int{
        return songs.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val song = songs[position]
        val author = authors[position]
        holder.itemSong.text = song
        holder.itemAuthor.text = author
        holder.itemImage.setImageResource(images[position])

        holder.itemSong.setOnClickListener{
            cellClickListener.onCellClickListener(position)
        }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val binding = PlaylistBinding.bind(itemView)

        var itemImage: ImageView
        var itemSong: TextView
        var itemAuthor: TextView

        init {
            itemImage = binding.image
            itemSong = binding.SongName
            itemAuthor = binding.Author
        }
    }
}