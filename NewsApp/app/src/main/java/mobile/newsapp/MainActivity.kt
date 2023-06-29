package mobile.newsapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import mobile.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding : ActivityMainBinding
    val maxPerson = 90
    val currentPerson = 91
    val text = "кот"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.check1.setOnClickListener {
            mainBinding.tvResult.setBackgroundColor(if (maxPerson > currentPerson) Color.GREEN else Color.RED)
            if (maxPerson > currentPerson)
                mainBinding.tvResult.text = "Все в порядке"
            else
                mainBinding.tvResult.text = "Превышено максимальное количество посетителей"
        }

        mainBinding.check2.setOnClickListener {
            when(currentPerson) {
                in 0..maxPerson -> mainBinding.tvResult.text = "Все в порядке"
                else -> mainBinding.tvResult.text = "Превышено максимальное количество посетителей"
            }
        }

        mainBinding.check3.setOnClickListener {
            mainBinding.tvResult.text = when(text) {
                "опасность" -> "опасность"
                "не опасность" -> "не опасность"
                "кот", "кошка" -> "кот, кошка"
                else -> "непонятно"
            }
        }
    }
}