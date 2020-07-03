package com.example.medicinereminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_MEDICINE_REQUEST=1;
    public static final int EDIT_MEDICINE_REQUEST=2;

    DBConnection db=new DBConnection(this);

    public ArrayList<Medicine_reminder> medicine_reminders=new ArrayList <Medicine_reminder>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medicine_reminders = db.get_all_record();

        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final medicine_adapter adapter=new medicine_adapter();
        adapter.set_medicine_info(medicine_reminders);
        recyclerView.setAdapter(adapter);

        show_medicine_added();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, addedit_medicine.class);
                startActivityForResult(intent,ADD_MEDICINE_REQUEST);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Medicine_reminder medicine_reminder;
                medicine_reminder=adapter.get_medicine(viewHolder.getAdapterPosition());
                medicine_reminders.remove(medicine_reminder);
                db.delete(medicine_reminder.getId());
                show_medicine_added();
                Toast.makeText(MainActivity.this,"Reminder Deleted",Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_main_2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_settings:
                medicine_reminders.removeAll(medicine_reminders);
                show_medicine_added();
                Toast.makeText(this,"All Reminders Deleted",Toast.LENGTH_SHORT).show();
                return true;

            case R.id.viewtodayMed:
                Intent intent = new Intent(this, TodayMed.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void show_medicine_added()
    {
        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final medicine_adapter adapter=new medicine_adapter();
        adapter.set_medicine_info(medicine_reminders);
        recyclerView.setAdapter(adapter);

       adapter.setonitemclicklistener(new medicine_adapter.onitemclicklistener() {
           @Override
           public void onitemclick(Medicine_reminder medicine_reminder) {
               Intent intent=new Intent(MainActivity.this, addedit_medicine.class);
               intent.putExtra(addedit_medicine.EXTRA_ID,medicine_reminder.getId());
               intent.putExtra(addedit_medicine.EXTRA_NAME,medicine_reminder.getName());
               intent.putExtra(addedit_medicine.EXTRA_IMG,medicine_reminder.getImg());
               intent.putExtra(addedit_medicine.EXTRA_DOSE,medicine_reminder.getDose());
               intent.putExtra(addedit_medicine.EXTRA_QUANTITYPERBOX,medicine_reminder.getQuantity_per_Box());
               intent.putExtra(addedit_medicine.EXTRA_CURRENTQUANTITY,medicine_reminder.getCurrent_Quantity());
               intent.putExtra(addedit_medicine.EXTRA_ACTIVATION,medicine_reminder.getActivation());
               intent.putExtra(addedit_medicine.EXTRA_TIME,medicine_reminder.getTime());
               intent.putExtra(addedit_medicine.EXTRA_DATE,medicine_reminder.getDate());
               intent.putExtra(addedit_medicine.EXTRA_REMINDER,medicine_reminder.getReminder());
               startActivityForResult(intent,EDIT_MEDICINE_REQUEST);
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

            show_medicine_added();

            startalarm(data,time,date);
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
            /*Medicine_reminder medicine_reminderr=new Medicine_reminder(idd,name,img,dose,quantity_per_box,current_quantity,reminder,time,date);
            medicine_reminderr.setActivation(activation);
            medicine_reminders.add(medicine_reminderr);*/
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
            show_medicine_added();

            startalarm(data,time,date);
            Toast.makeText(this,"Reminder Updated",Toast.LENGTH_SHORT).show();
            db.update(idd, name,String.valueOf(img),dose,quantity_per_box,current_quantity,reminder,time,date,activation);
        }
        else
        {
            Toast.makeText(this,"Reminder Not Saved",Toast.LENGTH_LONG).show();
        }
    }

    private void startalarm(Intent data, String time, String date) {
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
}
