package mobile.newsapp

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
    val a = 457
    val b = 56

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.addB.setOnClickListener {
            val result = a + b
            mainBinding.tvResult.text = "Результат сложения равен: $result"
        }

        mainBinding.subtractB.setOnClickListener {
            val result = a - b
            mainBinding.tvResult.text = "Результат вычитания равен: $result"
        }

        mainBinding.multiplyB.setOnClickListener {
            val result = a * b
            mainBinding.tvResult.text = "Результат умножения равен: $result"
        }
    }
}