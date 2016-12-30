package com.example.shijun.notepad.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.shijun.notepad.Bean.GlobalParams;
import com.example.shijun.notepad.Bean.NotepadBean;
import com.example.shijun.notepad.Bean.NotepadWithDataBean;
import com.example.shijun.notepad.R;
import com.example.shijun.notepad.db.DataHelper;

import java.util.Calendar;

public class AddContentActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_save;
    private TextView tv_cancel;
    private TextView tv_date;
    private TextView tv_time;
    private EditText et_content;

    private String time = "";
    private String date = "";
    private Bundle bundle;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_content);
//        bundle=getIntent().getExtras();
//        type=bundle.getInt(GlobalParams.TYPE_KEY);
        findViews();
        setListeners();
        initDate();
    }

    private void findViews(){
        et_content=(EditText)findViewById(R.id.et_content);
        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_cancel=(TextView)findViewById(R.id.tv_cancel);
    }

    private void setListeners() {
        tv_save.setOnClickListener(this);
        tv_date.setOnClickListener(this);
        tv_time.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    private void initDate() {
        Calendar c = Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day=c.get(Calendar.DAY_OF_MONTH);
        date=getDate(year,month,day);
        if(type==GlobalParams.TYPE_EDIT){
            NotepadWithDataBean notepadWithDataBean=(NotepadWithDataBean)(bundle.getSerializable(GlobalParams.BEAN_KEY));
            et_content.setText(notepadWithDataBean.getNotepadBeenList().get(bundle.getInt(GlobalParams.ITEM_POSITION_KEY)).getContent());
            date=notepadWithDataBean.getData()+"";
            tv_date.setText(date);
            time=notepadWithDataBean.getNotepadBeenList().get(bundle.getInt(GlobalParams.ITEM_POSITION_KEY)).getTime();
            tv_time.setText(time);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_save:
                if(type== GlobalParams.TYPE_EDIT){
                    update();
                }else {
                    save();
                }
                break;
            case R.id.tv_date:
                selectDateDialog();
                break;
            case R.id.tv_time:
                selectTimeDialog();
                break;
            case R.id.tv_cancel:
                finish();
                break;

        }
    }

    private void update(){
        DataHelper helper=new DataHelper(AddContentActivity.this);
        NotepadWithDataBean bean=(NotepadWithDataBean)(bundle.getSerializable(GlobalParams.BEAN_KEY));
        int itemPosition=bundle.getInt(GlobalParams.ITEM_POSITION_KEY);
        helper.update(bean.getNotepadBeenList().get(itemPosition).getId(),date,time,et_content.getText().toString());
        setResult(GlobalParams.ADD_RESULT_OK);
        finish();
    }

    private void save() {
        String content = et_content.getText().toString();
        if ("".equals(content)) {
            Toast.makeText(AddContentActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(time)) {
            Toast.makeText(AddContentActivity.this, "请选择时间", Toast.LENGTH_SHORT).show();
            return;
        }
        NotepadBean notepadBean = new NotepadBean();
        notepadBean.setContent(content);
        notepadBean.setDate(Integer.parseInt(date));
        notepadBean.setTime(time);
        DataHelper helper = new DataHelper(AddContentActivity.this);
        helper.insertData(notepadBean);
        setResult(GlobalParams.ADD_RESULT_OK);
        finish();
    }

    private void selectTimeDialog() {
        Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        new TimePickerDialog(AddContentActivity.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view,
                                          int hourOfDay, int minute) {
                        time=formatTime(hourOfDay,minute);
                        tv_time.setText(time);
                    }
                }, mHour, mMinute, true).show();
    }

    private String formatTime(int hour,int minute){
        String time=hour+":";
        if(minute<10){
            time=time+"0"+minute;
        }else{
            time+=minute;
        }
        return time;
    }

    private void selectDateDialog(){
        Calendar c = Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        final int month=c.get(Calendar.MONTH)+1;
        int day=c.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(AddContentActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date=getDate(year,monthOfYear,dayOfMonth);
                tv_date.setText(date);
            }
        },year,month,day).show();
    }

    private String  getDate(int year,int month,int day){
        String date="";
        date+=year;
        if(month<9){
            date=date+"0"+(month+1);
        }else{
            date+=(month+1);
        }
        if(day<10){
            date=date+"0"+day;
        }else {
            date+=day;
        }
        return date;
    }



}
