package com.example.medicinereminder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class date_pop_activity extends DialogFragment implements View.OnClickListener
{

    View view;
    DatePicker dp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.date_pop_activity,container,false);
        Button done=(Button)view.findViewById(R.id.done2_btu);
        dp=(DatePicker)view.findViewById(R.id.date_id);
        done.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view)
    {
        this.dismiss();
        String mon=String.valueOf(dp.getMonth()+1);
        String date=dp.getYear()+"/"+mon+"/"+dp.getDayOfMonth();
        addedit_medicine am=(addedit_medicine)getActivity();
        am.setdate(date);
    }
}
