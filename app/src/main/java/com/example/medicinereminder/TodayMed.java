package com.example.medicinereminder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TodayMed extends AppCompatActivity {

    public static final int ADD_MEDICINE_REQUEST=1;
    public static final int EDIT_MEDICINE_REQUEST=2;

    Menu menu;

    DBConnection db=new DBConnection(this);

    public ArrayList<Medicine_reminder> medicine_reminders=new ArrayList <Medicine_reminder>();
    public ArrayList<Medicine_reminder> Today = new ArrayList<Medicine_reminder>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_med);

        if(menu != null)
        {
            MenuItem item = menu.findItem(R.id.action_settings);
            item.setVisible(false);

        }

        medicine_reminders = db.get_all_record();

        show();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TodayMed.this, addedit_medicine.class);
                startActivityForResult(intent,ADD_MEDICINE_REQUEST);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.action_settings:
                medicine_reminders.removeAll(medicine_reminders);

                Toast.makeText(this,"All Reminders Deleted",Toast.LENGTH_SHORT).show();
                return true;

            case R.id.viewMed:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void show()
    {
        Today= new ArrayList<Medicine_reminder>();
        for(int i=0; i<medicine_reminders.size(); i++)
        {
            String day;
            String[] feild=medicine_reminders.get(i).getDate().split("/");
            day=feild[2];
            if (Integer.valueOf(day)>=1 && Integer.valueOf(day)<=9)
            {
                String date = new SimpleDateFormat("yyyy/M/d", Locale.getDefault()).format(new Date());
                System.out.println("Current time " + date);
                if(medicine_reminders.get(i).getDate().equals(date))
                {
                    Today.add(medicine_reminders.get(i));
                }
            }
            else
            {
                String date = new SimpleDateFormat("yyyy/M/dd", Locale.getDefault()).format(new Date());
                System.out.println("Current time " + date);
                if(medicine_reminders.get(i).getDate().equals(date))
                {
                    Today.add(medicine_reminders.get(i));
                }
            }

        }
        RecyclerView recyclerView=findViewById(R.id.recycler_view2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final today_adapter adapter=new today_adapter();
        adapter.set_medicine_info(Today);
        recyclerView.setAdapter(adapter);

        adapter.setonitemclicklistener(new today_adapter.onitemclicklistener() {
            @Override
            public void onitemclick(Medicine_reminder medicine_reminder, boolean status) {
                if (status) {
                    int current = medicine_reminder.getCurrent_Quantity();
                    int dose = medicine_reminder.getDose();
                    int difference = current - dose;
                    medicine_reminder.setCurrent_Quantity(difference);
                    db.updateQuantity(medicine_reminder.getId(), medicine_reminder.getCurrent_Quantity());
                    if (medicine_reminder.getCurrent_Quantity()<=medicine_reminder.getQuantity_per_Box())
                    {
                        startalarm_quantity();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==ADD_MEDICINE_REQUEST && resultCode==RESULT_OK)
        {

            String name=data.getStringExtra(addedit_medicine.EXTRA_NAME);
            int img=data.getIntExtra(addedit_medicine.EXTRA_IMG,1);
            int dose=data.getIntExtra(addedit_medicine.EXTRA_DOSE,1);
            int quantity_per_box=data.getIntExtra(addedit_medicine.EXTRA_QUANTITYPERBOX,1);
            int current_quantity=data.getIntExtra(addedit_medicine.EXTRA_CURRENTQUANTITY,1);
            String activation=data.getStringExtra(addedit_medicine.EXTRA_ACTIVATION);
            String time=data.getStringExtra(addedit_medicine.EXTRA_TIME);
            String date=data.getStringExtra(addedit_medicine.EXTRA_DATE);
            String reminder=data.getStringExtra(addedit_medicine.EXTRA_REMINDER);

            int id;
            if (medicine_reminders.size()==0)
            {
                id=1;
            }
            else
            {
                id =medicine_reminders.get(medicine_reminders.size()-1).getId()+1;
            }
            Medicine_reminder medicine_reminder=new Medicine_reminder(id,name,img,dose,quantity_per_box,current_quantity,reminder,time,date);
            medicine_reminder.setActivation(activation);
            medicine_reminders.add(medicine_reminder);
            db.insert_row_medicine_info(name,String.valueOf(img),dose,quantity_per_box,current_quantity,reminder,time,date,activation);

            show();
            startalarm(time,date);
            Toast.makeText(this,"Reminder Saved",Toast.LENGTH_LONG).show();
        }
        else if (requestCode==EDIT_MEDICINE_REQUEST && resultCode==RESULT_OK)
        {
            int idd=data.getIntExtra(addedit_medicine.EXTRA_ID,-1);
            if (idd ==-1)
            {
                Toast.makeText(this,"Reminder can't be updated",Toast.LENGTH_SHORT).show();
                return;
            }
            String name=data.getStringExtra(addedit_medicine.EXTRA_NAME);
            int img=data.getIntExtra(addedit_medicine.EXTRA_IMG,1);
            int dose=data.getIntExtra(addedit_medicine.EXTRA_DOSE,1);
            int quantity_per_box=data.getIntExtra(addedit_medicine.EXTRA_QUANTITYPERBOX,1);
            int current_quantity=data.getIntExtra(addedit_medicine.EXTRA_CURRENTQUANTITY,1);
            String activation=data.getStringExtra(addedit_medicine.EXTRA_ACTIVATION);
            String time=data.getStringExtra(addedit_medicine.EXTRA_TIME);
            String date=data.getStringExtra(addedit_medicine.EXTRA_DATE);
            String reminder=data.getStringExtra(addedit_medicine.EXTRA_REMINDER);



            for (int i=0;i<medicine_reminders.size();i++)
            {
                if (idd==medicine_reminders.get(i).getId())
                {
                    medicine_reminders.get(i).setId(idd);
                    medicine_reminders.get(i).setName(name);
                    medicine_reminders.get(i).setImg(img);
                    medicine_reminders.get(i).setDose(dose);
                    medicine_reminders.get(i).setQuantity_per_Box(quantity_per_box);
                    medicine_reminders.get(i).setCurrent_Quantity(current_quantity);
                    medicine_reminders.get(i).setReminder(reminder);
                    medicine_reminders.get(i).setTime(time);
                    medicine_reminders.get(i).setDate(date);
                    medicine_reminders.get(i).setActivation(activation);
                    break;
                }
            }

            show();
            startalarm(time,date);
            Toast.makeText(this,"Reminder Updated",Toast.LENGTH_SHORT).show();
            db.update(idd, name,String.valueOf(img),dose,quantity_per_box,current_quantity,reminder,time,date,activation);
        }
        else
        {
            Toast.makeText(this,"Reminder Not Saved",Toast.LENGTH_LONG).show();
        }
    }
    private void startalarm(String time, String date) {
        String hour,min;
        String[] feild=time.split(":");
        hour=feild[0];   min=feild[1];

        String year,mon,day;
        String[] feild2=date.split("/");
        year=feild2[0];     mon=feild2[1];     day=feild2[2];

        Calendar calendar=Calendar.getInstance();
        calendar.set(
                Integer.valueOf(year),
                Integer.valueOf(mon)-1,
                Integer.valueOf(day),
                Integer.valueOf(hour),
                Integer.valueOf(min),0);

        setalarm(calendar.getTimeInMillis());
    }
    private void setalarm(long timeInMillis) {
        AlarmManager alarmManager=(AlarmManager)this.getSystemService(ALARM_SERVICE);
        Intent intent=new Intent(this,MyAlarm.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent);
    }
    private void startalarm_quantity() {

        Calendar calendar=Calendar.getInstance();
        calendar.set(
                calendar.get(calendar.YEAR),
                calendar.get(calendar.MONTH),
                calendar.get(calendar.DAY_OF_MONTH),
                calendar.get(calendar.HOUR),
                calendar.get(calendar.MINUTE)+1,0);

        setalarm_quantity(calendar.getTimeInMillis());
    }
    private void setalarm_quantity(long timeInMillis) {
        AlarmManager alarmManager=(AlarmManager)this.getSystemService(ALARM_SERVICE);
        Intent intent=new Intent(this,MyAlarm_Quantity.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent);
    }
}
