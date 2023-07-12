package mobile.newsapp.extension

import android.os.Build
import androidx.annotation.RequiresApi
import mobile.newsapp.constants.Constants
import java.time.LocalDate

fun String.withQuotes() = "\"$this\""

fun String.withPercentages() = "%$this%"

@RequiresApi(Build.VERSION_CODES.O)
fun String.reformatTextDate() : String {
    val date = LocalDate.parse(this)
    return "${date.dayOfMonth} ${Constants.NAME_MONTHS[date.month.toString()]}"
}
