package mobile.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Contacts
import android.view.View
import mobile.newsapp.constants.Constants
import mobile.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.btResult.setOnClickListener {
            val resultValue = mainBinding.edValue.text.toString()
            mainBinding.tvResult.visibility = View.VISIBLE

            mainBinding.tvResult.text = when(resultValue) {
                Constants.ENGINEER -> {
                     isCorrectPassword(
                        mainBinding.edPassword.text.toString(),
                        Constants.ENGINEER_PASSWORD,
                        Constants.ENGINEER_SALARY
                    )
                }
                Constants.JANITOR -> {
                    isCorrectPassword(
                        mainBinding.edPassword.text.toString(),
                        Constants.JANITOR_PASSWORD,
                        Constants.JANITOR_SALARY
                    )
                }
                Constants.DIRECTOR -> {
                    isCorrectPassword(
                        mainBinding.edPassword.text.toString(),
                        Constants.DIRECTOR_PASSWORD,
                        Constants.DIRECTOR_SALARY
                    )
                }
                else -> "Нет такого работника"
            }
        }
    }

    private fun isCorrectPassword(text: String, password: Int, salary: Int) =
        if (text.isNotEmpty()) {
            if (text.toInt() == password)
                "Получите ваши $salary"
            else
                "Неверный пароль"
        }  else
            "Вы не ввели пароль"
}
