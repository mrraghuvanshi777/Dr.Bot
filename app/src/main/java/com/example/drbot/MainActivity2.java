package com.example.drbot;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import android.app.Dialog;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    private Button button;
    public CheckBox[] ch = new CheckBox[10];

    Dialog dialog;
    LinearLayout l1;
    ScrollView sv;
    Button click, getMedicine;
    int checked = 0,clicked=0;
    int size;
    Dataget db;
    TextView tv1;
    String key = "";
    CheckBox[] t1;
    RadioGroup[] rg;
    RadioButton[][] t2;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contact:
                startActivity(new Intent(getApplicationContext(), MainActivity3.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        click = findViewById(R.id.click);

        //icon on actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.lol_foreground);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        click.setEnabled(true);
        l1 = findViewById(R.id.lv2);
        sv = findViewById(R.id.svr);
        rg = new RadioGroup[10];
        dialog = new Dialog(MainActivity2.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        t1 = new CheckBox[100];
        t2 = new RadioButton[100][100];
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        Button ok = dialog.findViewById(R.id.btn_yes);
        Button cancel = dialog.findViewById(R.id.btn_no);
        db = new Dataget(key);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.setVisibility(View.VISIBLE);

                size = db.s1.size();
                for (int i = 0; i < db.s1.size(); i++) {

                    t1[i] = new CheckBox(getApplicationContext());
                    t1[i].setText(db.s1.get(i));
                    t1[i].setTextColor(Color.parseColor("#f000e4ef"));
                    t1[i].setId(i);
             /*   ch[i].setText(db.s1.get(i));
                ch[i].setLayoutParams(paramss);
                l1.addView(ch[i]);*/
                    l1.addView(t1[i]);


                }

                dialog.dismiss();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        dialog.show();



        //Fetch data
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked=1;
                for (int i = 0; i < db.s1.size(); i++) {
                    if (t1[i].isChecked()) {
                        GradientDrawable gd = new GradientDrawable();

                   /* key=t1[i].getText().toString();
                   Dataget db1=new Dataget(key);
                   */
                        gd.setCornerRadius(20);
                        gd.setColor(Color.rgb(206, 250, 254));
                        tv1 = new TextView(getApplicationContext());

                        //tv1.setText(t1[i].getText());

                        tv1.setText(db.s1.get(i));
                        tv1.setTextColor(Color.BLUE);
                        tv1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        tv1.setPadding(20, 20, 20, 20);
                /*tv1.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                tv1.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);*/
                        tv1.setBackground(gd);
                        l1.addView(tv1);
                        checked += 1;

                        //add new checkbox for getting paricular stage of ill
                        rg[i]=new RadioGroup(getApplicationContext());
                        for (int j = 1; j <= 3; j++) {
                            t2[i][j - 1] = new RadioButton(getApplicationContext());
                            t2[i][j - 1].setText(db.map.get(db.s1.get(i) + j));
                            t2[i][j-1].setTextColor(Color.parseColor("#f000e4ef"));
                            rg[i].addView(t2[i][j - 1]);

                        }
                        l1.addView(rg[i]);

                    }

                }
                getMedicine = new Button(getApplicationContext());
                getMedicine.setText("Get Prescription");
                getMedicine.setBackgroundColor(Color.parseColor("#f000e4ef"));
                getMedicine.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                getMedicine.setTextColor(Color.WHITE);
                l1.addView(getMedicine);


            //get Prescription          1
                getMedicine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < size; i++) {
                            if (t1[i].isChecked()) {
                                for (int j = 1; j != 4; j++) {
                                    if (t2[i][j - 1].isChecked()) {
                                        String medi = db.map1.get(t1[i].getText().toString() + j);
                                        TextView tv = new TextView(getApplicationContext());
                                        tv.setTextColor(Color.parseColor("#f000e4ef"));
                                        tv.setTextSize(24);
                                        tv.setText(medi);
                                        l1.addView(tv);

                                    }
                                }
                            }
                            l1.removeView(rg[i]);
                        }
                    getMedicine.setVisibility(View.INVISIBLE);
                    }
                });
                for (int i = 0; i < size; i++) {
                    l1.removeView(t1[i]);
                }

                if(clicked>0){
                    click.setVisibility(View.INVISIBLE);
                }
            }


        });








    /*public void add(View view) {
        for (int i = 0; i < size; i++) {
            if (t1[i].isChecked()) {
                GradientDrawable gd = new GradientDrawable();

                   *//* key=t1[i].getText().toString();
                   Dataget db1=new Dataget(key);
                   *//*
                gd.setCornerRadius(20);
                gd.setColor(Color.rgb(206, 250, 254));
                tv1 = new TextView(getApplicationContext());

                //tv1.setText(t1[i].getText());

                tv1.setText(db.s1.get(i));
                tv1.setTextColor(Color.BLUE);
                tv1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tv1.setPadding(20, 20, 20, 20);
                *//*tv1.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                tv1.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);*//*
                tv1.setBackground(gd);
                l1.addView(tv1);
                checked += 1;

                //add new checkbox for getting paricular stage of ill

                for (int j = 1; j <= 3; j++) {
                    t2[i][j - 1] = new RadioButton(getApplicationContext());
                    t2[i][j - 1].setText(db.map.get(db.s1.get(i) + j));
                    rg.addView(t2[i][j-1]);

                }
                l1.addView(rg);

            }

        }
        getMedicine=new Button(getApplicationContext());
        getMedicine.setText("Get Prescription");
        getMedicine.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        getMedicine.setBackgroundColor(Color.parseColor("#f000e4ef"));
        l1.addView(getMedicine);

        //get Prescription
        getMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < checked+1; i++) {
                    if (t1[i].isChecked()) {
                        for (int j = 1; j != 4; j++) {
                            if (t2[i][j - 1].isChecked()) {
                                String medi = db.map1.get(t1[i].getText().toString() + j);
                                TextView tv = new TextView(getApplicationContext());
                                tv.setText(medi);
                                l1.addView(tv);

                            }
                        }
                    }
                    l1.removeView(rg);
                }
            }
        });
        for(int i=0;i<size;i++){
            l1.removeView(t1[i]);
        }


    }*/





              /*  public void medicine(){

                    for(int i=0;i<checked;i++){
                        for(int j=1;j<=3;j++){
                            if(t2[i][j-1].isChecked()){
                                String medi=db.map1.get(t1[i].getText().toString()+j);
                                TextView tv=new TextView(getApplicationContext());
                                tv.setText(medi);
                                l1.addView(tv);
                            }
                        }
                    }
                }*/


    }
}