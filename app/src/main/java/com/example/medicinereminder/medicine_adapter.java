package com.example.medicinereminder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class medicine_adapter extends RecyclerView.Adapter<medicine_adapter.medicine_holder> {
    ArrayList<Medicine_reminder> arrayList=new ArrayList<Medicine_reminder>();
    private onitemclicklistener listener;
    @NonNull
    @Override
    public medicine_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.added_medication,parent,false);

        return new medicine_holder(itemView);
    }




    @Override
    public void onBindViewHolder(@NonNull medicine_holder holder, int position) {
    Medicine_reminder medicine_reminder=arrayList.get(position);
    holder.med_img.setImageResource(medicine_reminder.getImg());
    holder.name_txt.setText(medicine_reminder.getName());
    holder.date_time_txt.setText(medicine_reminder.getDate()+"  "+medicine_reminder.getTime());
    holder.reminder_txt.setText(medicine_reminder.getReminder());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void set_medicine_info(ArrayList<Medicine_reminder> arrayList)
    {
        this.arrayList=arrayList;
        notifyDataSetChanged();
    }
    public Medicine_reminder get_medicine(int position)
    {
        return arrayList.get(position);
    }

    class medicine_holder extends RecyclerView.ViewHolder
    {
        private ImageView med_img;
        private TextView name_txt;
        private TextView  date_time_txt;
        private TextView  reminder_txt;

        public medicine_holder(@NonNull final View itemView) {
            super(itemView);
            med_img=(ImageView)itemView.findViewById(R.id.med_img_view);
            name_txt=(TextView)itemView.findViewById(R.id.name_txt_view);
            date_time_txt=(TextView)itemView.findViewById(R.id.date_time_txt_view);
            reminder_txt=(TextView)itemView.findViewById(R.id.reminder_txt_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    if (listener!=null && position!=RecyclerView.NO_POSITION)
                    {
                        listener.onitemclick(arrayList.get(position));
                    }
                }
            });

        }
    }
    public interface onitemclicklistener
    {
        void onitemclick(Medicine_reminder medicine_reminder);
    }
    public void setonitemclicklistener(onitemclicklistener listener)
    {
        this.listener=listener;
    }
}
