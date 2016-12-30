package com.example.shijun.notepad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ListView;
import android.widget.TextView;


import com.example.shijun.notepad.Bean.NotepadBean;
import com.example.shijun.notepad.R;




import java.util.List;

/**
 * Created by shijun on 2016/12/23.
 */
public  class NotepadeAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    private List<NotepadBean> noteList;
    private int index;



    public NotepadeAdapter(Context context,List<NotepadBean> noteList,ListView listView){
        mInflater = LayoutInflater.from(context);
        this.noteList = noteList;
        this.mContext = context;

    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int i) {
        return noteList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewholder = null;
        if(view == null){
            viewholder = new ViewHolder();
            view  = mInflater.inflate(R.layout.view_notepad_item, null);
            viewholder.mTime = (TextView) view.findViewById(R.id.show_time);
            viewholder.mContent=(TextView) view.findViewById(R.id.show_content);
            view.setTag(viewholder);
        }else{
            viewholder = (ViewHolder)view.getTag();
        }
        viewholder.mTime.setText(noteList.get(i).getTime());
        viewholder.mContent.setText(noteList.get(i).getContent());
        index = i;
        return view;
    }
    public  class ViewHolder{
        public TextView mTime;
        public TextView mContent;
    }
}
