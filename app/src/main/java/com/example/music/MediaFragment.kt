package com.example.music

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.music.databinding.FragmentMediaBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MediaFragment : Fragment() {

    private val allTracks = arrayListOf(R.raw.track2, R.raw.track3,
        R.raw.track4, R.raw.track5, R.raw.track6,
        R.raw.track7, R.raw.track8, R.raw.track9)


    lateinit var runnable: Runnable
    private var handler = Handler()
    private lateinit var binding: FragmentMediaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMediaBinding.inflate(inflater, container, false)
        val args = this.arguments
        var position = args?.get("position")
        position = position.toString().toInt()

        val song = args?.get("song")
        val author = args?.get("author")
        binding.SongName.text = song.toString()
        binding.Author.text = author.toString()


        val mp = MediaPlayer.create(requireContext(), allTracks[position])

        binding.seekbar.progress = 0
        binding.seekbar.max = mp.duration
        binding.endTime.text = format(mp.duration)

        binding.playbtn.setOnClickListener{
            if (!mp.isPlaying) {
                mp.start()
                binding.playbtn.setImageResource(R.drawable.pause)
            }else{
                mp.pause()
                binding.playbtn.setImageResource(R.drawable.play1)
            }
        }

        binding.forwardbtn.setOnClickListener{
            mp.seekTo(mp.currentPosition + 10000)
        }

        binding.rewindbtn.setOnClickListener{
            mp.seekTo(mp.currentPosition - 10000)
        }

        binding.seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, pos: Int, changed: Boolean) {
                if(changed){
                    mp.seekTo(pos)
                    binding.startTime.text = format(mp.currentPosition)

                }
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {

            }
            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        runnable = Runnable {
            binding.seekbar.progress = mp.currentPosition
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
        mp.setOnCompletionListener {
            binding.playbtn.setImageResource(R.drawable.play1)
            binding.seekbar.progress = 0
        }

        binding.swipemusic.setOnClickListener{
            findNavController().navigate(R.id.action_mediaFragment_to_secondFragment)
        }

        return binding.root
    }

    fun format(time: Int): String {
        val format: DateFormat = SimpleDateFormat("mm:ss", Locale.US)
        return format.format(Date(time.toLong()))
    }


}