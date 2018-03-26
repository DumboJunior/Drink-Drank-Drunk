package com.exsample.drinkdrankdrunk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Results extends AppCompatActivity {
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        TextView tv = (TextView) findViewById(R.id.textView2);
        Intent intent = getIntent();
        s = intent.getStringExtra("s");
        s = "Din promille er: " + s;
        tv.setText(s);
    }
}
