package com.example.superpiano

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_piano.view.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_full_tone_piano_key.view.*
import com.example.superpiano.databinding.FragmentPianoBinding
import java.io.File
import java.io.FileOutputStream


class PianoLayout : Fragment() {


    private var _binding: FragmentPianoBinding? = null
    private val binding get() = _binding!!

    private val fullTones = listOf("C", "D", "E", "F", "G", "A", "B", "C", "D", "E", "F", "G")
    private val halfTones = listOf("C#", "D#", "F#", "G#", "A#", "C2#", "D2#", "F2#")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        _binding = FragmentPianoBinding.inflate(layoutInflater)
        val view = binding.root

        val fm = childFragmentManager
        val ft = fm.beginTransaction()

        fullTones.forEach {
            val fullTonePianoKey = FullTonePianoKeyFragment.newInstance(it)

            fullTonePianoKey.onKeyUp = {
                println("Key up $it")
            }
            fullTonePianoKey.onKeyDown = {
                println("Key down $it")
            }


        }
        return view




    }

}






