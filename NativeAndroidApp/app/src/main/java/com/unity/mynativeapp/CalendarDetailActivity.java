package com.unity.mynativeapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.unity.model.CalendarVO;
import com.unity.util.DatabaseHelper;

public class CalendarDetailActivity extends Activity {
    SQLiteDatabase db;
    DatabaseHelper helper;
    TextView id;
    TextView startTime;
    TextView endTime;
    TextView title;
    TextView content;
    Button updateBtn;
    Button deleteBtn;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_detail);
        setTitle("Detail");
        Intent intent = getIntent();
        final CalendarVO vo = new CalendarVO();
        helper = new DatabaseHelper(this);
        db = helper.getReadableDatabase();
        try {
            Cursor cur = db.rawQuery("SELECT * FROM calendar WHERE id ='" + intent.getStringExtra("id") + "'", null);
            while (cur.moveToNext()) {

                vo.setId(Integer.valueOf(cur.getString(0)));
                vo.setStart_date(cur.getString(1));
                vo.setEnd_date(cur.getString(2));
                vo.setTitle(cur.getString(3));
                vo.setContent(cur.getString(4));
            }
        }
        catch(SQLiteException e){
            e.printStackTrace();
        }finally{
            db.close();
        }
        id=findViewById(R.id.detail_id);
        id.setText(String.valueOf(vo.getId()));
        startTime=findViewById(R.id.detail_startDate);
        startTime.setText("시작시간:"+vo.getStart_date());
        endTime=findViewById(R.id.detail_endDate);
        endTime.setText("끝난 시간"+vo.getEnd_date());
        title=findViewById(R.id.detail_title);
        title.setText("제목"+vo.getTitle());
        content=findViewById(R.id.detail_content);
        content.setText("내용"+vo.getContent());
        updateBtn=findViewById(R.id.updateBtn);
        deleteBtn=findViewById(R.id.deleteBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),CalendarUpdateActivity.class);
                intent.putExtra("id",vo.getId());
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=helper.getWritableDatabase();
                try{
                db.execSQL("DELETE FROM calendar WHERE id='"+vo.getId()+"'");}
                catch (Exception e){
                    e.printStackTrace();
                }
                finally{
                    db.close();
                }
                Intent intent=new Intent(getApplicationContext(),CalendarActivity.class);
                startActivity(intent);
            }
        });
    }
}
