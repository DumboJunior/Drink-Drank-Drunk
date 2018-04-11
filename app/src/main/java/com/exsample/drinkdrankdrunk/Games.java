package com.exsample.drinkdrankdrunk;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.nio.channels.InterruptedByTimeoutException;

public class Games extends NavigationDrawer {

    int REQUEST_ENABLE_BT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_games, null, false);
        mDrawerLayout.addView(contentView, 0);
        //setContentView(R.layout.activity_games);
    }
    protected void onStart(){
        super.onStart();
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }
    public void changeview_Connect2Game(View view){
        Intent connect_intent = new Intent(this, ConnectToGame.class);
        startActivity(connect_intent);
    }
    public void changeview_HostGame(View view){
        Intent hostGame_intent = new Intent(this,HostGame.class);
        startActivity(hostGame_intent);
    }
}
