package com.nnm.team91.mine;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nnm.team91.mine.data.TodoData;

public class DetailTodoActivity extends AppCompatActivity {
    private Button prevButton;
    private Button nextButton;
    private Button deleteButton;


    private TextView dateTextView;
    private TextView timeTextView;
    private CheckBox statusCheckBox;
    private TextView keyTextView;
    private TextView hashTextView;

    private TodoData selectedTodo;

    private OnTodoDetailActivityInteractionListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_todo);

        // TODO: 2016. 12. 8. use better method to gain MainActivity instance
        final Context context = (Context) MainActivity.activity;
        if (context instanceof OnTodoDetailActivityInteractionListener) {
            mListener = (OnTodoDetailActivityInteractionListener) context;
        }

        dateTextView = (TextView) findViewById(R.id.detail_todo_date);
        timeTextView = (TextView) findViewById(R.id.detail_todo_time);
        statusCheckBox = (CheckBox) findViewById(R.id.detail_todo_status);
        keyTextView = (TextView) findViewById(R.id.detail_todo_key_tag);
        hashTextView = (TextView) findViewById(R.id.detail_todo_hash_tag);

        prevButton = (Button) findViewById(R.id.detail_todo_button_prev);
        nextButton = (Button) findViewById(R.id.detail_todo_button_next);
        deleteButton = (Button) findViewById(R.id.detail_todo_button_delete);

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                final View dialogEditView = inflater.inflate(R.layout.dialog_edit_date, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailTodoActivity.this);
                builder.setTitle("날짜 선택"); //Dialog 제목
                builder.setView(dialogEditView); //위에서 inflater가 만든 dialogView 객체 세팅 (Customize)

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // get date
                        DatePicker datePicker = (DatePicker) dialogEditView.findViewById(R.id.dialog_edit_date_picker);
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth();
                        int day = datePicker.getDayOfMonth();

                        // set date
                        selectedTodo.setDate(year,month + 1,day);

                        mListener.updateTodoData(selectedTodo);

                        dateTextView.setText(selectedTodo.getDate());
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 취소 출력 메세지 변경
                        Toast.makeText(DetailTodoActivity.this, "취소", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();

                // Set DatePicker Date to current selected date
                DatePicker datePicker = (DatePicker) dialogEditView.findViewById(R.id.dialog_edit_date_picker);

                int year = selectedTodo.getYear();
                int month =  selectedTodo.getMonth();
                int day = selectedTodo.getDay();
                datePicker.updateDate(year, month-1, day);

                dialog.show();
            }
        });

        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                final View dialogEditView = inflater.inflate(R.layout.dialog_edit_time, null);

                AlertDialog.Builder buider = new AlertDialog.Builder(DetailTodoActivity.this);
                buider.setTitle("시간 선택"); //Dialog 제목
                buider.setView(dialogEditView); //위에서 inflater가 만든 dialogView 객체 세팅 (Customize)

                buider.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // get time
                        TimePicker TimePicker = (TimePicker) dialogEditView.findViewById(R.id.dialog_edit_time_picker);
                        int hour, minute;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            hour = TimePicker.getHour();
                            minute = TimePicker.getMinute();
                        } else {
                            hour = TimePicker.getCurrentHour();
                            minute = TimePicker.getCurrentMinute();
                        }

                        // set time
                        selectedTodo.setTime(hour,minute);

                        mListener.updateTodoData(selectedTodo);

                        timeTextView.setText(selectedTodo.getTime());
//                        timeTextView.invalidate();
                    }
                });

                buider.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 취소 출력 메세지 변경
                        Toast.makeText(DetailTodoActivity.this, "취소", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = buider.create();

                // Set TimePicker Time to current selected time
                TimePicker timePicker = (TimePicker) dialogEditView.findViewById(R.id.dialog_edit_time_picker);
                int hour = selectedTodo.getHour();
                int minute = selectedTodo.getMinute();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    timePicker.setHour(hour);
                    timePicker.setMinute(minute);
                }
                else {
                    timePicker.setCurrentHour(hour);
                    timePicker.setCurrentMinute(minute);
                }

                dialog.show();

            }
        });

        statusCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selectedTodo.setStatus(isChecked);
                mListener.updateTodoDataStatus(selectedTodo);
            }
        });

        hashTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();

                final View dialogEditView = inflater.inflate(R.layout.dialog_edit_hash_tag, null);

                AlertDialog.Builder buider = new AlertDialog.Builder(DetailTodoActivity.this);
                buider.setTitle("해시 태그 선택"); //Dialog 제목
                buider.setView(dialogEditView); //위에서 inflater가 만든 dialogView 객체 세팅 (Customize)

                buider.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 해쉬태그 변경 기능 추가
                        EditText hashTag = (EditText) dialogEditView.findViewById(R.id.dialog_edit_hash_tag);
                        Editable editableHashTag = hashTag.getText();
                        String hashTagString = editableHashTag.toString();

                        selectedTodo.setHashTag(hashTagString);

                        mListener.updateTodoData(selectedTodo);

                        hashTextView.setText(selectedTodo.getHasTagListString());
                        keyTextView.setText(selectedTodo.getKeyTag());
                    }
                });

                buider.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 취소 출력 메세지 변경
                        Toast.makeText(DetailTodoActivity.this, "취소", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = buider.create();
                TextView hashTag = (EditText) dialogEditView.findViewById(R.id.dialog_edit_hash_tag);
                hashTag.setText(selectedTodo.getHasTagListString());
                dialog.show();
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoData todo = mListener.findPrevTodo();
                if (todo != null) {
                    selectedTodo = todo;
                    reloadViewContents();
                } else {
                    Toast.makeText(DetailTodoActivity.this, "이전 이벤트가 없습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoData todo = mListener.findNextTodo();
                if (todo != null) {
                    selectedTodo = todo;
                    reloadViewContents();
                } else {
                    Toast.makeText(DetailTodoActivity.this, "다음 이벤트가 없습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016. 12. 7. add delete dialog function
                AlertDialog.Builder buider = new AlertDialog.Builder(DetailTodoActivity.this);
                buider.setMessage("삭제하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mListener.deleteTodoData(selectedTodo);
                                reloadViewContents();
                            }
                        }).setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                AlertDialog confirm = buider.create();
                confirm.show();
            }
        });

        reloadViewContents();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        reloadViewContents();
    }

    private void reloadViewContents() {
        setTodoData();
        setViewData();
        if (selectedTodo == null) {
            // if selectedTodo data is null finish the view
            finish();
        }
    }

    public void setTodoData() {
        selectedTodo = mListener.getSelectedTodo();
    }

    private void setViewData() {
        if (selectedTodo != null) {
            dateTextView.setText(selectedTodo.getDate());
            timeTextView.setText(selectedTodo.getTime());
            statusCheckBox.setChecked(selectedTodo.getStatus());
            keyTextView.setText(selectedTodo.getKeyTag());

            String hashTagString = "";
            for (String tag : selectedTodo.getHashTagList()) {
                hashTagString += tag + " ";
            }
            hashTextView.setText(hashTagString);
        }
    }

    public interface OnTodoDetailActivityInteractionListener {
        TodoData getSelectedTodo();
        TodoData findPrevTodo();
        TodoData findNextTodo();
        void updateTodoData(TodoData todo);
        void updateTodoDataStatus(TodoData todo);
        void deleteTodoData(TodoData todo);
    }
}