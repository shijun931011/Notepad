package com.example.shijun.notepad.Bean;

import android.os.AsyncTask;

import com.example.shijun.notepad.db.DataHelper;

/**
 * Created by shijun on 2016/12/30.
 */
public class DeleteAsyncTask extends AsyncTask<Integer, Void, Void>{
    private DataHelper noteDB;
    public DeleteAsyncTask(DataHelper noteDB){
        this.noteDB = noteDB;
    }

    @Override
    protected Void doInBackground(Integer... params) {
        noteDB.deleteNotepad(params[0]);
        return null;
    }
}
