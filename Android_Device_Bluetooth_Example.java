package com.example.android_device_bluetooth_example;

import android.content.pm.PackageManager;
import android.os.Bundle;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView mac_address = findViewById(R.id.mac_address);

        // Create Bluetooth adapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
            if (device != null) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                //System.out.println(deviceName + " :" + deviceHardwareAddress); // Serial print the mac address
                mac_address.setText(deviceName + " :" + deviceHardwareAddress);
            }
        }

        // Connect to Wear OS watch
        String wearAddress = "94:45:60:99:68:56"; // The WearOS mac Address
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // Standard UUID for SPP
        wearDevice = bluetoothAdapter.getRemoteDevice(wearAddress);

        try {
            socket = wearDevice.createRfcommSocketToServiceRecord(uuid);
            socket.connect();
            // Sends Data over
        }
        catch (IOException e) {
            //System.out.println("Smth went wrong"); // Serial print the mac address
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
