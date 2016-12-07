package com.nnm.team91.mine;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.nnm.team91.mine.data.TodoData;

import java.util.ArrayList;

public class DetailTodoActivity extends AppCompatActivity {
    private Button prevButton;
    private Button nextButton;
    private Button editButton;
    private Button deleteButton;


    private TextView dateTextView;
    private TextView timeTextView;
    private CheckBox statusCheckBox;
    private TextView keyTextView;
    private TextView hashTextView;

    private TodoData selectedTodo;

    private int position;
    private int commonId;
    private String date;
    private String time;
    private boolean status;
    private String keyTag;
    private String hashTagString;
    private ArrayList<String> hashTagList;

    private OnDetailActivityInteractionListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_todo);

        // TODO: 2016. 12. 8. use better method to gain MainActivity instance
        Context context = (Context) MainActivity.activity;
        if (context instanceof OnDetailActivityInteractionListener ) {
            mListener = (OnDetailActivityInteractionListener) context;
        }

        dateTextView = (TextView) findViewById(R.id.detail_todo_date);
        timeTextView = (TextView) findViewById(R.id.detail_todo_time);
        statusCheckBox = (CheckBox) findViewById(R.id.detail_todo_status);
        keyTextView = (TextView) findViewById(R.id.detail_todo_key_tag);
        hashTextView = (TextView) findViewById(R.id.detail_todo_hash_tag);

        prevButton = (Button) findViewById(R.id.detail_todo_button_prev);
        nextButton = (Button) findViewById(R.id.detail_todo_button_next);
        editButton = (Button) findViewById(R.id.detail_todo_button_edit);
        deleteButton = (Button) findViewById(R.id.detail_todo_button_delete);

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();

                final View dialogEditView = inflater.inflate(R.layout.dialog_edit_date, null);

                AlertDialog.Builder buider = new AlertDialog.Builder(DetailTodoActivity.this);
                buider.setTitle("날짜 선택"); //Dialog 제목
                buider.setIcon(android.R.drawable.ic_menu_add); //제목옆의 아이콘 이미지(원하는 이미지 설정)
                buider.setView(dialogEditView); //위에서 inflater가 만든 dialogView 객체 세팅 (Customize)

                buider.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 메인의 선택된 날짜 변경 & 데이터 리로드
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
                //                dialog.setCanceledOnTouchOutside(false);
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
                buider.setIcon(android.R.drawable.ic_menu_add); //제목옆의 아이콘 이미지(원하는 이미지 설정)
                buider.setView(dialogEditView); //위에서 inflater가 만든 dialogView 객체 세팅 (Customize)

                buider.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 메인의 선택된 날짜 변경 & 데이터 리로드
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
                //                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });

        hashTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();

                final View dialogEditView = inflater.inflate(R.layout.dialog_edit_hash_tag, null);

                AlertDialog.Builder buider = new AlertDialog.Builder(DetailTodoActivity.this);
                buider.setTitle("해시 태그 선택"); //Dialog 제목
                buider.setIcon(android.R.drawable.ic_menu_add); //제목옆의 아이콘 이미지(원하는 이미지 설정)
                buider.setView(dialogEditView); //위에서 inflater가 만든 dialogView 객체 세팅 (Customize)

                buider.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 메인의 선택된 날짜 변경 & 데이터 리로드
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
                //                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016. 12. 7. add prev function
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016. 12. 7. add next function
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016. 12. 7. add delete dialog function
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
    }

    public void setTodoData() {
        Bundle b = getIntent().getExtras();
        if (b != null) {
            position = b.getInt("position");
            selectedTodo = mListener.getSelectedTodo(position);
            if (selectedTodo != null) {
                commonId = selectedTodo.getId();
                date = selectedTodo.getDate();
                time = selectedTodo.getTime();
                status = selectedTodo.getStatus();
                keyTag = selectedTodo.getKeyTag();
                hashTagList = selectedTodo.getHashTagList();
            }
        }
    }

    private void setViewData() {
        dateTextView.setText(date);
        timeTextView.setText(time);
        statusCheckBox.setChecked(status);
        keyTextView.setText(keyTag);

        hashTagString = "";
        for(String s : hashTagList) {
            hashTagString += s + " ";
        }
        hashTextView.setText(hashTagString);
    }

    public interface OnDetailActivityInteractionListener {
        TodoData getSelectedTodo(int position);
    }
}