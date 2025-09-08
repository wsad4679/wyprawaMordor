package com.example.wyprawamordor

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val name : EditText = findViewById(R.id.nameEditText)

        val raceSpinner : Spinner = findViewById(R.id.raceSpinner)
        val raceList = arrayOf("Elf", "Hobbit", "Człowiek", "Krasnolud")



        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, raceList)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        raceSpinner.adapter = adapter

        val podsumowanie = findViewById<TextView>(R.id.characterSummaryTextView)
        raceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //wyprowadzić dane ze spinnera
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }


        val item1 : CheckBox = findViewById(R.id.item1CheckBox)
        val item2 : CheckBox = findViewById(R.id.item2CheckBox)
        val item3 : CheckBox = findViewById(R.id.item3CheckBox)


        val walkRadioGroup = findViewById<RadioGroup>(R.id.walkRadioGroup)

        walkRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            //TODO("Wyprowadzić dane")
        }

        val walkTime = findViewById<SeekBar>(R.id.walkTimeSeekBar)
        val walkTimeView = findViewById<TextView>(R.id.walkTimeTextView)
        walkTime.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                walkTimeView.text = "Czas marszu: $progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //nothing
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //TODO("Wyprowadzić dane")
            }

        })

        val progressBar = findViewById<ProgressBar>(R.id.timeToWalkProgressBar)

        val countDownTimer : CountDownTimer = object : CountDownTimer(30000, 1){
            override fun onFinish() {
                //TODO("Not yet implemented")
            }

            override fun onTick(millisUntilFinished: Long) {
                val progress = ((30000 - millisUntilFinished).toFloat()/30000*100).toInt()
                progressBar.progress = progress
            }
        }.start()



    }
}