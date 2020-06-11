package com.unity.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unity.model.CalendarVO;
import com.unity.model.Note;
import com.unity.mynativeapp.CalendarDetailActivity;
import com.unity.mynativeapp.R;
import com.unity.util.DatabaseHelper;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private ArrayList<CalendarVO> notes;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    public NotesAdapter(Context context) {
        notes = generateNotes(context);
    }
    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.calendar_vo, viewGroup,
                false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CalendarVO vo= notes.get(i);
        String title=vo.getTitle();
        String content= vo.getContent();
        String startDate=vo.getStart_date();
        int id = vo.getId();
        String endDate= vo.getEnd_date();
        viewHolder.title.setText(title);
        viewHolder.content.setText(content);
        viewHolder.id.setText(String.valueOf(id));
        viewHolder.startDate.setText(startDate);
        viewHolder.endDate.setText(endDate);
        viewHolder.id.setVisibility(TextUtils.isEmpty(title)?View.GONE:View.VISIBLE);
        viewHolder.title.setVisibility(TextUtils.isEmpty(title)?View.GONE:View.VISIBLE);
        viewHolder.content.setVisibility(TextUtils.isEmpty(title)?View.GONE:View.VISIBLE);
        viewHolder.startDate.setVisibility(TextUtils.isEmpty(title)?View.GONE:View.VISIBLE);
        viewHolder.endDate.setVisibility(TextUtils.isEmpty(title)?View.GONE:View.VISIBLE);
        int paddingTop = (viewHolder.title.getVisibility() != View.VISIBLE) ? 0
                : viewHolder.itemView.getContext().getResources()
                .getDimensionPixelSize(R.dimen.note_content_spacing);
        viewHolder.content.setPadding(viewHolder.content.getPaddingLeft(), paddingTop,
                viewHolder.content.getPaddingRight(), viewHolder.content.getPaddingBottom());

    }
    private ArrayList<CalendarVO> generateNotes(Context context) {
        ArrayList<CalendarVO> notes = new ArrayList<CalendarVO>();
        dbHelper= new DatabaseHelper(context);
        db=dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM calendar",null);
        while(cursor.moveToNext()){
            CalendarVO vo =new CalendarVO();
            vo.setId(Integer.valueOf(cursor.getString(0)));
            vo.setStart_date(cursor.getString(1));
            vo.setEnd_date(cursor.getString(2));
            vo.setTitle(cursor.getString(3));
            vo.setContent(cursor.getString(4));
            notes.add(vo);
        }

        return notes;
    }
    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView content;
        public TextView startDate;
        public TextView endDate;
        public TextView id;
        public ViewHolder(View itemView) {
            super(itemView);

            title= itemView.findViewById(R.id.calendarVO_title);
            content=itemView.findViewById(R.id.calendarVO_content);
            startDate=itemView.findViewById(R.id.calendarVO_start_date);
            endDate=itemView.findViewById(R.id.calendarVO_end_date);
            id=itemView.findViewById(R.id.calendarVO_id);
        }
    }
}
