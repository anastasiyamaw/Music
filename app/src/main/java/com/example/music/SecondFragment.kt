package com.example.music

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.music.databinding.FragmentSecondBinding


class SecondFragment : Fragment(), CellClickListener {

    private lateinit var binding: FragmentSecondBinding

    val songs = arrayListOf(
        "Ной", "Money and the power", "Saving Me", "Unity",
        "Pieces", "The high road", "Leave a light on", "Сеть"
    )

    val authors = arrayListOf(
        "ATL", "Kid Ink", "Nickelback", "Shinedown",
        "Sum 41", "Three Days Grace", "Tom Walker", "Макс Корж"
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSecondBinding.inflate(inflater, container, false)


        val layoutManager: RecyclerView.LayoutManager
        val adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
        val recyclerView = binding.TracksRecycler

        layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        adapter = RecyclerAdapter(songs, authors, this)
        recyclerView.adapter = adapter

        return binding.root
    }


    override fun onCellClickListener(position: Int) {
        val bundle = Bundle()
        bundle.putInt("position", position)
        bundle.putString("song", songs[position])
        bundle.putString("author", authors[position])

        val fragment = MediaFragment()
        fragment.arguments = bundle
        findNavController().navigate(R.id.action_secondFragment_to_mediaFragment, bundle)

    }
}