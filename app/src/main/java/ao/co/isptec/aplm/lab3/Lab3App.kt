package ao.co.isptec.aplm.lab3

import android.app.Application

data class Note(val title: String, val content: String)

class Lab3App : Application() {

    var serviceStartCount = 0
        private set

    val notes = mutableListOf<Note>()

    fun incrementServiceStartCount() {
        serviceStartCount++
    }
}
