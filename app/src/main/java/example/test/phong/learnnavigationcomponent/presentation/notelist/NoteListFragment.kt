package example.test.phong.learnnavigationcomponent.presentation.notelist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import example.test.phong.learnnavigationcomponent.R
import example.test.phong.learnnavigationcomponent.domain.Note
import example.test.phong.learnnavigationcomponent.presentation.notelist.NoteListFragmentDirections.actionNotesToNoteDetail
import kotlinx.android.synthetic.main.fragment_note_list.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class NoteListFragment : Fragment() {
    private val clickListener: ClickListener = this::onNoteClicked

    private val recyclerViewAdapter = NoteAdapter(clickListener)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_list, container, false)
    }

    private lateinit var viewmodel: NoteListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        viewmodel = ViewModelProviders.of(this).get(NoteListViewModel::class.java)
        viewmodel.observableNoteList.observe(this, Observer { notes ->
            notes?.let {
                render(notes)
            }
        })

        fab.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_notes_to_addNote)
        }
    }

    override fun onResume() {
        super.onResume()
        // load new list
        viewmodel.load()
    }

    private fun render(noteList: List<Note>) {
        recyclerViewAdapter.updateNotes(noteList)
        if (noteList.isEmpty()) {
            notesRecyclerView.visibility = View.GONE
            notesNotFound.visibility = View.VISIBLE
        } else {
            notesRecyclerView.visibility = View.VISIBLE
            notesNotFound.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        notesRecyclerView.layoutManager = LinearLayoutManager(context)
        notesRecyclerView.adapter = recyclerViewAdapter
        notesRecyclerView.setHasFixedSize(true)
    }


    fun onNoteClicked(note: Note) {
        val navDirections = actionNotesToNoteDetail(note.id)
        view?.let {
            Navigation.findNavController(it).navigate(navDirections)
        }
    }
}

