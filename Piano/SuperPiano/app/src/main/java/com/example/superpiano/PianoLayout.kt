package com.example.superpiano

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.superpiano.data.Note
import com.example.superpiano.databinding.FragmentPianoLayoutBinding
import kotlinx.android.synthetic.main.fragment_full_tone_piano_key.view.*
import kotlinx.android.synthetic.main.fragment_half_tone_piano_key.view.*
import kotlinx.android.synthetic.main.fragment_piano_layout.view.*
import java.io.File
import java.io.FileOutputStream

class PianoLayout : Fragment() {

    private var _binding:FragmentPianoLayoutBinding? = null
    private val binding get() = _binding!!
    private var score = mutableListOf<Note>()

    private var musicStart:Long = 0
    private var isPlaying:Boolean = false


    private val allTones = listOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B",
            "C2", "C2#", "D2", "D2#", "E2", "F2", "F2#", "G2")


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPianoLayoutBinding.inflate(layoutInflater)
        val view = binding.root

        val fm = childFragmentManager
        val ft = fm.beginTransaction()

        allTones.forEach(){ orgNoteValue ->
            val fullTonePianoKey = FullTonePianoKeyFragment.newInstance(orgNoteValue)
            val halfTonePianoKey = HalfTonePianoKeyFragment.newInstance(orgNoteValue)
            var startPlay: Long =0


            val pattern = ".*#".toRegex()

            if(pattern.containsMatchIn(orgNoteValue)){
                halfTonePianoKey.onKeyDown = {
                    if(!isPlaying){
                        startMusic()
                    } else {
                        startPlay = System.nanoTime() - musicStart
                    }
                    println("Piano key down $it / id = " + view.halfToneKey.id)
                }

                halfTonePianoKey.onKeyUp = {
                    val endPlay = System.nanoTime() - musicStart
                    val note = Note(it, startPlay, endPlay)
                    score.add(note)
                    println("Piano key up $it / id = " + view.halfToneKey.id)
                }
                ft.add(view.pianoKeys.id, halfTonePianoKey, "note_$orgNoteValue")

            } else {
                fullTonePianoKey.onKeyDown = {
                    if(!isPlaying){
                        startMusic()
                    } else {
                        startPlay = System.nanoTime() - musicStart
                    }
                    println("Piano key down $it / id = " + view.fullToneKey.id)
                }

                fullTonePianoKey.onKeyUp = {
                    val endPlay = System.nanoTime()
                    val note = Note(it, startPlay, endPlay)
                    score.add(note)
                    println("Piano key up $it / id = " + view.fullToneKey.id)
                }
                ft.add(view.pianoKeys.id,fullTonePianoKey,"note_$orgNoteValue")
            }

            }
            ft.commit()

        view.saveScoreBt.setOnClickListener {
            var fileName:String = "unknown"
            if(view.fileNameTextEdit.text.toString()!=""){
                fileName = view.fileNameTextEdit.text.toString()
            }
            if(fileName == ""){
                fileName = "Unknown"
            }
            val path = this.activity?.getExternalFilesDir(null)
            saveToFile(score as ArrayList<Note>, fileName)
        }

        return view
    }
    private fun startMusic(){
        musicStart = System.nanoTime()
        isPlaying = true
    }

    fun saveToFile(notes: ArrayList<Note>, filename: String){//(notes: mutableListOf<note>()){
        var fileName = filename
        val path = this.activity?.getExternalFilesDir(null)

        if(notes.count() > 0 && fileName.isNotEmpty() && path != null) {

            if(!File(path,"$fileName.music").exists()){
                fileName = "$fileName.music"
            } else {
                fileName = fileName + System.nanoTime() + ".music"
                Log.d("saveScoreBt", "Filename already exists, saving to $fileName instead: ")
            }

            FileOutputStream(File(path,fileName), true).bufferedWriter().use { writer ->

                notes.forEach {
                    writer.write("${it}\n")
                }
                writer.close()
            }
        }

        score.clear()
    }

}