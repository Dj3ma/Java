package ru.smirnov;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Server {
    private static Server server;
    private List<Message> history;
    private Map<Integer, Message> final_set;

    private Server(){
        history = new ArrayList<Message>();
        final_set = new HashMap<Integer, Message>();
    }

    public static Server getServer(){
        if(server == null){
            server = new Server();
        }
        return server;
    }

    public void setMessage(Message message){
        this.history.add(message);
        this.final_set.put(message.getId(), message);
    }

    public void saveHistory(File file){
        try {
            FileOutputStream fos = new FileOutputStream(file);
            for(int j = 0; j<history.size(); j++) {
                fos.write("Serial number = ".getBytes());
                fos.write(history.get(j).getSerial_number().toString().getBytes());
                fos.write("\n".getBytes());
                fos.write("id = ".getBytes());
                fos.write(history.get(j).getId().toString().getBytes());
                fos.write("\n".getBytes());
                Set<String> keys = history.get(j).getKeys().keySet();
                for (String key : keys) {
                    byte buffer[] = history.get(j).getKeys().get(key).toString().getBytes();
                    fos.write(key.getBytes());
                    fos.write(" = ".getBytes());
                    fos.write(buffer, 0, buffer.length);
                    fos.write("\n".getBytes());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveFinalSet(File file){
        try {
            FileOutputStream fos = new FileOutputStream(file);
            Set<Integer> ids = final_set.keySet();
            for(Integer id : ids) {
                fos.write("Serial number = ".getBytes());
                fos.write(final_set.get(id).getSerial_number().toString().getBytes());
                fos.write("\n".getBytes());
                fos.write("id = ".getBytes());
                fos.write(final_set.get(id).getId().toString().getBytes());
                fos.write("\n".getBytes());
                Set<String> keys = final_set.get(id).getKeys().keySet();
                for (String key : keys) {
                    byte buffer[] = final_set.get(id).getKeys().get(key).toString().getBytes();
                    fos.write(key.getBytes());
                    fos.write(" = ".getBytes());
                    fos.write(buffer, 0, buffer.length);
                    fos.write("\n".getBytes());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
