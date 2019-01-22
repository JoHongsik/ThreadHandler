package com.example.threadpractice2;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private int i = 0;
    private TextView myi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myi = (TextView) findViewById(R.id.textview);
    }

    Handler handler = new Handler() {   // 쓰레드의 handler만이 메인쓰레드의 UI에 접근하여 수정할 수 있다.
        @Override
        public void handleMessage(Message msg) {
            updateThread(); //UI업데이트 메소드 호출.
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Thread myThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        handler.sendMessage(handler.obtainMessage()); //handler에게 메시지를 보내 UI업데이트를 하게 함.
                        Thread.sleep(1000); //1초에 한번 업데이트.
                    } catch (Throwable t) {
                    }
                }
            }
        });

        myThread.start();
    }

    private void updateThread() {
        i++;
        myi.setText(String.valueOf(i));
    }
}
