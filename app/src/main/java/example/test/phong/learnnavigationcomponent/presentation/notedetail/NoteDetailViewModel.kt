package example.test.phong.learnnavigationcomponent.presentation.notedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import example.test.phong.learnnavigationcomponent.domain.Note
import example.test.phong.learnnavigationcomponent.domain.NotesManager

class NoteDetailViewModel : ViewModel() {
    private val note = MutableLiveData<Note>()

    val observableNote: LiveData<Note>
        get() = note

    fun getNote(id: Int) {
        note.value = NotesManager.getNote(id)
    }
}