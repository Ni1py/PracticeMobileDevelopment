package mobile.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var a = 120 //mutable
    val z = 100 //immutable

    var b: Byte = 120
    var s: Short = 120
    var i: Int = 120
    var l: Long = 120L

    var f: Float = 120.8f
    var d: Double = 120.8

    var g: Boolean = true
    var j: String = "Hello"
    var tr = j.startsWith("hello")
    var c: Char = 't'

    //var tvTest : TextView? = null
    lateinit var tvTest : TextView
    lateinit var bTest : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvTest = findViewById(R.id.tvTest)
        bTest = findViewById(R.id.bTest)

        bTest.setOnClickListener {
            tvTest.text = "Privetik"
            var test1 = f.toString()
        }
    }

//    fun onClickStart(view : View) { //когда в параметры кидаем View, говорим системе, что это слушатель нажатий
//        tvTest.text = "Privetik"
//        var test1 = f.toString()
//    }
}