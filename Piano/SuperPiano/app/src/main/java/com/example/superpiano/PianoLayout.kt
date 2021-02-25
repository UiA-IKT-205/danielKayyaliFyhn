package com.example.superpiano

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.superpiano.databinding.FragmentPianoLayoutBinding
import kotlinx.android.synthetic.main.fragment_piano_layout.view.*

class PianoLayout : Fragment() {

    private var _binding:FragmentPianoLayoutBinding? = null
    private val binding get() = _binding!!


    private val allTones = listOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B",
            "C2", "C2#", "D2", "D2#", "E2", "F2", "F2#", "G2")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPianoLayoutBinding.inflate(layoutInflater)
        val view = binding.root

        val fm = childFragmentManager
        val ft = fm.beginTransaction()

        allTones.forEach(){
            val fullTonePianoKey = FullTonePianoKeyFragment.newInstance(it)


            val pattern = ".*#".toRegex()

            if(pattern.containsMatchIn(it)){
                fullTonePianoKey.onKeyDown = {
                    println("Piano key down $it")
                }

                fullTonePianoKey.onKeyUp = {
                    println("Piano key up $it")
                }
                ft.add(view.pianoKeys.id, fullTonePianoKey, "note_$it")

            } else {
                fullTonePianoKey.onKeyDown = {
                    println("Piano key down $it")
                }

                fullTonePianoKey.onKeyUp = {
                    println("Piano key up $it")
                }
                ft.add(view.pianoKeys.id,fullTonePianoKey,"note_$it")
            }
        }

        ft.commit()

        return view
    }
}