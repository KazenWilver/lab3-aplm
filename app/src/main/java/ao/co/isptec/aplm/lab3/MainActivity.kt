package ao.co.isptec.aplm.lab3

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private const val TAG = "MainActivity"
private const val PHONE_PERMISSION_REQUEST = 1

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        setContent {
            MaterialTheme {
                Column(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Lab3 - Componentes", style = MaterialTheme.typography.headlineSmall)
                    MenuButton("Start Service", ::startSimpleService)
                    MenuButton("Stop Service", ::stopSimpleService)
                    MenuButton("Pedir permissão de telefone", ::requestPhonePermission)
                    MenuButton("Bloco de notas", ::openNotes)
                    MenuButton("Encerrar", ::finishButtonPressed)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

    private fun finishButtonPressed() {
        finish()
    }

    private fun startSimpleService() {
        (application as Lab3App).incrementServiceStartCount()
        startService(Intent(this, SimpleService::class.java))
    }

    private fun stopSimpleService() {
        stopService(Intent(this, SimpleService::class.java))
    }

    private fun requestPhonePermission() {
        requestPermissions(
            arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG),
            PHONE_PERMISSION_REQUEST
        )
    }

    private fun openNotes() {
        startActivity(Intent(this, ListNotesActivity::class.java))
    }
}

@Composable
fun MenuButton(label: String, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Text(label)
    }
}
