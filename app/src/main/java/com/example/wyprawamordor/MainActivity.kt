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
                when (raceList[position]) {
                    "Elf" -> characterImage.setImageResource(R.drawable.elf)
                    "Hobbit" -> characterImage.setImageResource(R.drawable.hobbit)
                    "Człowiek" -> characterImage.setImageResource(R.drawable.czlowiek)
                    "Krasnolud" -> characterImage.setImageResource(R.drawable.krasnolud)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }


        val item1 : CheckBox = findViewById(R.id.item1CheckBox)
        val item2 : CheckBox = findViewById(R.id.item2CheckBox)
        val item3 : CheckBox = findViewById(R.id.item3CheckBox)



        val walkRadioGroup = findViewById<RadioGroup>(R.id.walkRadioGroup)

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
                //nothing
            }

        })
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



        val progressBar = findViewById<ProgressBar>(R.id.timeToWalkProgressBar)
        val timeToGo = findViewById<TextView>(R.id.timeToGoTextView)
        val countDownTimer : CountDownTimer = object : CountDownTimer(30000, 1){
            override fun onFinish() {
                progressBar.progress = 30000
                Toast.makeText(this@MainActivity, "Wyruszyłeś!", Toast.LENGTH_SHORT).show()
                timeToGo.text = "Czas do wymarszu 0"


                val rasa = raceSpinner.selectedItem.toString()

                val przedmiot1 = if (item1.isChecked) "${item1.text}, " else ""
                val przedmiot2 = if (item2.isChecked) "${item2.text}, " else ""
                val przedmiot3 = if (item3.isChecked) "${item3.text} "  else ""

                val elfieSciezki = if (elfPath.isChecked) "Tak" else "Nie"

                val selectedWalkId = walkRadioGroup.checkedRadioButtonId
                val priorytetMarszu = if (selectedWalkId != -1) {
                    findViewById<RadioButton>(selectedWalkId).text.toString()
                } else {
                    "Nie wybrano"
                }

                val morale = teamMorale.rating.toInt()



                val dzien = dateOfTravel.dayOfMonth
                val miesiac = dateOfTravel.month + 1 // kotlin styczeń ma jako 0
                val rok = dateOfTravel.year
                val godzina = timeOfTravel.hour
                val minuta = timeOfTravel.minute
                val dataWymarszu = "$dzien/$miesiac/$rok $godzina:$minuta"

                podsumowanie.text = "Imię: ${name.text}\n Rasa: $rasa \n Przedmioty: $przedmiot1 $przedmiot2 $przedmiot3 \n" +
                        "Priorytet marszu: $priorytetMarszu\n Czas marszu: ${walkTime.progress}\n  Elfie ścieżki: $elfieSciezki \n"+
                        "Morale drużyny: $morale \n Data wymarszu $dataWymarszu"

            }

            override fun onTick(millisUntilFinished: Long) {
                val progress = ((30000 - millisUntilFinished).toFloat()/30000*100).toInt()
                progressBar.progress = progress
                timeToGo.text = "Czas do wymarszu ${millisUntilFinished/1000}"
            }
        }

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