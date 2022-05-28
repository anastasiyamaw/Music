package com.example.music

import android.app.DownloadManager
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Environment
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

    private val dlsongs = arrayOf(
        "https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1uu2PDXQpf61vfIFUepbE13qBNaHknBFy",
        "https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1wWS2AaCnjUHxMCQTeYXJ9h17mcAadzIc",
        "https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1wQzzmV_rOAcguL7VVJ65Fs-gLdymawQe",
        "https://drive.google.com/uc?export=download&confirm=no_antivirus&id=15oD3BWZJaHjs0B7Km4c_HEzodu4XRmex",
        "https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1x62kWYCOnmqga2kRCwBW5-QNOH-ikVEj",
        "https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1HInO8uxdS66HRdBMYqcBDW2KcH_TTLrP",
        "https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1f0FdmAcRvNn5GB1smRJpwcgn_yQdIqe0",
        "https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1WBelRqrJIw0MUGUneAunw2GR9Iqwt-bX")

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
        binding.downloadTrack.setOnClickListener{
            downloads(position)
        }

        return binding.root
    }

    fun format(time: Int): String {
        val format: DateFormat = SimpleDateFormat("mm:ss", Locale.US)
        return format.format(Date(time.toLong()))
    }

    fun downloads(position: Int){
        val request = DownloadManager.Request(Uri.parse(dlsongs[position]))
            .setTitle("Track")
            .setDescription("Downloading..")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Track")
            .setAllowedOverMetered(true)

        val dm = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)
    }
}