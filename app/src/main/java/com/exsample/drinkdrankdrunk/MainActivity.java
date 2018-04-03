package com.exsample.drinkdrankdrunk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    boolean gender = true;
    public String pro;
    public String kore;
    public String sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onToggleClicked(View view){
        boolean on = ((ToggleButton) view).isChecked();

        if(!on){
            gender = true;
        }else{
            gender = false;
        }
    }

    public void change_view(View view){
        Intent startNewView = new Intent(this, Results.class);

        EditText vaegt = (EditText) findViewById(R.id.editText);
        String value = vaegt.getText().toString();
        int vaegten = Integer.parseInt(value);

        EditText vaegt2 = (EditText) findViewById(R.id.editText2);
        String value2 = vaegt2.getText().toString();
        int vaegten2 = Integer.parseInt(value2);


        double promile = 0;

        if(gender == true){
           promile = vaegten2/(vaegten*0.7);
           sex = "0.7";
        }else{
            promile = vaegten2/(vaegten*0.55);
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
