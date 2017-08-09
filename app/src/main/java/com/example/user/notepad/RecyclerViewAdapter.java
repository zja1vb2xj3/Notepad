package com.example.user.notepad;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Vector;

/**
 * Created by user on 2017-08-08.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Vector<String> datas;
    private LayoutInflater layoutInflater;
    private int clickPosition;
    RecyclerViewAdapter(Context context, Vector<String> datas){
        this.layoutInflater = LayoutInflater.from(context);
        this.datas = datas;
    }
    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recyclerview_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String data = datas.get(position);//0은 0이 들어가고 1에는 0하고 1이 같이들어감
        holder.data_TextView.setText(data);
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
            onItemClickListener.onItemView(data_TextView.getText().toString(), getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            return true;
        }
    }//end ViewHolder

    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    interface OnItemClickListener {
        void onItemView(String itemData, int position);
    }

    interface OnItemLongClickListener {
        void onRemove(String itemData);
    }

}
