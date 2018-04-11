package com.exsample.drinkdrankdrunk;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

public class MainActivity extends NavigationDrawer {
    boolean gender = true;
    public String pro;
    public String kore;
    public String sex;
    public int numberOfDrinks = 0;
    public String numberOfDrinks_string;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_main, null, false);
        mDrawerLayout.addView(contentView, 0);
        //setContentView(R.layout.activity_main);
    }
    public void onToggleClicked(View view){
        boolean on = ((ToggleButton) view).isChecked();

        if(!on){
            gender = true;
        }else{
            gender = false;
        }
    }
    public void actionButtonUp(View view){
            numberOfDrinks+=1;
            numberOfDrinks_string = Integer.toString(numberOfDrinks);
            TextView numberOfDrinks_tv = (TextView) findViewById(R.id.textView4);
            numberOfDrinks_tv.setText(numberOfDrinks_string);
    }

    public void actionButtonDown(View view){
        if(numberOfDrinks>0) {
            numberOfDrinks -= 1;
            numberOfDrinks_string = Integer.toString(numberOfDrinks);
            TextView numberOfDrinks_tv = (TextView) findViewById(R.id.textView4);
            numberOfDrinks_tv.setText(numberOfDrinks_string);
        }
    }

    public void changeView_Games(View view){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter != null) {
            Intent startGamesView = new Intent(this, Games.class);
            startActivity(startGamesView);
        }
    }


    public void change_view(View view){
        Intent startNewView = new Intent(this, Results.class);

        EditText vaegt = (EditText) findViewById(R.id.editText);
        String value = vaegt.getText().toString();
        int vaegten = Integer.parseInt(value);

        int avg_Alko_gram = 12;                                     //Gemmensnit på indhold af gram i en genstand (taget fra wiki).

        int alkohol = avg_Alko_gram*numberOfDrinks;

        double promile = 0;

        if(gender == true){
           promile = alkohol/(vaegten*0.7);
           sex = "0.7";
        }else{
            promile = alkohol/(vaegten*0.55);
            sex = "0.55";
        }
        promile = promile*100;
        promile = Math.floor(promile);
        promile = promile/100;

        pro = String.valueOf(promile);


        if (promile > 0.5) {
            kore = "Du må ikke køre endnu! Lad bilen stå!";
        }
        else if (promile < 0.4) {
            kore = "Du må gerne køre bil :)";
        }
        else {
            kore = "Tænk dig om en ekstra gang, du er tæt på grænsen!";
        }

        startNewView.putExtra("s",pro);
        startNewView.putExtra("s2", kore);
        startNewView.putExtra("sex",sex);
        startActivity(startNewView);

    }
}
