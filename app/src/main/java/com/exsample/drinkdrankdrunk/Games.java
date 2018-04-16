package com.exsample.drinkdrankdrunk;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;

public class Games extends NavigationDrawer implements AdapterView.OnItemClickListener{
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    public ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
    public DeviceListAdapter mDeviceListAdapter;
    static final String TAG = "Games";
    ListView lvNewDevices;
    int REQUEST_ENABLE_BT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_games, null, false);
        mDrawerLayout.addView(contentView, 0);
        //setContentView(R.layout.activity_games);
        lvNewDevices = (ListView) findViewById(R.id.lvNewDevices);
        mBTDevices = new ArrayList<>();
        lvNewDevices.setOnItemClickListener(Games.this);
    }
    // Create a BroadcastReceiver for ACTION_FOUND.
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG,"onReceive: action...");

            if (action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device = intent.getParcelableExtra (BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);
                mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_adapter_view, mBTDevices);
                lvNewDevices.setAdapter(mDeviceListAdapter);
            }
        }
    };
    public void refress_recycleview(View view){
        Log.d(TAG,"Button Pressed....");
        if(mBluetoothAdapter.isDiscovering()){
            Log.d(TAG,"Cancel");
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothAdapter.startDiscovery();
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mReceiver, filter);
        }
        if(!mBluetoothAdapter.isDiscovering()){
            Log.d(TAG,"Starting");
            mBluetoothAdapter.startDiscovery();
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mReceiver, filter);
        }
    }
    public void discoverability_button(View view){
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        // discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);   //Sætter synligheden til et bedstemt
        startActivity(discoverableIntent);
    }
    public void bluetooth_on_of(View view){
        if(!mBluetoothAdapter.isEnabled()){
            Log.d(TAG, "bluetooth_on_off: enabling BT.");
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);
        }
        if(mBluetoothAdapter.isEnabled()) {
            Log.d(TAG, "bluetooth_on_off: disabling BT.");
            mBluetoothAdapter.disable();
        }
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
    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mReceiver);
    }

    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
        mBluetoothAdapter.cancelDiscovery();
        String deviceName = mBTDevices.get(i).getName();
        String deviceAddress = mBTDevices.get(i).getAddress();


        //create the bond.
        //NOTE: Requires API 17+? I think this is JellyBean
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
            mBTDevices.get(i).createBond();
        }else{
            Toast.makeText(this,"Version er for dårlig",Toast.LENGTH_LONG).show();
        }
    }
}
