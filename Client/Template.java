package ru.smirnov;

import java.util.ArrayList;

public class Template {
    private int id;
    private ArrayList<String> keys;

    public Template(){
        keys = new ArrayList<String>();
        id = 0;
    }
    public void addKey(String str){
        this.keys.add(str);
    }
    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public ArrayList<String> getKeys(){
        return this.keys;
    }
}
