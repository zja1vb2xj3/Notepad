package com.example.user.notepad;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * RecyclerViewAdapter.class
 * RecyclerView 내의 모든 item view의 데이터를 관리
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    /**
     * datas 객체
     * ViewHolder.class의 멤버변수 data_TextView에 할당되는 데이터들
     */
    private ArrayList<String> datas;
    /**
     * layoutInflater 객체
     * 사용 할 View의 context를 받아오는 객체
     */
    private LayoutInflater layoutInflater;
    /**
     * viewHolder 객체
     * RecyclerView내에 각 item의 View 정보를 할당받는객체
     */
    private ViewHolder viewHolder;


    /**
     * ArrayList를 받는 생성자
     * @param context
     * @param datas
     */
    RecyclerViewAdapter(Context context, ArrayList<String> datas) {
        this.layoutInflater = LayoutInflater.from(context);
        this.datas = datas;

        System.out.println("getDBIndex" + this.datas);
    }

    /**
     * getItemCount 메서드
     * RecyclerView의 item의 개수를 return
     */
    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * onCreateViewHolder 메서드
     * RecyclerView의 id를 할당받아서 return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recyclerview_row, parent, false);
        viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    /**
     * onBindViewHolder 메서드
     * 생성된 viewHolder와 position을 전달받아 현재의 position에 맞는 data를 viewHolder가 관리하는 view들에 binding
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String data = datas.get(position);//0은 0이 들어가고 1에는 0하고 1이 같이들어감
        String removedStr = removalLinefeedStr(data);
        holder.data_TextView.setText(removedStr);
    }

    /**
     * onBindViewHolder 메서드
     * 생성된 viewHolder와 position을 전달받아 현재의 position에 맞는 data를 viewHolder가 관리하는 view들에 binding
     */
    private String removalLinefeedStr(String oldStr) {
        //String.replace 해당 문자열(oldChar)을 새 문자열(newChar)로 교체하여 반환
        //System.getProperty(String key) 지정된 키로 표시된 시스템 속성을 가져옵니다.
        //String key : line.separator ("\n") 라인 구분문자.
        return oldStr.replace(System.getProperty("line.separator"), "");
    }

    /**
     * RecyclerViewAdapter.class
     * RecyclerView 내의 모든 item view의 데이터를 관리
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView data_TextView;

        public ViewHolder(View itemView) {
            super(itemView);
            data_TextView = (TextView) itemView.findViewById(R.id.data_TextView);
            data_TextView.setOnClickListener(this);
            data_TextView.setOnLongClickListener(this);
        }

        /**
         * onClick 메서드
         * 설정 한 view를 onClick 할 시 작동하는 메서드
         */
        @Override
        public void onClick(View view) {
            //클릭 시 data_TextView의 텍스트를 받아와 할당
            String data_TextViewStr = data_TextView.getText().toString();
            onItemClickListener.itemOnClick(data_TextViewStr, getAdapterPosition());
        }

        /**
         * onLongClick 메서드
         * 설정 한 view를 onLongClick 할 시 작동하는 메서드
         */
        @Override
        public boolean onLongClick(View view) {

            String data_TextViewStr = data_TextView.getText().toString();
            boolean dialogButtonSign = onItemLongClickListener.itemLongClick(data_TextViewStr);
            Log.i("dialogButtonSign", String.valueOf(dialogButtonSign));
            if (dialogButtonSign != false)
                removeViewItem(getAdapterPosition());

            System.out.println("getAdapterPosition : " + getAdapterPosition());

            return true;
        }

    }//end ViewHolder

    /**
     * removeViewItem 메서드
     * onIKtemLongClick 인터페이스의 itemLongClick 메서드의 반환 값이 true라면
     * datas 객체, recyclerView에 등록된 observer에게 이전에 위치한 항목의 데이터가 제거 되었음을 알림.
     */
    private void removeViewItem(int position) {
        datas.remove(position);
        notifyItemRemoved(position);
    }

    private OnItemLongClickListener onItemLongClickListener;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    interface OnItemClickListener {
        void itemOnClick(String textViewStr, int position);
    }

    interface OnItemLongClickListener {
        boolean itemLongClick(String textViewStr);
    }

}
