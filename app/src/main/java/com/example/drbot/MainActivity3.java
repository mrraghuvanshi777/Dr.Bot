package com.example.drbot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        //icon on actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.lol_foreground);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setupHyperlink();
    }

    private void setupHyperlink() {
        TextView linkTextView = findViewById(R.id.link);
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());

        TextView linkTextView1 = findViewById(R.id.link1);
        linkTextView1.setMovementMethod(LinkMovementMethod.getInstance());

    }
}