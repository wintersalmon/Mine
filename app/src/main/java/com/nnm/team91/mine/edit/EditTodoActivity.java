package com.nnm.team91.mine.edit;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nnm.team91.mine.MainActivity;
import com.nnm.team91.mine.R;

import java.util.ArrayList;

public class EditTodoActivity extends AppCompatActivity {
    int year, month, day, hour, minute;

    private Button cancelButton;
    private Button saveButton;

    private TextView dateTextView;
    private TextView timeTextView;
    private CheckBox statusCheckBox;
    private EditText keyEditView;
    private EditText hashEditView;

    private int position;
    private int commonId;
    private String date;
    private String time;
    private boolean status;
    private String keyTag;
    private String hashTagString;
    private ArrayList<String> hashTagList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        dateTextView = (TextView) findViewById(R.id.edit_todo_date);
        timeTextView = (TextView) findViewById(R.id.edit_todo_time);
        statusCheckBox = (CheckBox) findViewById(R.id.edit_todo_status);
        keyEditView = (EditText) findViewById(R.id.edit_todo_key_tag);
        hashEditView = (EditText) findViewById(R.id.edit_todo_hash_tag);

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditTodoActivity.this, dateSetListener, year, month, day).show();
            }
        });

        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(EditTodoActivity.this, timeSetListener, hour, minute, false).show();
            }
        });
    }
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            String msg = String.format("%d / %d / %d", year,monthOfYear+1, dayOfMonth);
            Toast.makeText(EditTodoActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            String msg = String.format("%d / %d / %d", year, hourOfDay, minute);
            Toast.makeText(EditTodoActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    };
}
