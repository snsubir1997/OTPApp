package com.example.subir.otpapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnSend;
    EditText phoneno;
    int otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSend = findViewById(R.id.register);
        phoneno = findViewById(R.id.phone);

        btnSend.setOnClickListener(this);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(),"Permission Not Granted",
                    Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1725);
            btnSend.setEnabled(false);
        }
        else {
            btnSend.setEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1725 &&
                grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(),"Permission has been Granted",
                    Toast.LENGTH_SHORT).show();
            btnSend.setEnabled(true);
        }
        else
        {
            btnSend.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        Random r = new Random();
        otp = r.nextInt((999999 - 111111) + 1) + 111111;

        String msg = "Your One time password for registering is "+otp+". Please do not share this " +
                "message with anyone.";
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneno.getText().toString(),
                null, msg,null, null);

        Intent i = new Intent(MainActivity.this,OtpCheck.class);
        i.putExtra("otp",otp);
        startService(i);
    }
}
