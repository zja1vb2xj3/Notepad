package com.example.user.notepad;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by user on 2017-08-08.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<String> datas;
    private LayoutInflater layoutInflater;
    private ViewHolder viewHolder;

    RecyclerViewAdapter(Context context, ArrayList<String> datas){
        this.layoutInflater = LayoutInflater.from(context);
        this.datas = datas;

        System.out.println("getDBIndex" + this.datas);
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recyclerview_row, parent, false);
        viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String data = datas.get(position);//0은 0이 들어가고 1에는 0하고 1이 같이들어감
        String replace = data.replace(System.getProperty("line.separator"),"");
        holder.data_TextView.setText(replace);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView data_TextView;

        public ViewHolder(View itemView) {
            super(itemView);
            data_TextView = (TextView) itemView.findViewById(R.id.data_TextView);
            data_TextView.setOnClickListener(this);
            data_TextView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //클릭 시 data_TextView의 텍스트를 받아와 할당
            String data_TextViewStr = data_TextView.getText().toString();
            onItemClickListener.itemOnClick(data_TextViewStr);
        }

        @Override
        public boolean onLongClick(View view) {
            String data_TextViewStr = data_TextView.getText().toString();
           boolean dialogButtonSign = onItemLongClickListener.itemLongClick(data_TextViewStr);

            if(dialogButtonSign != false)
            removeViewItem(getAdapterPosition());

            System.out.println("getAdapterPosition : " + getAdapterPosition());

            return true;
        }

    }//end ViewHolder

    private void removeViewItem(int position){
        datas.remove(position);
        notifyItemRemoved(position);
    }

    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    interface OnItemClickListener {
        void itemOnClick(String textViewStr);
    }

    interface OnItemLongClickListener {
        boolean itemLongClick(String textViewStr);
    }

}
