package com.example.medicinereminder;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class StopAlarm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_alarm);
        MyAlarm.mediaPlayer.stop();
        Intent intent=new Intent(this,TodayMed.class);
        startActivity(intent);
    }
}

