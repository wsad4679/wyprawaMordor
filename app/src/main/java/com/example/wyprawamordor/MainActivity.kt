package com.example.wyprawamordor

import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.Chronometer
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RatingBar
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
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

        val characterImage = findViewById<ImageView>(R.id.characterImageView)
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
        val timeToGo = findViewById<TextView>(R.id.timeToGoTextView)
        val countDownTimer : CountDownTimer = object : CountDownTimer(30000, 1){
            override fun onFinish() {
                progressBar.progress = 30000
                Toast.makeText(this@MainActivity, "Wyruszyłeś!", Toast.LENGTH_SHORT).show()
                timeToGo.text = "Czas do wymarszu 0"
            }

            override fun onTick(millisUntilFinished: Long) {
                val progress = ((30000 - millisUntilFinished).toFloat()/30000*100).toInt()
                progressBar.progress = progress
                timeToGo.text = "Czas do wymarszu ${millisUntilFinished/1000}"
            }
        }


        val elfPath = findViewById<Switch>(R.id.elfPathSwitch)

        val teamMorale = findViewById<RatingBar>(R.id.teamMoraleRatingBar)

        val dateOfTravel = findViewById<DatePicker>(R.id.dateDatePicker)
        val timeOfTravel = findViewById<TimePicker>(R.id.timeTimePicker)
        val startTraining = findViewById<Button>(R.id.startTrainingButton)
        val endTraining = findViewById<Button>(R.id.endTrainingButton)
        val startJourney = findViewById<Button>(R.id.startJourneyButton)
        val trainingTime = findViewById<Chronometer>(R.id.trainingTimeChronometer)
        var running = false
        var pauseOffset: Long = 0

        startTraining.setOnClickListener {
            if (!running) {
                trainingTime.base = SystemClock.elapsedRealtime() - pauseOffset
                trainingTime.start()
                running = true
            }
        }

        endTraining.setOnClickListener {
            pauseOffset = SystemClock.elapsedRealtime() - trainingTime.base
            trainingTime.stop()
            running = false
        }

        startJourney.setOnClickListener {
            countDownTimer.start()
        }

    }
}