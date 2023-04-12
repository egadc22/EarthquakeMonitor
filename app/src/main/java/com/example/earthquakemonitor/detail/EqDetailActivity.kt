package com.example.earthquakemonitor.detail

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.earthquakemonitor.Earthquake
import com.example.earthquakemonitor.R
import com.example.earthquakemonitor.databinding.ActivityEqDetailBinding
import java.text.SimpleDateFormat
import java.util.*

class EqDetailActivity : AppCompatActivity() {
    companion object {
        const val EQ_KEY = "earthquake"

        private const val TIME_FORMAT = "dd/MMM/yyyy HH:mm:ss"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEqDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val earthquake: Earthquake = if (Build.VERSION.SDK_INT >= 33) {
            intent?.extras?.getParcelable(EQ_KEY, Earthquake::class.java)!!
        } else {
            intent?.extras?.getParcelable<Earthquake>(EQ_KEY)!!
        }

        binding.magnitudeText.text = getString(R.string.magnitude_format, earthquake.magnitude)
        binding.longitudeText.text = earthquake.longitude.toString()
        binding.latitudeText.text = earthquake.latitude.toString()
        binding.placeText.text = earthquake.place
        setupTime(binding, earthquake)
    }

    private fun setupTime(binding: ActivityEqDetailBinding,
                          earthquake: Earthquake) {
        val dateFormat = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
        val date = Date(earthquake.time)
        binding.timeText.text = dateFormat.format(date)
    }
}