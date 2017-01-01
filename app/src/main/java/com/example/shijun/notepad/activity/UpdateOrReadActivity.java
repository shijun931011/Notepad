package com.example.shijun.notepad.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shijun.notepad.Bean.DeleteAsyncTask;
import com.example.shijun.notepad.Bean.NotepadBean;
import com.example.shijun.notepad.R;
import com.example.shijun.notepad.db.DataHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateOrReadActivity extends AppCompatActivity {

    private TextView mComplete;
    private ImageButton mBackBtn;
    private EditText mContent;
    private LinearLayout mScreen;

    //备忘录数据
    private int noteId;
    private String noteTime;
    private String noteContent;
    private String originData;


    //数据库
    private DataHelper mNoteDB;
    private static NotepadBean note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_update_or_read);

        //获取传递过来的note对象
        Intent intent = getIntent();
        noteId = intent.getIntExtra("note_id",0);
        Log.d("shijun", "传递后的备忘录ID:" + noteId);

        initViews();
        /**
         * 加载显示数据
         */
        new LoadAsyncTask().execute();
        initEvent();
    }

    private void initViews(){
        /**
         * 布局控件初始化
         */
        mComplete = (TextView) findViewById(R.id.complete_btn);
        mBackBtn = (ImageButton) findViewById(R.id.back_btn);
        mContent = (EditText) findViewById(R.id.note_content);
        mScreen = (LinearLayout) findViewById(R.id.screen_view);

        /**
         * 获取数据库实例
         */
        mNoteDB = DataHelper.getInstance(this);

    }

    private void initEvent(){

        /**
         * 返回上一级菜单
         */
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 updateDataOrNot();
            }
        });

        /**
         * 完成按钮，修改备忘录到数据库
         */
        mComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mContent.getText().toString().trim().equals("")){
                    Log.d("shijun","判断进入空函数");
                    new DeleteAsyncTask(mNoteDB).execute(noteId);
                    finish();
                }else if (mContent.getText().toString().equals(originData)){
                    finish();
                }else{
                    Log.d("shijun","进入判断不为空函数");
                    new UpdateAsyncTask().execute();
                    finish();
                }
            }
        });

    }



    /**
     * 根据是否有内容，提示保存
     */
    private void updateDataOrNot(){

        if (mContent.getText().toString().equals(originData)){
            new AlertDialog.Builder(UpdateOrReadActivity.this)
                    .setTitle("提示")
                    .setMessage("需要保存你编译的内容吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            new UpdateAsyncTask().execute();
                            finish();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).show();
        }else {
            finish();
        }

    }

    /**
     * 根据id从数据库读数据的异步任务
     */
    class LoadAsyncTask extends AsyncTask<Void,Void,NotepadBean>{

        @Override
        protected NotepadBean doInBackground(Void... voids) {
            note = mNoteDB.loadById(noteId);
            return note;
        }

        @Override
        protected void onPostExecute(NotepadBean note) {
            super.onPostExecute(note);
            /**
             * 根据传递进来的Note显示备忘录内容，并把光标移动到最后
             * 记录最初的文本内容
             */
            originData = note.getContent();
            mContent.setText(note.getContent());
            mContent.setSelection(mContent.getText().toString().length());
        }
    }


    /**
     * 更新数据库的异步任务
     */
    class UpdateAsyncTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             * 记录数据
             */
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
            Date date = new Date(System.currentTimeMillis());
            noteTime = sdf.format(date);
            noteContent = mContent.getText().toString();
            note.setTime(noteTime);
            note.setContent(noteContent);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mNoteDB.updateById(noteTime,noteContent,noteId);
            return null;
        }
    }

    /**
     * 返回键事件
     * 根据内容是否有变化，提示是否保存
     */
    @Override
    public void onBackPressed() {
        updateDataOrNot();
    }
}
