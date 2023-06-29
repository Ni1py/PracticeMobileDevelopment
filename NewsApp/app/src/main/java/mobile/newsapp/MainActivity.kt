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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.btResult.setOnClickListener {
            val resultValue = mainBinding.edValue.text.toString().toInt()
            mainBinding.tvResult.visibility = View.VISIBLE
            when(resultValue) {
                in 0..1000 -> mainBinding.tvResult.text = "Вы начинающий блогер"
                in 1001..100000 -> mainBinding.tvResult.text = "Вы средний блогер"
                in 100001..1000000 -> mainBinding.tvResult.text = "Вы суперзвезда!"
                else -> mainBinding.tvResult.text = "Вы нечто!"
            }
        }
    }
}