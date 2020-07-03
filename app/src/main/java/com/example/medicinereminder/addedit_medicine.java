package com.example.medicinereminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class addedit_medicine extends AppCompatActivity {
    public static final String EXTRA_ID="com.example.medicinereminder.EXTRA_ID";
    public static final String EXTRA_NAME="com.example.medicinereminder.EXTRA_NAME";
    public static final String EXTRA_IMG="com.example.medicinereminder.EXTRA_IMG";
    public static final String EXTRA_DOSE="com.example.medicinereminder.EXTRA_DOSE";
    public static final String EXTRA_QUANTITYPERBOX="com.example.medicinereminder.EXTRA_QUANTITYPERBOX";
    public static final String EXTRA_CURRENTQUANTITY="com.example.medicinereminder.EXTRA_CURRENTQUANTITY";
    public static final String EXTRA_REMINDER="com.example.medicinereminder.EXTRA_REMINDER";
    public static final String EXTRA_TIME="com.example.medicinereminder.EXTRA_TIME";
    public static final String EXTRA_DATE="com.example.medicinereminder.EXTRA_DATE";
    public static final String EXTRA_ACTIVATION="com.example.medicinereminder.EXTRA_ACTIVATION";
    private String reminder;
    private EditText name_edit;
    private ImageView img_edit;
    private EditText dose_edit;
    private EditText quantity_per_box_edit;
    private EditText current_quantity_edit;
    private EditText time_edit;
    private EditText date_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        final Spinner subject_spinner=(Spinner)findViewById(R.id.reminder_spinner);
        final ArrayList<String> spinner_array=new ArrayList<String>();
        spinner_array.add("Every Day");
        spinner_array.add("Every Week");
        spinner_array.add("Every Month");
        spinner_array.add("Every Year");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinner_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subject_spinner.setAdapter(adapter);
        subject_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                reminder =spinner_array.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                subject_spinner.setSelection(0);
            }
        });
        name_edit=(EditText)findViewById(R.id.name_edit_txt);
        img_edit=(ImageView)findViewById(R.id.med_icon);
        dose_edit=(EditText)findViewById(R.id.dose_edit_txt);
        quantity_per_box_edit=(EditText)findViewById(R.id.Q_p_B_edit_txt);
        current_quantity_edit=(EditText)findViewById(R.id.c_q_edit_txt);
        time_edit=(EditText)findViewById(R.id.T_edit_txt);
        time_edit.setEnabled(false);

        date_edit=(EditText)findViewById(R.id.D_edit_txt);
        date_edit.setEnabled(false);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        Intent intent=getIntent();
        if (intent.hasExtra(EXTRA_ID))
        {
            setTitle("Edit Reminder");
            name_edit.setText(intent.getStringExtra(EXTRA_NAME));
            img_edit.setImageResource(intent.getIntExtra(EXTRA_IMG,1));
            dose_edit.setText(String.valueOf(intent.getIntExtra(EXTRA_DOSE,1)));
            quantity_per_box_edit.setText(String.valueOf(intent.getIntExtra(EXTRA_QUANTITYPERBOX,1)));
            current_quantity_edit.setText(String.valueOf(intent.getIntExtra(EXTRA_CURRENTQUANTITY,1)));
            time_edit.setText(intent.getStringExtra(EXTRA_TIME));
            date_edit.setText(intent.getStringExtra(EXTRA_DATE));
            for (int i=0;i<spinner_array.size();i++)
            {
                if (reminder==spinner_array.get(i))
                {
                    subject_spinner.setSelection(i);
                    break;
                }
            }
        }
        else
        {
            setTitle("Add Reminder");
        }

    }
    int index=0;
    int[] arrpic=new int[]{R.drawable.tablet,R.drawable.capsul,R.drawable.drop,R.drawable.inhaler,R.drawable.mouthwash,R.drawable.powder,R.drawable.spray,R.drawable.syrup};
    String[] arrtxt=new String[]{"Tablet","Capsul","Drop","Inhaler","Mouthwash","Powder","Spray","Syrup"};

    public void next(View view)
    {
        index++;
        if (index>7)
            index=0;
        ImageView mypic=(ImageView)findViewById(R.id.med_icon);
        TextView mytxt=(TextView)findViewById(R.id.textview3);
        mypic.setImageResource(arrpic[index]);
        mytxt.setText(arrtxt[index]);


    }

    public void pervious(View view)
    {
        index--;
        if (index<0)
            index=7;
        ImageView mypic=(ImageView)findViewById(R.id.med_icon);
        TextView mytxt=(TextView)findViewById(R.id.textview3);
        mypic.setImageResource(arrpic[index]);
        mytxt.setText(arrtxt[index]);


    }

    public void time(View view)
    {
        FragmentTransaction manger=getSupportFragmentManager().beginTransaction();
        time_pop_activity time_pop=new time_pop_activity();
        time_pop.show(manger,null);
    }
    public void settime(String time)
    {
        time_edit=(EditText)findViewById(R.id.T_edit_txt);
        time_edit.setText(time);
    }
    public void date_btn(View view)
    {
        FragmentTransaction manger=getSupportFragmentManager().beginTransaction();
        date_pop_activity date_pop=new date_pop_activity();
        date_pop.show(manger,null);
    }
    public void setdate(String date)
    {
        date_edit=(EditText)findViewById(R.id.D_edit_txt);
        date_edit.setText(date);
    }

    private void savemed()
    {
        String name=null;
        int img=arrpic[index];
        int dose=0;
        int quantity_per_box=0;
        int current_quantity=0;
        String activation=null;
        String time=null;
        String date=null;
        try {
            name=name_edit.getText().toString();
            img=arrpic[index];
            dose=Integer.valueOf(dose_edit.getText().toString());
            quantity_per_box=Integer.valueOf(quantity_per_box_edit.getText().toString());
            current_quantity=Integer.valueOf(current_quantity_edit.getText().toString());
            activation="true";
            time=time_edit.getText().toString();
            date=date_edit.getText().toString();

        }catch (Exception e)
        {
            Toast.makeText(this,"Please enter all data",Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent=new Intent();
        intent.putExtra(EXTRA_NAME,name);
        intent.putExtra(EXTRA_IMG,img);
        intent.putExtra(EXTRA_DOSE,dose);
        intent.putExtra(EXTRA_QUANTITYPERBOX,quantity_per_box);
        intent.putExtra(EXTRA_CURRENTQUANTITY,current_quantity);
        intent.putExtra(EXTRA_ACTIVATION,activation);
        intent.putExtra(EXTRA_TIME,time);
        intent.putExtra(EXTRA_DATE,date);
        intent.putExtra(EXTRA_REMINDER,reminder);

        int id=getIntent().getIntExtra(EXTRA_ID,-1);
        if (id!=-1)
        {
            intent.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK,intent);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.save_medicine_info,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.save_med:
                savemed();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }
}
