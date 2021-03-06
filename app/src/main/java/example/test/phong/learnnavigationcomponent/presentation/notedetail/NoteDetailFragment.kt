package example.test.phong.learnnavigationcomponent.presentation.notedetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import example.test.phong.learnnavigationcomponent.R
import example.test.phong.learnnavigationcomponent.domain.Note
import example.test.phong.learnnavigationcomponent.presentation.notedetail.NoteDetailFragmentArgs.fromBundle
import example.test.phong.learnnavigationcomponent.presentation.notedetail.NoteDetailFragmentDirections.actionNoteDetailFragmentToDeleteNoteFragment
import example.test.phong.learnnavigationcomponent.presentation.notedetail.NoteDetailFragmentDirections.actionNoteDetailFragmentToEditNoteFragment
import kotlinx.android.synthetic.main.fragment_note_detail.*


class NoteDetailFragment : Fragment() {
    private lateinit var viewModel: NoteDetailViewModel

    private val noteId by lazy {
        fromBundle(arguments).noteId
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NoteDetailViewModel::class.java)

        viewModel.observableNote.observe(this, Observer { note ->
            note?.let { render(note) } ?: renderNoteNotFound()
        })

        editNoteButton.setOnClickListener {
            Navigation.findNavController(it).navigate(actionNoteDetailFragmentToEditNoteFragment(noteId))
        }

        deleteNoteButton.setOnClickListener {
            Navigation.findNavController(it).navigate(actionNoteDetailFragmentToDeleteNoteFragment(noteId))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNote(noteId)
    }

    private fun renderNoteNotFound() {
        noteIdView.visibility = View.GONE
        noteText.visibility = View.GONE
        view?.let {
            Snackbar.make(it, R.string.error_loading_note, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun render(note: Note) {
        noteIdView.text = String.format(getString(R.string.note_detail_id), note.id)
        noteText.text = String.format(getString(R.string.note_detail_text), note.text)
    }
}
