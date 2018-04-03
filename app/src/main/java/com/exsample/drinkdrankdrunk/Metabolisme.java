package com.exsample.drinkdrankdrunk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Metabolisme extends AppCompatActivity {
    String sex;
    String promil;
   // LineGraphSeries<DataPoint> series;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metabolisme);
        Intent intent = getIntent();
        sex = intent.getStringExtra("sex");
        promil = intent.getStringExtra("promil");
        /*
        double promille;
        promille = Double.parseDouble(promil);

        double y,x; x=0;
*/

        TextView promil_tv = (TextView) findViewById(R.id.textView5);
        promil_tv.setText(promil);

        TextView sex_tv = (TextView) findViewById(R.id.textView6);
        sex_tv.setText(sex);
/*
        GraphView graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();
        for(int i=0; i<500;i++){
        x= x+0.1;
        y=-115/(1000*sex)*x+promille;
        series.appendData(new DataPoint(x,y),true,500);
        }
        graph.addSeries(series);
*/


    }
}
