package com.example.medicinereminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class MainPage extends AppCompatActivity {

    private RelativeLayout myLayout = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        getSupportActionBar().hide();

        myLayout = (RelativeLayout)findViewById(R.id.myLayout);
        myLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                open();
                return true;
            }
        });
    }

    public void open()
    {
        Intent intent = new Intent(this, TodayMed.class);
        startActivity(intent);
    }
}
