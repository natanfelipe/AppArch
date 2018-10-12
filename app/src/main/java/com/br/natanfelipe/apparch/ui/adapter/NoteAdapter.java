package com.br.natanfelipe.apparch.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.br.natanfelipe.apparch.OnItemClickListener;
import com.br.natanfelipe.apparch.R;
import com.br.natanfelipe.apparch.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notes = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public NoteAdapter(OnItemClickListener onItemClickListener) {
        //this.notes = notes;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentNote = notes.get(position);
        holder.bind(currentNote,onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle,tvDesc,tvPriority;

        public NoteViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_description);
            tvPriority = itemView.findViewById(R.id.tv_priority);
        }

        public void bind(final Note note, final OnItemClickListener onItemClickListener) {
            tvTitle.setText(note.getTitle());
            tvDesc.setText(note.getDescription());
            tvPriority.setText(String.valueOf(note.getPriority()));

            int id = note.getId();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(note);
                }
            });
        }
    }

    public Note getNoteAt(int position){
        return notes.get(position);
    }

    public void setNotes(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }
}
