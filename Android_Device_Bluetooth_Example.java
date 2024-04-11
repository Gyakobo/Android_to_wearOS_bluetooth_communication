package com.example.android_device_bluetooth_example;

import android.content.pm.PackageManager;
import android.os.Bundle;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.view.View;

import com.example.android_device_bluetooth_example.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice wearDevice;
    private BluetoothSocket socket;
    private OutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button myBtn_1 = findViewById(R.id.my_button_1); // Main button
        Button myBtn_2 = findViewById(R.id.my_button_1); // Main button
        TextView mac_address = findViewById(R.id.mac_address);

        // Create Bluetooth adapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        /*for (BluetoothDevice device : pairedDevices) {
            if (device != null) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                //System.out.println(deviceName + " :" + deviceHardwareAddress); // Serial print the mac address
                mac_address.setText(deviceName + " :" + deviceHardwareAddress);
            }
        }*/

        // This program incentivices that you have only one device (the Google Pixel) connected
        BluetoothDevice device = (BluetoothDevice) pairedDevices.toArray()[0];
        if (device != null) {
            String deviceName = device.getName();
            String deviceHardwareAddress = device.getAddress(); // MAC address
            //System.out.println(deviceName + " :" + deviceHardwareAddress); // Serial print the mac address
            mac_address.setText(deviceName + " :" + deviceHardwareAddress);
        }

        // Connect to Wear OS watch
        String wearAddress = "94:45:60:99:68:56"; // The WearOS mac Address
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // Standard UUID for SPP
        wearDevice = bluetoothAdapter.getRemoteDevice(wearAddress);

        myBtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Initializes a socket connection
                    socket = wearDevice.createRfcommSocketToServiceRecord(uuid);
                    socket.connect();
                    Toast.makeText(MainActivity.this, "Sent Message 1", Toast.LENGTH_SHORT).show();

                    // Sends Data over
                    outputStream = socket.getOutputStream();
                    sendData("Message 1");
                }
                catch (IOException e) {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        myBtn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Initializes a socket connection
                    socket = wearDevice.createRfcommSocketToServiceRecord(uuid);
                    socket.connect();
                    Toast.makeText(MainActivity.this, "Sent Message 2", Toast.LENGTH_SHORT).show();

                    // Sends Data over
                    outputStream = socket.getOutputStream();
                    sendData("Message 2");
                }
                catch (IOException e) {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });


    }

    private void sendData(String message) {
        try {
            outputStream.write(message.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
