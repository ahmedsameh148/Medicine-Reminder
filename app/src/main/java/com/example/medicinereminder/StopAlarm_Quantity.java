package com.example.medicinereminder;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class StopAlarm_Quantity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_alarm_quantity);
        MyAlarm_Quantity.mediaPlayer.stop();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}