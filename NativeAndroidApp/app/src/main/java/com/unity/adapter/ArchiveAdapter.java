//package com.unity.adapter;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.unity.model.DBVO;
//import com.unity.mynativeapp.R;
//
//import java.util.ArrayList;
//
//public class ArchiveAdapter extends RecyclerView.Adapter<ArchiveAdapter.ViewHolder> {
//    private ArrayList<DBVO> mData=null;
//    public class ViewHolder extends RecyclerView.ViewHolder{
//        TextView textview;
//        Button btn;
//        ViewHolder(View itemView){
//            super(itemView);
//            textview= itemView.findViewById(R.id.archive_text);
//            btn=itemView.findViewById(R.id.archive_btn);
//        }
//    }
//    ArchiveAdapter(ArrayList<DBVO> list){
//        mData=list;
//    }
//    @Override
//   public ArchiveAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
//        Context context = parent.getContext();
//        Log.d("피지지지지지지지지컬",context.getClass().getName());
//        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view= inflater.inflate(R.layout.archive_layout,parent,false);
//        ArchiveAdapter.ViewHolder vh= new ArchiveAdapter.ViewHolder(view);
//        return vh;
//   }
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mData.size();
//    }
//}
