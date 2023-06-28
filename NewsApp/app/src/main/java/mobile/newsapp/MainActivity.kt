package mobile.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("NewsApp", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("NewsApp", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("NewsApp", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("NewsApp", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("NewsApp", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("NewsApp", "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("NewsApp", "onRestart")
    }
}