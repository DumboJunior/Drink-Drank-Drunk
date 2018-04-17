package com.exsample.drinkdrankdrunk;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.channels.InterruptedByTimeoutException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.UUID;

public class Games extends NavigationDrawer implements AdapterView.OnItemClickListener{
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    public ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
    public DeviceListAdapter mDeviceListAdapter;
    static final String TAG = "Games";
    ListView lvNewDevices;

    Button btnStartConnection;
    Button btnSend;
    EditText etSend;
    Button btnSten;
    Button btnSaks;
    Button btnPapir;
    String valg;
    String results_ssp;

    TextView incomingMessages;
    StringBuilder messages;

    BluetoothConnectionService mBluetoothConnection;
    private static final UUID MY_UUID_INSECURE = UUID.fromString("209b01d8-2497-4a2d-e61d-79ddfca211e8");
    BluetoothDevice mBTDevice;
    int REQUEST_ENABLE_BT = 100;

    /**
     * Broadcast Receiver that detects bond state changes (Pairing status changes)
     */
    private final BroadcastReceiver mBroadcastReceiver4 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //3 cases:
                //case1: bonded already
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED){
                    Log.d(TAG, "BroadcastReceiver: BOND_BONDED.");
                    //inside BroadcastReceiver4
                    mBTDevice = mDevice;
                }
                //case2: creating a bone
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
                    Log.d(TAG, "BroadcastReceiver: BOND_BONDING.");
                }
                //case3: breaking a bond
                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                    Log.d(TAG, "BroadcastReceiver: BOND_NONE.");
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_games, null, false);
        mDrawerLayout.addView(contentView, 0);
        //setContentView(R.layout.activity_games);


        //Broadcasts when bond state changes (ie:pairing)
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver4, filter);

        btnStartConnection = (Button) findViewById(R.id.btnStartConnection);
        btnSend = (Button) findViewById(R.id.btnSend);
        etSend = (EditText) findViewById(R.id.editText);
        btnPapir = (Button) findViewById(R.id.papir);
        btnSaks = (Button) findViewById(R.id.saks);
        btnSten = (Button) findViewById(R.id.sten);
        incomingMessages = (TextView) findViewById(R.id.incomingMessage);
        messages = new StringBuilder();

        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiverMessage,new IntentFilter("incomingMessage"));

        btnStartConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startConnection();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] bytes = valg.getBytes(Charset.defaultCharset());
                mBluetoothConnection.write(bytes);

                etSend.setText("");
            }
        });
        btnSten.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View view){
                    valg = "sten";
            }
            });
        btnPapir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                valg = "papir";
            }
        });
        btnSaks.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                valg = "saks";
            }
        });
        lvNewDevices = (ListView) findViewById(R.id.lvNewDevices);
        mBTDevices = new ArrayList<>();
        lvNewDevices.setOnItemClickListener(Games.this);
    }

     BroadcastReceiver mReceiverMessage = new BroadcastReceiver() {
         @Override
         public void onReceive(Context context, Intent intent) {
             String text = intent.getStringExtra("theMessage");

             messages.append(text+"\n");
             if(text=="sten" || text=="saks"||text=="papir"){
                 if(text==valg){
                     results_ssp = "Uafgjort ["+text+" vs "+valg+"]";
                 }
                 if(text=="sten" && valg=="papir"){
                     results_ssp = "Du har vundet [sten vs papir]";
                 }
                 if(text=="sten" && valg=="saks"){
                     results_ssp = "Du tabte [sten vs saks]";
                 }
                 if(text=="saks" && valg=="papir"){
                     results_ssp = "Du tabte [saks vs papir]";
                 }
                 if(text=="saks" && valg=="sten"){
                     results_ssp = "Du har vundet [saks vs sten]";
                 }
                 if(text=="papir" && valg=="saks"){
                     results_ssp = "Du har vundet [papir vs saks]";
                 }
                 if(text=="papir" && valg=="sten"){
                     results_ssp = "Du tabte [papir vs sten]";
                 }
                 results_ssp = results_ssp+"\n";
                 incomingMessages.setText(results_ssp);
             }else{
                 incomingMessages.setText(messages);
             }


         }
     };

    //create method for starting connection
    //***remember the conncction will fail and app will crash if you haven't paired first
    public void startConnection(){
        startBTConnection(mBTDevice,MY_UUID_INSECURE);
    }

    public void startBTConnection(BluetoothDevice device, UUID uuid){
        Log.d(TAG,"startBTConnection: Initializing RFCOM Bluetooth Connection.");

        mBluetoothConnection.startClient(device,uuid);
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
        int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
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
        unregisterReceiver(mBroadcastReceiver4);
        unregisterReceiver(mReceiver);
        unregisterReceiver(mReceiverMessage);
    }

    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
        mBluetoothAdapter.cancelDiscovery();
        String deviceName = mBTDevices.get(i).getName();
        String deviceAddress = mBTDevices.get(i).getAddress();


        //create the bond.
        //NOTE: Requires API 17+? I think this is JellyBean
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
            mBTDevices.get(i).createBond();

            mBTDevice = mBTDevices.get(i);
            mBluetoothConnection = new BluetoothConnectionService(Games.this);
        }else{
            Toast.makeText(this,"Version er for dårlig",Toast.LENGTH_LONG).show();
        }
    }
}
