package com.exsample.drinkdrankdrunk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Results extends AppCompatActivity {
    String s;
    String s2;
    String sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        TextView tv = (TextView) findViewById(R.id.textView2);
        Intent intent = getIntent();
        s = intent.getStringExtra("s");
        String s_new = "Din promille er:\n" + s;
        tv.setText(s_new);

        TextView tv3 = (TextView) findViewById(R.id.textView3);
        s2 = intent.getStringExtra("s2");
        tv3.setText(s2);


        sex = intent.getStringExtra("sex");
    }
    public void view_graph(View view){
        Intent graph_intent = new Intent(this,Metabolisme.class);

        graph_intent.putExtra("promil",s);
        graph_intent.putExtra("sex",sex);

        startActivity(graph_intent);
    }
}
