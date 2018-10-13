package example.test.phong.learnnavigationcomponent.presentation.notelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import example.test.phong.learnnavigationcomponent.domain.Note
import example.test.phong.learnnavigationcomponent.domain.NotesManager

class NoteListViewModel : ViewModel() {
    private val noteList = MutableLiveData<List<Note>>()

    val observableNoteList: LiveData<List<Note>>
        get() = noteList

    init {
        load()
    }

    fun load() {
        noteList.postValue(NotesManager.getNoteList())
    }
}