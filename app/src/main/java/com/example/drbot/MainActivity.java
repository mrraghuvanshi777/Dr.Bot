package com.example.drbot;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    ScrollView sv;
    ImageView logo;
    LinearLayout l1,l2;
    TextView[] t1=new TextView[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            l1 = (LinearLayout) findViewById(R.id.lv2);
                Timer t = new Timer();

                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                    }
                }, 2000);



        }



    /*public void add(View view) {

        sv=findViewById(R.id.svr);
        for(int i=0;i<10;i++){
            t1[i]=new TextView(this);
        t1[i].setText("hi");
        t1[i].setTextSize(132);
        l1.addView(t1[i]);

        }
        sv.post(new Runnable() {
            public void run() {
                sv.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
*/
    }

