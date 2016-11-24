package com.nnm.team91.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by wintersalmon on 2016. 11. 11..
 */

public class SplashActivity extends Activity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;  // 지연처리, 보통 3초

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler hd = new Handler();
        hd.postDelayed(new SplashHandler(), SPLASH_DISPLAY_LENGTH);
    }

    private class SplashHandler implements Runnable { // TODO: mine_icon.png mipmap 으로 수정하기
        @Override
        public void run() {
            startActivity(new Intent(getApplication(), MainActivity.class)); // 로딩이 끝난 후 이동할 Activity
            SplashActivity.this.finish(); // 로딩 페이지 Activity stack에서 제거
        }
    }
}