package example.test.phong.learnnavigationcomponent.presentation.notelist;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import example.test.phong.learnnavigationcomponent.R
import example.test.phong.learnnavigationcomponent.domain.Note
import kotlinx.android.synthetic.main.note_item.view.*

typealias ClickListener = (Note) -> Unit

class NoteAdapter(private val clickListener: ClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var noteList = emptyList<Note>()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val note = noteList[position]
        holder.itemView.noteId.text = note.id.toString()
        holder.itemView.noteText.text = note.text
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.note_item, parent, false)
        val vh = ViewHolder(v)
        v.setOnClickListener {
            clickListener(noteList[vh.adapterPosition])
        }
        return vh
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun updateNotes(noteList: List<Note>) {
        val diffResult = DiffUtil.calculateDiff(NoteDiffCallback(this.noteList, noteList))
        this.noteList = noteList
        diffResult.dispatchUpdatesTo(this)
    }

    private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

class NoteDiffCallback(private val old: List<Note>,
                       private val new: List<Note>) : DiffUtil.Callback() {
    override fun getOldListSize() = old.size

    override fun getNewListSize() = new.size

    override fun areItemsTheSame(oldIndex: Int, newIndex: Int): Boolean {
        return old[oldIndex].text == new[newIndex].text
    }

    override fun areContentsTheSame(oldIndex: Int, newIndex: Int): Boolean {
        return old[oldIndex] == new[newIndex]
    }
}

