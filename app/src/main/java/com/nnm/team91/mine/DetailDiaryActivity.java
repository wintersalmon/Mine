package com.nnm.team91.mine;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nnm.team91.mine.data.DiaryData;

public class DetailDiaryActivity extends AppCompatActivity {
    private Button prevButton;
    private Button nextButton;
    private Button deleteButton;


    private TextView dateTextView;
    private TextView timeTextView;
    private TextView keyTextView;
    private TextView hashTextView;
    private TextView contentsTextView;

    private DiaryData selectedDiary;

    private OnDiaryDetailActivityInteractionListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_diary);

        // TODO: 2016. 12. 8. use better method to gain MainActivity instance
        final Context context = (Context) MainActivity.activity;
        if (context instanceof OnDiaryDetailActivityInteractionListener) {
            mListener = (OnDiaryDetailActivityInteractionListener) context;
        }

        dateTextView = (TextView) findViewById(R.id.detail_diary_date);
        timeTextView = (TextView) findViewById(R.id.detail_diary_time);
        keyTextView = (TextView) findViewById(R.id.detail_diary_key_tag);
        hashTextView = (TextView) findViewById(R.id.detail_diary_hash_tag);
        contentsTextView = (TextView) findViewById(R.id.detail_diary_contents);

        prevButton = (Button) findViewById(R.id.detail_diary_button_prev);
        nextButton = (Button) findViewById(R.id.detail_diary_button_next);
        deleteButton = (Button) findViewById(R.id.detail_diary_button_delete);



        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                final View dialogEditView = inflater.inflate(R.layout.dialog_edit_date, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailDiaryActivity.this);
                builder.setTitle("날짜 선택"); //Dialog 제목
//                buider.setIcon(android.R.drawable.ic_menu_add); //제목옆의 아이콘 이미지(원하는 이미지 설정)
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
                        selectedDiary.setDate(year,month + 1,day);

                        mListener.updateDiaryData(selectedDiary);

                        dateTextView.setText(selectedDiary.getDate());
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 취소 출력 메세지 변경
                        Toast.makeText(DetailDiaryActivity.this, "취소", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();

                // Set DatePicker Date to current selected date
                DatePicker datePicker = (DatePicker) dialogEditView.findViewById(R.id.dialog_edit_date_picker);
                int year = selectedDiary.getYear();
                int month = selectedDiary.getMonth();
                int day = selectedDiary.getDay();
                datePicker.updateDate(year, month-1, day);

                dialog.show();
            }
        });

        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                final View dialogEditView = inflater.inflate(R.layout.dialog_edit_time, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailDiaryActivity.this);
                builder.setTitle("시간 선택"); //Dialog 제목
//                builder.setIcon(android.R.drawable.ic_menu_add); //제목옆의 아이콘 이미지(원하는 이미지 설정)
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
                        selectedDiary.setTime(hour,minute);

                        mListener.updateDiaryData(selectedDiary);

                        timeTextView.setText(selectedDiary.getTime());
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 취소 출력 메세지 변경
                        Toast.makeText(DetailDiaryActivity.this, "취소", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();

                // Set TimePicker Time to current selected time
                TimePicker timePicker = (TimePicker) dialogEditView.findViewById(R.id.dialog_edit_time_picker);
                int hour = selectedDiary.getHour();
                int minute = selectedDiary.getMinute();
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

                AlertDialog.Builder buider = new AlertDialog.Builder(DetailDiaryActivity.this);
                buider.setTitle("해시 태그 선택"); //Dialog 제목
                buider.setIcon(android.R.drawable.ic_menu_add); //제목옆의 아이콘 이미지(원하는 이미지 설정)
                buider.setView(dialogEditView); //위에서 inflater가 만든 dialogView 객체 세팅 (Customize)

                buider.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 해쉬태그 변경 기능 추가
                        EditText hashTag = (EditText) dialogEditView.findViewById(R.id.dialog_edit_hash_tag);
                        Editable editableHashTag = hashTag.getText();
                        String hashTagString = editableHashTag.toString();

                        selectedDiary.setHashTag(hashTagString);

                        mListener.updateDiaryData(selectedDiary);

                        hashTextView.setText(selectedDiary.getHasTagListString());
                        keyTextView.setText(selectedDiary.getKeyTag());
                    }
                });

                buider.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 취소 출력 메세지 변경
                        Toast.makeText(DetailDiaryActivity.this, "취소", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = buider.create();
                TextView hashTag = (EditText) dialogEditView.findViewById(R.id.dialog_edit_hash_tag);
                hashTag.setText(selectedDiary.getHasTagListString());
                dialog.show();
            }
        });

        contentsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();

                final View dialogEditView = inflater.inflate(R.layout.dialog_edit_contents, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailDiaryActivity.this);
                builder.setTitle("내용 입력");
                builder.setView(dialogEditView);

                builder.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText edittext = (EditText) dialogEditView.findViewById(R.id.dialog_edit_contents_text);
                        Editable editableText = edittext.getText();
                        String contentsString = editableText.toString();
                        selectedDiary.setText(contentsString);

                        mListener.updateDiaryData(selectedDiary);

                        contentsTextView.setText(contentsString);
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DetailDiaryActivity.this, "취소", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();

                EditText edittext = (EditText) dialogEditView.findViewById(R.id.dialog_edit_contents_text);
                edittext.setText(selectedDiary.getText(), TextView.BufferType.EDITABLE);

                dialog.show();
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryData diary = mListener.findPrevDiary();
                if (diary != null) {
                    selectedDiary = diary;
                    reloadViewContents();
                } else {
                    Toast.makeText(DetailDiaryActivity.this, "이전 이벤트가 없습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryData diary = mListener.findNextDiary();
                if (diary != null) {
                    selectedDiary = diary;
                    reloadViewContents();
                } else {
                    Toast.makeText(DetailDiaryActivity.this, "다음 이벤트가 없습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016. 12. 7. add delete dialog function
                AlertDialog.Builder buider = new AlertDialog.Builder(DetailDiaryActivity.this);
                buider.setMessage("삭제하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mListener.deleteDiaryData(selectedDiary);
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
        if (selectedDiary == null) {
            // if selectedTodo data is null finish the view
            finish();
        }
    }

    public void setTodoData() {
        selectedDiary = mListener.getSelectedDiary();
    }

    private void setViewData() {
        if (selectedDiary != null) {
            dateTextView.setText(selectedDiary.getDate());
            timeTextView.setText(selectedDiary.getTime());
            contentsTextView.setText(selectedDiary.getText());
            keyTextView.setText(selectedDiary.getKeyTag());

            String hashTagString = "";
            for (String tag : selectedDiary.getHashTagList()) {
                hashTagString += tag + " ";
            }
            hashTextView.setText(hashTagString);
        }
    }

    public interface OnDiaryDetailActivityInteractionListener {
        DiaryData getSelectedDiary();
        DiaryData findPrevDiary();
        DiaryData findNextDiary();
        void updateDiaryData(DiaryData diary);
        void deleteDiaryData(DiaryData diary);
    }
}
