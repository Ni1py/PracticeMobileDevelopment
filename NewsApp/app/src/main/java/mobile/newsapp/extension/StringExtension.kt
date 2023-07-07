package mobile.newsapp.extension

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

fun String.withQuotes() = "\"$this\""

fun String.withPercentages() = "%$this%"

@RequiresApi(Build.VERSION_CODES.O)
fun String.reformatTextDate() : String {
    val date = LocalDate.parse(this)
    return "${date.dayOfMonth}-${date.monthValue}-${date.year}"
}
