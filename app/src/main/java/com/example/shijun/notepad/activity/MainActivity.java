package com.example.shijun.notepad.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shijun.notepad.Bean.DeleteAsyncTask;
import com.example.shijun.notepad.Bean.GlobalParams;
import com.example.shijun.notepad.Bean.NotepadBean;
import com.example.shijun.notepad.R;
import com.example.shijun.notepad.adapter.NotepadeAdapter;
import com.example.shijun.notepad.db.DataHelper;
import com.example.shijun.notepad.service.MainService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTilte;
    private TextView mNoteNum;
    private ImageButton mWrite;
    private ImageButton mAbout;
    private ListView mNoteListView;

    private List<NotepadBean> mNoteList = new ArrayList<NotepadBean>();
    private DataHelper mDataHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        new NewAsyncTask().execute();
        initEvent();

    }

    private void initView(){
        //布局控件初始化
        mTilte = (TextView) findViewById(R.id.app_title);
        mNoteNum = (TextView) findViewById(R.id.note_num);
        mWrite = (ImageButton) findViewById(R.id.write_btn);
        mAbout = (ImageButton) findViewById(R.id.about_btn);
        mNoteListView = (ListView) findViewById(R.id.listview);

        //获取数据库实例
        mDataHelper = DataHelper.getInstance(this);
    }

    private void initEvent(){
        //新写一条备忘录
        mWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddContentActivity.class);
                startActivity(intent);
            }
        });

        //修改或则查看已有的一条备忘录
        mNoteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NotepadBean note = (NotepadBean) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this,UpdateOrReadActivity.class);
                intent.putExtra("note_id", note.getId());
                startActivity(intent);
            }
        });

        //listView长按删除
        mNoteListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final NotepadBean note = (NotepadBean) adapterView.getItemAtPosition(i);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("提示")
                        .setMessage("真的要删除这条记录吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new DeleteAsyncTask(mDataHelper).execute(note.getId());
                                new NewAsyncTask().execute();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                return true;
            }
        });

        //关于自己
        mAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });

    }

    class NewAsyncTask extends AsyncTask<Void, Void, List<NotepadBean>>{
        @Override
        protected List<NotepadBean> doInBackground(Void... voids) {
            mNoteList = mDataHelper.getNotepadList();
            return mNoteList;
        }

        @Override
        protected void onPostExecute(List<NotepadBean> notepadBeen) {
            super.onPostExecute(notepadBeen);
            /**
             * 设置适配器，绑定适配器
             */
            NotepadeAdapter myAdapter = new NotepadeAdapter(MainActivity.this,notepadBeen,mNoteListView);
            mNoteListView.setAdapter(myAdapter);
            /**
             * 更新备忘录记录数
             */
            int temp = mNoteList.size();
            mNoteNum.setText("共 " + temp + " 条备忘录");
        }
    }

    /**
     * 当活动恢复时，刷新listview和备忘录记录数
     */
    @Override
    protected void onResume() {
        super.onResume();
        new NewAsyncTask().execute();
    }
}
