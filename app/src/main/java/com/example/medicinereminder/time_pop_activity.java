package com.example.medicinereminder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class time_pop_activity extends DialogFragment implements View.OnClickListener {

    View view;
    TimePicker tp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.time_pop_activity,container,false);
        Button done=(Button)view.findViewById(R.id.done_btu);
        tp=(TimePicker)view.findViewById(R.id.time_id);
        done.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        this.dismiss();
        String time=tp.getHour() +":"+tp.getMinute();
        addedit_medicine am=(addedit_medicine)getActivity();
        am.settime(time);
    }
}
