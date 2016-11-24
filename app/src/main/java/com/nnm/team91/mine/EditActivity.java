package com.nnm.team91.mine;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {
    Button timingBtn, mapsBtn, sharpBtn, checkBtn;
    TextView dateTxt;
    EditText contentTxt;
    RelativeLayout activity_edit;
    public static final int REQUEST_CODE = 101;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //TODO: dateformat으로 어떻게 해보자

        activity_edit = (RelativeLayout) findViewById(R.id.activity_edit);
        dateTxt = (TextView) findViewById(R.id.now_txt);
        contentTxt = (EditText) findViewById(R.id.content_txt);
        mapsBtn = (Button)findViewById(R.id.maps_btn);


        // 현재 시간을 msec으로 구한다.
        long now = System.currentTimeMillis();

        // 현재 시간을 저장 한다.
        Date date = new Date(now);
        SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        /*
        *        시간 포맷 지정
                SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
                SimpleDateFormat CurTimeFormat = new SimpleDateFormat("HH시 mm분");
                SimpleDateFormat CurYearFormat = new SimpleDateFormat("yyyy");
                SimpleDateFormat CurMonthFormat = new SimpleDateFormat("MM");
                SimpleDateFormat CurDayFormat = new SimpleDateFormat("dd");
                SimpleDateFormat CurHourFormat = new SimpleDateFormat("HH");
                SimpleDateFormat CurMinuteFormat = new SimpleDateFormat("mm");
        * */
        String strCurDate = CurDateFormat.format(date);
        dateTxt.setText(strCurDate);

        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this, MapsActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
/*

       //TODO: 기능 구현 (리스너 연결 후 기능 구현하지 않을 시 오류발생으로 주석처리)
       timingBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               long now = System.currentTimeMillis();
               Date date1 = new Date(now);
               SimpleDateFormat CurTimeFormat = new SimpleDateFormat("HH시 mm분");
               String strCurTime = CurTimeFormat.format(date1);
                contentTxt.setText(strCurTime);
           }
       });




        sharpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });  */

    } // end of onCreate

    public void DiaryAlarm(View v) {
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, EditActivity.class), 0);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setWhen(System.currentTimeMillis());
        builder.setContentTitle("스케쥴");
        builder.setContentText("약속 시간: 할 일");
        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        Notification.InboxStyle inboxStyle = new Notification.InboxStyle(builder);
        inboxStyle.addLine("장소");
        inboxStyle.addLine("사람");

        builder.setStyle(inboxStyle);
        nm.notify(1111, builder.build());
    }


}

