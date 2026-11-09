package ao.co.isptec.aplm.lab3

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast

class SimpleService : Service() {

    override fun onCreate() {
        super.onCreate()
        showToast("Service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val count = (application as Lab3App).serviceStartCount
        showToast("Service started: $count")
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        showToast("Service destroyed")
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
