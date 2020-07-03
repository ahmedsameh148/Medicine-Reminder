package com.example.medicinereminder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class today_adapter extends RecyclerView.Adapter<today_adapter.medicine_holder> {
    ArrayList<Medicine_reminder> arrayList=new ArrayList<Medicine_reminder>();



    private onitemclicklistener listener;
    @NonNull
    @Override
    public medicine_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.check_medicine,parent,false);

        return new medicine_holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final medicine_holder holder, int position) {
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
        private CheckBox isTakenChecked;
        private TextView curr;
        private TextView mini;
        private TextView dose;
        public medicine_holder(@NonNull final View itemView) {
            super(itemView);
            med_img=(ImageView)itemView.findViewById(R.id.med_img_view);
            name_txt=(TextView)itemView.findViewById(R.id.name_txt_view);
            date_time_txt=(TextView)itemView.findViewById(R.id.date_time_txt_view);
            reminder_txt=(TextView)itemView.findViewById(R.id.reminder_txt_view);
            curr = (TextView)itemView.findViewById(R.id.c_q_edit_txt);
            mini = (TextView)itemView.findViewById(R.id.Q_p_B_edit_txt);
            dose = (TextView)itemView.findViewById(R.id.dose_edit_txt);
            isTakenChecked = (CheckBox)itemView.findViewById(R.id.checkBox);
            isTakenChecked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position=getAdapterPosition();
                    if (listener!=null && position!=RecyclerView.NO_POSITION)
                    {
                        listener.onitemclick(arrayList.get(position),isTakenChecked.isChecked());
                    }
                }
            });
        }
    }

    public interface onitemclicklistener
    {
        void onitemclick(Medicine_reminder medicine_reminder,boolean status);
    }
    public void setonitemclicklistener(onitemclicklistener listener)
    {
        this.listener=listener;
    }
}
