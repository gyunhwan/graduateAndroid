package com.unity.mynativeapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.unity.util.DatabaseHelper;
import com.unity.util.ValueObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {
    private Button btnStartDate;
    private Button btnEndDate;
    private Button btnStartTime;
    private Button btnEndTime;
    private String startDate;
    private String endDate;
    private Button btnPush;
    private Button btnCancle;
    private String startTime;
    private String endTime;
    EditText title;
    EditText content;
    String query;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    private Calendar startCalendar=Calendar.getInstance();
    private Calendar endCalendar=Calendar.getInstance();

    DatePickerDialog.OnDateSetListener startDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            startCalendar.set(Calendar.YEAR, year);
            startCalendar.set(Calendar.MONTH, month);
            startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate("start");
        }
    };
    DatePickerDialog.OnDateSetListener endDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            endCalendar.set(Calendar.YEAR, year);
            endCalendar.set(Calendar.MONTH, month);
            endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate("end");
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_list);
        btnStartDate=findViewById(R.id.start);
        btnEndDate=findViewById(R.id.end);
        btnStartTime=findViewById(R.id.startTime);
        btnEndTime=findViewById(R.id.endTime);
        title=findViewById(R.id.title);
        content=findViewById(R.id.content);
        btnPush=findViewById(R.id.push);
        btnCancle=findViewById(R.id.cancle);
        dbHelper=new DatabaseHelper(CalendarActivity.this);


        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CalendarActivity.this,startDatePicker,startCalendar.get(Calendar.YEAR),startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CalendarActivity.this,endDatePicker,endCalendar.get(Calendar.YEAR),endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CalendarActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String hour=String.valueOf(selectedHour);
                        String minute=String.valueOf(selectedMinute);
                        if(selectedHour<10){
                            hour="0"+hour;
                        }
                        if(selectedMinute<10){
                            minute="0"+minute;
                        }
                        startTime=hour+":"+minute;
                        Log.d("start시간:",startTime);
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        btnEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CalendarActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String hour=String.valueOf(selectedHour);
                        String minute=String.valueOf(selectedMinute);
                        if(selectedHour<10){
                            hour="0"+hour;
                        }
                        if(selectedMinute<10){
                            minute="0"+minute;
                        }
                        endTime=hour+":"+minute;
                        Log.d("end시간:",endTime);
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        btnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db=dbHelper.getWritableDatabase();
                try{
                query="INSERT INTO calendar(id,start_date,end_date,title,content) values(null,'"+startDate+"-"+startTime+"','"+endDate+"-"+endTime+"','"+title.getText()+"','"+content.getText()+"')";

                db.execSQL(new String(query.getBytes("utf-8")));}
                catch (Exception e){
                    e.printStackTrace();
                }
                finally{
                db.close();
                Intent intent = new Intent(getApplication(),MaterialSheetFabActivity.class);
                startActivity(intent);
                }
            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),MaterialSheetFabActivity.class);
                startActivity(intent);

            }
        });

    }
    public void updateDate(String se){
        String format= "yyyy/MM/dd";
        SimpleDateFormat dateformat=new SimpleDateFormat(format, Locale.KOREA);
        if(se=="start"){
            startDate=dateformat.format(startCalendar.getTime()).toString();
            Log.d("startDate:",startDate);
        }
        else{
            endDate=dateformat.format(endCalendar.getTime()).toString();
            Log.d("endDate:",endDate);
        }
    }
}

