package ao.co.isptec.aplm.lab3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private const val EXTRA_NOTE_INDEX = "note_index"

class ListNotesActivity : ComponentActivity() {

    private val titles = mutableStateListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Column(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Notas", style = MaterialTheme.typography.headlineSmall)
                    MenuButton("New Note", ::openCreateNote)
                    LazyColumn {
                        itemsIndexed(titles) { index, title ->
                            Text(
                                text = title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { openNote(index) }
                                    .padding(vertical = 12.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        titles.clear()
        titles.addAll((application as Lab3App).notes.map { it.title })
    }

    private fun openCreateNote() {
        startActivity(Intent(this, CreateNoteActivity::class.java))
    }

    private fun openNote(index: Int) {
        val intent = Intent(this, ReadNoteActivity::class.java)
        intent.putExtra(EXTRA_NOTE_INDEX, index)
        startActivity(intent)
    }
}

class CreateNoteActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                var title by remember { mutableStateOf("") }
                var content by remember { mutableStateOf("") }
                Column(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Título") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = content,
                        onValueChange = { content = it },
                        label = { Text("Texto") },
                        modifier = Modifier.fillMaxWidth().height(200.dp)
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(onClick = { saveNote(title, content) }) { Text("OK") }
                        Button(onClick = ::finish) { Text("Cancel") }
                    }
                }
            }
        }
    }

    private fun saveNote(title: String, content: String) {
        if (title.isNotBlank()) {
            (application as Lab3App).notes.add(Note(title.trim(), content))
        }
        finish()
    }
}

class ReadNoteActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val index = intent.getIntExtra(EXTRA_NOTE_INDEX, 0)
        val note = (application as Lab3App).notes[index]
        setContent {
            MaterialTheme {
                Column(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(note.title, style = MaterialTheme.typography.headlineSmall)
                    Text(note.content)
                }
            }
        }
    }
}
