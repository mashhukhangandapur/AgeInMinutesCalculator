package com.example.dobcalculator

import android.app.DatePickerDialog
import android.icu.util.Calendar
import java.util.Locale
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    var tvSelectedDate : TextView? = null
    var ageInMinutes : TextView? =  null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        ageInMinutes = findViewById(R.id.ageInMinutes)


        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }
       private fun clickDatePicker(){
            val myCalender = Calendar.getInstance()
            val year = myCalender.get(Calendar.YEAR)
            val month  = myCalender.get(Calendar.MONTH)
            val day = myCalender.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener{ view , year, month , dayOFMonth ->
                    Toast.makeText(this, "Year was $year, month was $month and day was $dayOFMonth",
                        Toast.LENGTH_LONG).show()

                    var selectedDate = "$dayOFMonth/$month/$year"
                    tvSelectedDate?.text = selectedDate

                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                    val date = sdf.parse(selectedDate)

                    date?.let {
                        val selectedDateInMinutes = date.time / 60000

                        val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                        currentDate?.let {
                            val currentDateInMinutes = currentDate.time / 60000

                            val differeneInMinutes = currentDateInMinutes - selectedDateInMinutes

                            ageInMinutes?.text = differeneInMinutes.toString()
                        }
                    }



                }, year,
                day,
                month
            )

            dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
            dpd.show()
        }
    }
