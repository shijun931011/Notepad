package com.example.shijun.notepad.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.shijun.notepad.Bean.GlobalParams;
import com.example.shijun.notepad.Bean.NotepadBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijun on 2016/12/23.
 */
public class DataHelper {

    public static final String DB_NAME = "notepad";
    public static final int VERSION = 1;
    private static DataHelper mNoteDB;
    private SQLiteDatabase db;

    public DataHelper(Context context) {
        MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper(context,DB_NAME,null,VERSION);
        db = dbHelper.getWritableDatabase();
    }
    /**
     * 获取 NoteDB 的实例
     * @param context
     * @return
     */
    public synchronized static DataHelper getInstance(Context context){
        if (mNoteDB == null){
            mNoteDB = new DataHelper(context);
        }
        return mNoteDB;
    }

    public void saveNote(NotepadBean note){
        if (note != null) {
            ContentValues values = new ContentValues();
            values.put("content", note.getContent());
            values.put("time", note.getTime());
            db.insert("Note", null, values);
        }
    }

    public List<NotepadBean> loadNotes(){
        List<NotepadBean> noteList = new ArrayList<NotepadBean>();
        /**
         * 先按时间降序排列，再按id降序排列
         */
        Cursor cursor = db.query("Note",null,null,null,null,null,"time desc,id desc");
        if (cursor.moveToNext()){
            do {
                NotepadBean note = new NotepadBean();
                note.setId(cursor.getInt(cursor.getColumnIndex("id")));
                note.setContent(cursor.getString(cursor.getColumnIndex("content")));
                note.setTime(cursor.getString(cursor.getColumnIndex("time")));
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        return noteList;
    }

    public NotepadBean loadById(int id){
        NotepadBean note = null;
        Cursor cursor = db.query("Note",null,"id = " + id,null,null,null,null);
        if (cursor.moveToNext()){
            note = new NotepadBean();
            note.setContent(cursor.getString(cursor.getColumnIndex("content")));
            note.setTime(cursor.getString(cursor.getColumnIndex("time")));
        }
        return note;
    }

    public void deleteById(Integer id){
        db.delete("Note","id = " + id,null);
    }

    public void deleteAllNote(){
        db.delete("Note", null, null);
    }

    public void updateById(String noteTime, String noteContent, int noteId){
        ContentValues values = new ContentValues();
        values.put("content",noteContent);
        values.put("time",noteTime);
        db.update("Note",values,"id = " + noteId,null);
    }
}
