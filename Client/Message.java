package ru.smirnov;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Message implements Serializable {
    private Integer serial_number;
    private static int number=0;
    private Integer id;
    private Map<String, String> keys = new HashMap<String, String>();

    public Message(Template template){
        for(String key:template.getKeys()){
            this.keys.put(key, randomString(6));
        }
        this.id = template.getId();
        number++;
        this.serial_number = number;
    }

    public String randomString(int top){
        Random ran = new Random();
        char temp = ' ';
        String key = "";

        for (int i=0; i<top; i++) {
            temp = ((char) (ran.nextInt(25) + 97));
            key += temp;
        }
        return key;
    }

    public Integer getId() {
        return id;
    }

    public Integer getSerial_number() {
        return serial_number;
    }

    public Map getKeys(){
        return this.keys;
    }

    public static int getNumber(){
        return number;
    }
}
