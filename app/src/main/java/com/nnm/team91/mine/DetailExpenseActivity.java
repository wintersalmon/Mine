package com.nnm.team91.mine;

import android.content.Context;
import android.content.DialogInterface;
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

import com.nnm.team91.mine.data.ExpenseData;

public class DetailExpenseActivity extends AppCompatActivity {
    private Button prevButton;
    private Button nextButton;
    private Button deleteButton;

    private TextView dateTextView;
    private TextView timeTextView;
    private TextView amountTextView;
    private TextView keyTextView;
    private TextView hashTextView;

    ExpenseData selectedExpense;

    private OnExpenseDetailActivityInteractionListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_expense);

        final Context context = (Context) MainActivity.activity;
        if (context instanceof OnExpenseDetailActivityInteractionListener) {
            mListener = (OnExpenseDetailActivityInteractionListener) context;
        }

        dateTextView = (TextView) findViewById(R.id.detail_expense_date);
        timeTextView = (TextView) findViewById(R.id.detail_expense_time);
        amountTextView = (TextView) findViewById(R.id.detail_expense_amount);
        keyTextView = (TextView) findViewById(R.id.detail_expense_key_tag);
        hashTextView = (TextView) findViewById(R.id.detail_expense_hash_tag);

        prevButton = (Button) findViewById(R.id.detail_expense_button_prev);
        nextButton = (Button) findViewById(R.id.detail_expense_button_next);
        deleteButton = (Button) findViewById(R.id.detail_expense_button_delete);

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                final View dialogEditView = inflater.inflate(R.layout.dialog_edit_date, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailExpenseActivity.this);
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
                        selectedExpense.setDate(year,month + 1,day);

                        mListener.updateExpenseData(selectedExpense);

                        dateTextView.setText(selectedExpense.getDate());
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 취소 출력 메세지 변경
                        Toast.makeText(DetailExpenseActivity.this, "취소", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();

                // Set DatePicker Date to current selected date
                DatePicker datePicker = (DatePicker) dialogEditView.findViewById(R.id.dialog_edit_date_picker);

                int year = selectedExpense.getYear();
                int month =  selectedExpense.getMonth();
                int day = selectedExpense.getDay();
                datePicker.updateDate(year, month-1, day);

                dialog.show();
            }
        });

        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                final View dialogEditView = inflater.inflate(R.layout.dialog_edit_time, null);

                AlertDialog.Builder buider = new AlertDialog.Builder(DetailExpenseActivity.this);
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
                        MainActivity main = (MainActivity) MainActivity.activity;
                        selectedExpense.setTime(hour,minute);

                        mListener.updateExpenseData(selectedExpense);

                        timeTextView.setText(selectedExpense.getTime());
//                        timeTextView.invalidate();
                    }
                });

                buider.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 취소 출력 메세지 변경
                        Toast.makeText(DetailExpenseActivity.this, "취소", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = buider.create();

                // Set TimePicker Time to current selected time
                TimePicker timePicker = (TimePicker) dialogEditView.findViewById(R.id.dialog_edit_time_picker);
                int hour = selectedExpense.getHour();
                int minute = selectedExpense.getMinute();
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

                AlertDialog.Builder buider = new AlertDialog.Builder(DetailExpenseActivity.this);
                buider.setTitle("해시 태그 선택"); //Dialog 제목
                buider.setView(dialogEditView); //위에서 inflater가 만든 dialogView 객체 세팅 (Customize)

                buider.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 해쉬태그 변경 기능 추가
                        EditText hashTag = (EditText) dialogEditView.findViewById(R.id.dialog_edit_hash_tag);
                        Editable editableHashTag = hashTag.getText();
                        String hashTagString = editableHashTag.toString();

                        selectedExpense.setHashTag(hashTagString);

                        mListener.updateExpenseData(selectedExpense);

                        hashTextView.setText(selectedExpense.getHasTagListString());
                        keyTextView.setText(selectedExpense.getKeyTag());
                    }
                });

                buider.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 취소 출력 메세지 변경
                        Toast.makeText(DetailExpenseActivity.this, "취소", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = buider.create();
                TextView hashTag = (EditText) dialogEditView.findViewById(R.id.dialog_edit_hash_tag);
                hashTag.setText(selectedExpense.getHasTagListString());
                dialog.show();
            }
        });

        amountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();

                final View dialogEditView = inflater.inflate(R.layout.dialog_edit_number, null);

                AlertDialog.Builder buider = new AlertDialog.Builder(DetailExpenseActivity.this);
                buider.setTitle("지출 입력"); //Dialog 제목
                buider.setView(dialogEditView); //위에서 inflater가 만든 dialogView 객체 세팅 (Customize)

                buider.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 해쉬태그 변경 기능 추가
                        EditText edittext = (EditText) dialogEditView.findViewById(R.id.dialog_edit_number_text);
                        Editable editableAmount = edittext.getText();
                        String amountString = editableAmount.toString();

                        selectedExpense.setAmount(Integer.valueOf(amountString));

                        mListener.updateExpenseData(selectedExpense);

                        amountTextView.setText(selectedExpense.getAmount());
                    }
                });

                buider.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 취소 출력 메세지 변경
                        Toast.makeText(DetailExpenseActivity.this, "취소", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = buider.create();
                TextView hashTab = (EditText) dialogEditView.findViewById(R.id.dialog_edit_number_text);
                hashTab.setText(selectedExpense.getAmount().substring(1));
                dialog.show();
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpenseData expense = mListener.findPrevExpense();
                if (expense != null) {
                    selectedExpense = expense;
                    reloadViewContents();
                } else {
                    Toast.makeText(DetailExpenseActivity.this, "이전 이벤트가 없습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpenseData expense = mListener.findNextExpense();
                if (expense != null) {
                    selectedExpense = expense;
                    reloadViewContents();
                } else {
                    Toast.makeText(DetailExpenseActivity.this, "다음 이벤트가 없습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016. 12. 7. add delete dialog function
                AlertDialog.Builder buider = new AlertDialog.Builder(DetailExpenseActivity.this);
                buider.setMessage("삭제하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mListener.deleteExpenseData(selectedExpense);
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
        if (selectedExpense == null) {
            // if selectedTodo data is null finish the view
            finish();
        }
    }

    public void setTodoData() {
        selectedExpense = mListener.getSelectedExpense();
    }

    private void setViewData() {
        if (selectedExpense != null) {
            dateTextView.setText(selectedExpense.getDate());
            timeTextView.setText(selectedExpense.getTime());
            amountTextView.setText(selectedExpense.getAmount());
            keyTextView.setText(selectedExpense.getKeyTag());

            String hashTagString = "";
            for (String tag : selectedExpense.getHashTagList()) {
                hashTagString += tag + " ";
            }
            hashTextView.setText(hashTagString);
        }
    }

    public interface OnExpenseDetailActivityInteractionListener {
        ExpenseData getSelectedExpense();
        ExpenseData findPrevExpense();
        ExpenseData findNextExpense();
        void updateExpenseData(ExpenseData expense);
        void deleteExpenseData(ExpenseData expense);
    }
}
