package com.example.sathsaradahana;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;  // Add this import for logging

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sathsaradahana.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";  // Tag for logging
    private static final int REQUEST_BLUETOOTH_PERMISSIONS = 1;
    private ActivityMainBinding binding;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private OutputStream outputStream;
    private static final String HC06_ADDRESS = "98:D3:61:F9:49:DA";


    private static final UUID HC06_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Bluetooth
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish(); // Exit app
            return;
        }

        // Attempt to connect to Bluetooth device
        checkBluetoothPermissions();

        // Set button listeners
        setButtonListeners();
    }

    private void checkBluetoothPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            // For Android 12 and higher
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        android.Manifest.permission.BLUETOOTH_CONNECT,
                        android.Manifest.permission.BLUETOOTH_SCAN
                }, REQUEST_BLUETOOTH_PERMISSIONS);
            } else {
                connectBluetooth();
            }
        } else {
            // For Android 11 and lower
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.BLUETOOTH,
                        android.Manifest.permission.BLUETOOTH_ADMIN
                }, REQUEST_BLUETOOTH_PERMISSIONS);
            } else {
                connectBluetooth();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_BLUETOOTH_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with Bluetooth connection
                connectBluetooth();
            } else {
                Toast.makeText(this, "Bluetooth permissions are required", Toast.LENGTH_SHORT).show();
                finish(); // Exit app if permissions are not granted
            }
        }
    }



//    private void connectBluetooth() {
//        try {
//            BluetoothDevice device = bluetoothAdapter.getRemoteDevice(HC05_ADDRESS);
//            bluetoothSocket = device.createRfcommSocketToServiceRecord(HC05_UUID);
//            bluetoothSocket.connect();
//            outputStream = bluetoothSocket.getOutputStream();
//            Toast.makeText(this, "Bluetooth Connected", Toast.LENGTH_SHORT).show();
//            Log.d(TAG, "Bluetooth connected successfully");
//        } catch (IOException e) {
//            Log.e(TAG, "Bluetooth connection failed", e);
//            Toast.makeText(this, "Bluetooth Connection Failed", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void connectBluetooth() {
        try {
            BluetoothDevice device = bluetoothAdapter.getRemoteDevice(HC06_ADDRESS);
            bluetoothSocket = device.createRfcommSocketToServiceRecord(HC06_UUID);
            bluetoothSocket.connect();
            outputStream = bluetoothSocket.getOutputStream();
            Toast.makeText(this, "Bluetooth Connected", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Bluetooth connected successfully");
        } catch (IOException e) {
            Log.e(TAG, "Bluetooth connection failed: " + e.getMessage(), e);
            Toast.makeText(this, "Bluetooth Connection Failed", Toast.LENGTH_SHORT).show();
        }
    }



    private void setButtonListeners() {
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btn4 = findViewById(R.id.btn4);
        Button btn5 = findViewById(R.id.btn5);
        Button btn6 = findViewById(R.id.btn6);
        Button btn7 = findViewById(R.id.btn7);
        Button btn8 = findViewById(R.id.btn8);
        Button btn9 = findViewById(R.id.btn9);
        Button btn10 = findViewById(R.id.btn10);
        Button btn11 = findViewById(R.id.btn11);
        Button btn12 = findViewById(R.id.btn12);
        Button btn13 = findViewById(R.id.btn13);

        // Set click listeners
        btn1.setOnClickListener(v -> sendData("1"));
        btn2.setOnClickListener(v -> sendData("2"));
        btn3.setOnClickListener(v -> sendData("3"));
        btn4.setOnClickListener(v -> sendData("4"));
        btn5.setOnClickListener(v -> sendData("5"));
        btn6.setOnClickListener(v -> sendData("6"));
        btn7.setOnClickListener(v -> sendData("7"));
        btn8.setOnClickListener(v -> sendData("8"));
        btn9.setOnClickListener(v -> sendData("9"));
        btn10.setOnClickListener(v -> sendData("10"));
        btn11.setOnClickListener(v -> sendData("11"));
        btn12.setOnClickListener(v -> sendData("12"));
        btn13.setOnClickListener(v -> sendData("13"));
    }

    private void sendData(String data) {
        if (outputStream != null) {
            try {
                outputStream.write(data.getBytes());
                Toast.makeText(this, "Data Sent: " + data, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Data sent: " + data);  // Log the data being sent
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to send data", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to send data", e);  // Log the error if sending fails
            }
        } else {
            Log.w(TAG, "Output stream is null, cannot send data");  // Log warning if outputStream is null
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close Bluetooth socket on app destroy
        try {
            if (bluetoothSocket != null) {
                bluetoothSocket.close();
                Log.d(TAG, "Bluetooth socket closed");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Error closing Bluetooth socket", e);
        }
    }
}
