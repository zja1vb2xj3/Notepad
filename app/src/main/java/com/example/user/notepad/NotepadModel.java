package com.example.user.notepad;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by user on 2017-09-03.
 */

public class NotepadModel implements Serializable {
    private ArrayList<String> noteDatas;
    private int dataPosition;

    public NotepadModel(){
        noteDatas = new ArrayList<>();
    }

    public ArrayList<String> getNoteDatas() {
        return noteDatas;
    }

    public void setNoteDatas(ArrayList<String> noteDatas) {
        this.noteDatas = noteDatas;
    }

    public int getDataPosition() {
        return dataPosition;
    }

    public void setDataPosition(int dataPosition) {
        this.dataPosition = dataPosition;
    }
}
