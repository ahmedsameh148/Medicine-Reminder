package com.example.medicinereminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBConnection extends SQLiteOpenHelper {

    public static final String Dbname="Medicine_Reminder.db";
    public static String TABLE ="Medicine_info";
    public static final int Version=1;

    SQLiteDatabase db;

    public DBConnection(Context context)
    {
        super(context, Dbname, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE \"Medicine_info\" (\"med_id\" INTEGER,\"name\" TEXT,\"img\" TEXT,\"dose\" INTEGER,\"quantity_per_box\" INTEGER,\"current_quantity\" INTEGER,\"reminder\" TEXT,\"time\" TEXT,\"date\" TEXT,\"activation\" TEXT, PRIMARY KEY(\"med_id\"));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        if(i1>i)
        {
            db.execSQL("DROP TABLE if EXISTS Medicine_info");
            onCreate(db);
        }
    }

    public void insert_row_medicine_info(String name,String img,int dose,int quantity_per_box,int current_quantity,String reminder,String time,String date,String activation)
    {
        db = getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("img",img);
        contentValues.put("dose",dose);
        contentValues.put("quantity_per_box",quantity_per_box);
        contentValues.put("current_quantity",current_quantity);
        contentValues.put("reminder",reminder);
        contentValues.put("time",time);
        contentValues.put("date",date);
        contentValues.put("activation",activation);
        db.insert("Medicine_info",null,contentValues);
    }

    public ArrayList<Medicine_reminder> get_all_record()
    {
        ArrayList<Medicine_reminder> arrayList=new ArrayList<Medicine_reminder>();
        Medicine_reminder medicine_reminder;
        db = getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE+" ;",null);
        res.moveToFirst();
        while (res.isAfterLast()==false)
        {
            medicine_reminder=new Medicine_reminder(res.getInt(res.getColumnIndex("med_id")),res.getString(res.getColumnIndex("name")),res.getInt(res.getColumnIndex("img")),res.getInt(res.getColumnIndex("dose")),res.getInt(res.getColumnIndex("quantity_per_box")),res.getInt(res.getColumnIndex("current_quantity")),res.getString(res.getColumnIndex("reminder")),res.getString(res.getColumnIndex("time")),res.getString(res.getColumnIndex("date")));
            medicine_reminder.setActivation(res.getString(res.getColumnIndex("activation")));
            arrayList.add(medicine_reminder);
            res.moveToNext();
        }
        return arrayList;
    }

    public void delete(int id)
    {
        db = getWritableDatabase();
        db.execSQL("DELETE from Medicine_info WHERE med_id ="+String.valueOf(id));
    }

    public void update(int id,String name,String img,int dose,int quantity_per_box,int current_quantity,String reminder,String time,String date,String activation)
    {
        db = getWritableDatabase();
        db.execSQL("UPDATE Medicine_info set name='"+name+"',img='"+img+"' ,dose='"+dose+"' ,quantity_per_box='"+quantity_per_box+"' ,current_quantity='"+current_quantity+"' ,reminder='"+reminder+"' ,time='"+time+"' ,date='"+date+"' ,activation='"+activation+"' where med_id='"+id+"'");
    }

    public void updateQuantity(int id,int current_quantity)
    {
        db = getWritableDatabase();
        db.execSQL("UPDATE Medicine_info set current_quantity='"+current_quantity+"' where med_id='"+id+"'");
    }

    public void updateactivation(int id,String activation)
    {
        db = getWritableDatabase();
        db.execSQL("UPDATE Medicine_info set current_quantity='"+activation+"' where med_id='"+id+"'");
    }

}
