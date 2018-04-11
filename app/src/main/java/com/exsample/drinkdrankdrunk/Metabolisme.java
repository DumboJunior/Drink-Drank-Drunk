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
    LineGraphSeries<DataPoint> series;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metabolisme);
        Intent intent = getIntent();
        sex = intent.getStringExtra("sex");            // Får variabler fra anden activity
        promil = intent.getStringExtra("promil");

        double promille;
        promille = Double.parseDouble(promil);          // String to Double
        double gender;
        gender = Double.parseDouble(sex);               // String to Double



        TextView promil_tv = (TextView) findViewById(R.id.textView5);
        promil_tv.setText(promil);

        /****** find y0****
          int imax=0; double y0=1,x0=0;
          while(y0!=0){
              x0=x0+0.1;
              y0=-115/(1000 * gender)*x0+promille;
              imax=imax+1;
          }

        **** end *******/




        double y,x; x=0;
        int check=0;
        GraphView graph = (GraphView) findViewById(R.id.graph);                     /*Plotter grafen*/
        series = new LineGraphSeries<DataPoint>();
        for(int i=0; i<240;i++){                                                   // 240 burde blive skiftet ud med imax
        x= x+0.1;
        y=-115/(1000 * gender)*x+promille;
        if(y>0&& y<0.5 && check==0){
            check=1;
            double x_time2Drive=x;
            x_time2Drive *=100;
            x_time2Drive=Math.floor(x_time2Drive);
            x_time2Drive=x_time2Drive/100;
            String x_time2Drive_string = Double.toString(x_time2Drive);
            String timeThenSober=getString(R.string.timeWhenSober)+" " +x_time2Drive_string+" "+getString(R.string.timeWhenSober2);
            promil_tv.setText(timeThenSober);
            }
        if(y>=0){
        series.appendData(new DataPoint(x,y),true,240);}// 240 burde blive skiftet ud med imax
        }
        graph.addSeries(series);


    }
}
