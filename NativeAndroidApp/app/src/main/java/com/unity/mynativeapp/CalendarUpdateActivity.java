package com.unity.mynativeapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.unity.model.CalendarVO;
import com.unity.util.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarUpdateActivity extends Activity {
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
        btnPush.setText("수정");
        btnCancle=findViewById(R.id.cancle);
        dbHelper=new DatabaseHelper(CalendarUpdateActivity.this);
        Intent intent2=getIntent();
        final String id =intent2.getStringExtra("id");
        setState(dbHelper,id);


        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CalendarUpdateActivity.this,startDatePicker,startCalendar.get(Calendar.YEAR),startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CalendarUpdateActivity.this,endDatePicker,endCalendar.get(Calendar.YEAR),endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CalendarUpdateActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                mTimePicker = new TimePickerDialog(CalendarUpdateActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                Log.d("id아이디수정:",id);
                db=dbHelper.getWritableDatabase();
                try{
                    query="UPDATE calendar SET start_date='"+startDate+"-"+startTime+"',end_date='" +endDate+"-"+endTime+"',title='"+title.getText()+"',content='"+content.getText()+"' WHERE id='"+id+"'";
                    db.execSQL(query);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally{
                    db.close();
                    Intent intent = new Intent(getApplication(),CalendarDetailActivity.class);
                    intent.putExtra("id",id);
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
    private void setState(DatabaseHelper dbhelper, String id){
        CalendarVO vo = new CalendarVO();

        try{
            db=dbhelper.getReadableDatabase();
            Cursor cur=db.rawQuery("SELECT * FROM calendar WHERE id='"+id+"'",null);
            while (cur.moveToNext()) {

                vo.setStart_date(cur.getString(1));
                vo.setEnd_date(cur.getString(2));
                vo.setTitle(cur.getString(3));
                vo.setContent(cur.getString(4));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally{
            db.close();
        }
        Log.d("스타트 타임 로그",vo.getStart_date());
        String [] startDateTime=vo.getStart_date().split("-");
        String [] endDateTime=vo.getEnd_date().split("-");
        startDate=startDateTime[0];
        startTime=startDateTime[1];
        endDate=endDateTime[0];
        endTime=endDateTime[1];
        title.setText(vo.getTitle());
        content.setText(vo.getContent());

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
