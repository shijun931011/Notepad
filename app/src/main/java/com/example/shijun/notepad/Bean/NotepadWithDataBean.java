package com.example.shijun.notepad.Bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijun on 2016/12/30.
 */
public class NotepadWithDataBean implements Serializable {
    private int data;
    private List<NotepadBean> notepadBeenList;
    public NotepadWithDataBean(){
        notepadBeenList=new ArrayList<NotepadBean>();
    }
    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public List<NotepadBean> getNotepadBeenList() {
        return notepadBeenList;
    }

    public void setNotepadBeenList(List<NotepadBean> notepadBeenList) {
        this.notepadBeenList = notepadBeenList;
    }

}
