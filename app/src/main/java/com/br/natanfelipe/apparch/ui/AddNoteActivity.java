package com.br.natanfelipe.apparch.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.br.natanfelipe.apparch.R;
import com.br.natanfelipe.apparch.model.Note;
import com.br.natanfelipe.apparch.vm.NewNoteViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNoteActivity extends AppCompatActivity {

    private static final String TAG = "AddNoteActivity";
    @BindView(R.id.et_title)
    EditText title;
    @BindView(R.id.et_desc)
    EditText desc;
    @BindView(R.id.np_priority)
    NumberPicker priority;
    private NewNoteViewModel newNoteViewModel;
    boolean isToEdit = false;
    Note note;
    Intent i;
    int situation = 1;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        ButterKnife.bind(this);
        newNoteViewModel = ViewModelProviders.of(this).get(NewNoteViewModel.class);

        priority.setMaxValue(10);
        priority.setMinValue(1);

        i = getIntent();
        if (i.getExtras() != null) {
            situation = i.getIntExtra(MainActivity.EXTRA_SITUATION, 1);
            id = i.getIntExtra(MainActivity.EXTRA_ID, 0);
        }

        if (situation == 2)
            isToEdit = true;
        else
            isToEdit = false;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        if (isToEdit) {
            setTitle(R.string.lb_edit_note);
            populateFields();
        } else
            setTitle(R.string.lb_add_note);

    }

    private void populateFields() {
        String resultTitle = "";
        String resultDesc = "";
        int resultPriority = 1;
        if (i.getExtras() != null) {
            resultTitle = i.getStringExtra(MainActivity.EXTRA_TITLE);
            title.setText(resultTitle);

            resultDesc = i.getStringExtra(MainActivity.EXTRA_DESC);
            desc.setText(resultDesc);

            resultPriority = i.getIntExtra(MainActivity.EXTRA_PRIORITY, 1);
            priority.setValue(resultPriority);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);
        if (isToEdit)
            menu.getItem(0).setIcon(R.drawable.ic_mode_edit_black_24dp);
        else
            menu.getItem(0).setIcon(R.drawable.ic_save_black_24dp);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.save_data:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String mTitle = title.getText().toString();
        String mDesc = desc.getText().toString();
        int mPriority = priority.getValue();

        if (mTitle.trim().isEmpty() || mDesc.trim().isEmpty()) {
            Toast.makeText(this, "Please fill the fields", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (isToEdit) {
                note = new Note(mTitle, mDesc, mPriority);
                note.setId(id);
                newNoteViewModel.update(note);
            } else {
                note = new Note(mTitle, mDesc, mPriority);
                newNoteViewModel.insert(note);
            }
            finish();
        }
    }
}
