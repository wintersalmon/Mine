package com.nnm.team91.mine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nnm.team91.mine.data.CommonData;
import com.nnm.team91.mine.data.DiaryData;
import com.nnm.team91.mine.data.ExpenseData;
import com.nnm.team91.mine.data.TodoData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity {
    private Button saveTodoButton;
    private Button saveExpenseButton;
    private Button saveDiaryButton;
    private Button insertHashTagButton;

    private TextView dateTextView;
    private TextView timeTextView;
    private TextView keyTextView;
    private TextView hashTextView;
    private TextView contentsTextView;
    private TextView amountTextView;

    private CommonData commonData;

    private String diaryString;
    private String expenseString;

    private OnAddActivityInteractionListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final Context context = (Context) MainActivity.activity;
        if (context instanceof OnAddActivityInteractionListener) {
            mListener = (OnAddActivityInteractionListener) context;
        }

        dateTextView = (TextView) findViewById(R.id.add_text_date);
        timeTextView = (TextView) findViewById(R.id.add_text_time);
        keyTextView = (TextView) findViewById(R.id.add_text_key_tag);
        hashTextView = (TextView) findViewById(R.id.add_text_hash_tag);
        amountTextView = (TextView) findViewById(R.id.add_text_amount);
        contentsTextView = (TextView) findViewById(R.id.add_text_contents);

        saveTodoButton = (Button) findViewById(R.id.add_button_save_todo);
        saveDiaryButton = (Button) findViewById(R.id.add_button_save_diary);
        saveExpenseButton = (Button) findViewById(R.id.add_button_save_expense);
        insertHashTagButton = (Button) findViewById(R.id.add_button_insert_hash_tag);

        commonData = new CommonData();

        commonData.setDateTimeNow();
        diaryString = "";
        expenseString = "";

        dateTextView.setText(commonData.getDate());
        timeTextView.setText(commonData.getTime());
        amountTextView.setText(expenseString);
        contentsTextView.setText(diaryString);

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                final View dialogEditView = inflater.inflate(R.layout.dialog_edit_date, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
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
                        commonData.setDate(year,month + 1,day);

                        dateTextView.setText(commonData.getDate());
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 취소 출력 메세지 변경
                        Toast.makeText(AddActivity.this, "취소", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();

                // Set DatePicker Date to current selected date
                DatePicker datePicker = (DatePicker) dialogEditView.findViewById(R.id.dialog_edit_date_picker);
                int year = commonData.getYear();
                int month = commonData.getMonth();
                int day = commonData.getDay();
                datePicker.updateDate(year, month-1, day);

                dialog.show();
            }
        });

        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                final View dialogEditView = inflater.inflate(R.layout.dialog_edit_time, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                builder.setTitle("시간 선택"); //Dialog 제목
                builder.setView(dialogEditView); //위에서 inflater가 만든 dialogView 객체 세팅 (Customize)

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
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
                        commonData.setTime(hour,minute);

                        timeTextView.setText(commonData.getTime());
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 취소 출력 메세지 변경
                        Toast.makeText(AddActivity.this, "취소", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();

                // Set TimePicker Time to current selected time
                TimePicker timePicker = (TimePicker) dialogEditView.findViewById(R.id.dialog_edit_time_picker);
                int hour = commonData.getHour();
                int minute = commonData.getMinute();
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

        hashTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();

                final View dialogEditView = inflater.inflate(R.layout.dialog_edit_hash_tag, null);

                AlertDialog.Builder buider = new AlertDialog.Builder(AddActivity.this);
                buider.setTitle("해시 태그 선택"); //Dialog 제목
                buider.setView(dialogEditView); //위에서 inflater가 만든 dialogView 객체 세팅 (Customize)

                buider.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 해쉬태그 변경 기능 추가
                        EditText hashTag = (EditText) dialogEditView.findViewById(R.id.dialog_edit_hash_tag);
                        Editable editableHashTag = hashTag.getText();
                        String hashTagString = editableHashTag.toString();

                        commonData.setHashTag(hashTagString);

                        hashTextView.setText(commonData.getHasTagListString());
                        keyTextView.setText(commonData.getKeyTag());
                    }
                });

                buider.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 취소 출력 메세지 변경
                        Toast.makeText(AddActivity.this, "취소", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = buider.create();
                TextView hashTag = (EditText) dialogEditView.findViewById(R.id.dialog_edit_hash_tag);
                hashTag.setText(commonData.getHasTagListString());
                dialog.show();
            }
        });

        amountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();

                final View dialogEditView = inflater.inflate(R.layout.dialog_edit_number, null);

                AlertDialog.Builder buider = new AlertDialog.Builder(AddActivity.this);
                buider.setTitle("지출 입력"); //Dialog 제목
                buider.setView(dialogEditView); //위에서 inflater가 만든 dialogView 객체 세팅 (Customize)

                buider.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 해쉬태그 변경 기능 추가
                        EditText edittext = (EditText) dialogEditView.findViewById(R.id.dialog_edit_number_text);
                        Editable editableAmount = edittext.getText();
                        expenseString = editableAmount.toString();

                        amountTextView.setText(expenseString);
                    }
                });

                buider.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 취소 출력 메세지 변경
                        Toast.makeText(AddActivity.this, "취소", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = buider.create();
                TextView numbers = (EditText) dialogEditView.findViewById(R.id.dialog_edit_number_text);
                numbers.setText(expenseString);
                dialog.show();
            }
        });

        contentsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();

                final View dialogEditView = inflater.inflate(R.layout.dialog_edit_contents, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                builder.setTitle("내용 입력");
                builder.setView(dialogEditView);

                builder.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText edittext = (EditText) dialogEditView.findViewById(R.id.dialog_edit_contents_text);
                        Editable editableText = edittext.getText();
                        diaryString = editableText.toString();

                        contentsTextView.setText(diaryString);
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AddActivity.this, "취소", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();

                EditText edittext = (EditText) dialogEditView.findViewById(R.id.dialog_edit_contents_text);
                edittext.setText(diaryString, TextView.BufferType.EDITABLE);

                dialog.show();
            }
        });

        saveTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.insertTodoData(commonData.getDateTime(), 0, commonData.getHashTagList(), 0);
                Toast.makeText(AddActivity.this, "ToDo 저장 완료", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        saveDiaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check content exists
                if (diaryString.length() > 0) {
                    mListener.insertDiaryData(commonData.getDateTime(), diaryString, commonData.getHashTagList(), 0);
                    Toast.makeText(AddActivity.this, "Diary 저장 완료", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddActivity.this, "일기 내용이 비여있습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        saveExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expenseString.length() > 0) {
                    try {
                        int amount = Integer.valueOf(expenseString);
                        mListener.insertExpenseData(commonData.getDateTime(), amount, commonData.getHashTagList(), 0);
                        Toast.makeText(AddActivity.this, "Expense 저장 완료", Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (Exception e) {
                        Toast.makeText(AddActivity.this, "지출 입력값을 확인하세요", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddActivity.this, "지출 내용이 비여있습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public interface OnAddActivityInteractionListener {
        ArrayList<String> loadTopHashTag(int size);
        void insertTodoData(String datetimeStr, int status, ArrayList<String> hashtags, int keyTagIndex);
        void insertDiaryData(String datetimeStr, String contents, ArrayList<String> hashtags, int keyTagIndex);
        void insertExpenseData(String datetimeStr, int amount, ArrayList<String> hashtags, int keyTagIndex);
    }
}
