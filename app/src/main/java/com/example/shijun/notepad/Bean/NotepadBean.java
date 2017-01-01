package com.example.shijun.notepad.Bean;

import java.io.Serializable;

/**
 * Created by shijun on 2016/12/23.
 */
public class NotepadBean implements Serializable {
    private int id;
    private String time;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
