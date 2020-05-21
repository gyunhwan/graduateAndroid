package com.unity.mynativeapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {
    DatePicker calendar;
    TimePicker startTime=findViewById(R.id.time1);
    TimePicker endTime=findViewById(R.id.time2);
    String selectedDate;
    String selectStart;
    String selectEnd;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_list);
        calendar= (DatePicker) findViewById(R.id.calendar);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDate=calendar.getYear()+"/"+calendar.getMonth()+"/"+calendar.getDayOfMonth();
            }
        });
//        startTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SimpleDateFormat sdf=new SimpleDateFormat("HH:MM");
//                selectStart=startTime.getCurrentHour()+":"+startTime.getCurrentMinute();
//                Log.d("time:",selectStart);
//            }
//        });
    }
}
