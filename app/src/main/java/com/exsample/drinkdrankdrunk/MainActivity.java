package com.exsample.drinkdrankdrunk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public final static String test = "com.exsample.drinkdrankdrunk.Results";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void change_view(View view){
        Intent startNewView = new Intent(this, Results.class);
        TextView text = (TextView) findViewById(R.id.textView2);
        String newString = text.getText().toString();

        startNewView.putExtra(test,newString);
        startActivity(startNewView);
    }
}
