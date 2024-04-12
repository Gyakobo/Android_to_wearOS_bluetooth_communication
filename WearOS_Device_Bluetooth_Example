package com.example.wearos_device_bluetooth_example.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import java.io.IOException
import java.util.*

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import com.example.wearos_device_bluetooth_example.R
import com.example.wearos_device_bluetooth_example.presentation.theme.WearOS_Device_Bluetooth_ExampleTheme

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.wearos_device_bluetooth_example.DataLayerListenerService
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import java.nio.charset.Charset

class MainActivity : ComponentActivity() {
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var serverSocket: BluetoothServerSocket? = null
    private var socket: BluetoothSocket? = null

    // Android UUID: 00001101-0000-1000-8000-00805F9B34FB
    private val MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") // Standard UUID for SPP


    // Not necessarily right :(
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        // Create a bluetooth adapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            Toast.makeText(this, "No Bluetooth", Toast.LENGTH_SHORT).show()
            return
        }
        else {
            Toast.makeText(this, "Ye Bluetooth", Toast.LENGTH_SHORT).show()
        }

        // Set theme
        setTheme(android.R.style.Theme_DeviceDefault)
        setContent {
            WearApp("Android")
        }

        // Listen for signals
        try {
            serverSocket = bluetoothAdapter!!.listenUsingRfcommWithServiceRecord("BluetoothServer", MY_UUID)
            val acceptThread = AcceptThread()
            acceptThread.start()
            // Now you can receive data from the socket
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // My personal code - don't forget to uncomment me!
        //startService(Intent(this, DataLayerListenerService::class.java))
    }

    // Method to handle incoming connection and data reception
    private inner class AcceptThread : Thread() {
        private val handler: Handler = Handler(Looper.getMainLooper())

        override fun run() {
            try {
                socket = serverSocket!!.accept()
                val inputStream = socket!!.inputStream

                // Read data from the input stream
                val buffer = ByteArray(1024)
                var bytes: Int

                while (true) {
                    bytes = inputStream.read(buffer)
                    val incomingMessage = String(buffer, 0, bytes, Charset.defaultCharset())
                    handler.post {
                        showToast("Received: $incomingMessage")
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    // Method to show toast message
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}


@Composable
fun WearApp(greetingName: String) {
    WearOS_Device_Bluetooth_ExampleTheme {
        Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
                contentAlignment = Alignment.Center
        ) {
            TimeText()
            Greeting(greetingName = greetingName)
        }
    }
}

@Composable
fun Greeting(greetingName: String) {
    Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            text = stringResource(R.string.hello_world, greetingName)
    )
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp("Preview Android")
}
