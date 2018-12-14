package ru.smirnov;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class Server {
    private static Server server;
    private List<Message> history;
    private Map<Integer, Message> final_set;
    private ServerSocket serverSocket = null;
    private Socket client = null;

    private Server() {
        history = new ArrayList<Message>();
        final_set = new HashMap<Integer, Message>();
    }

    public static Server getServer() {
        if (server == null) {
            server = new Server();
        }
        return server;
    }

    public void setMessage(Message message) {
        this.history.add(message);
        this.final_set.put(message.getId(), message);
    }

    public void saveHistory(File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            for (int j = 0; j < history.size(); j++) {
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
            System.out.println(e.getStackTrace());
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }

    public void saveFinalSet(File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            Set<Integer> ids = final_set.keySet();
            for (Integer id : ids) {
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
            System.out.println(e.getStackTrace());
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            System.exit(-1);
        }
        try {
            System.out.println("Waiting for a client...");
            client = serverSocket.accept();
            System.out.println("Client connected");
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }

    public static void main(String[] args){
        File dir = new File("src//ru//smirnov");
        File fileHistory = new File(dir, "history.txt");
        File fileFinal = new File(dir, "final.txt");
        getServer().run();
        ArrayList<Message> messages = null;
        ObjectInputStream deserializer = null;
        try {
            deserializer = new ObjectInputStream(getServer().client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(!getServer().client.isClosed()) {
            try {
                messages = (ArrayList<Message>) deserializer.readObject();
                System.out.println("Получен пакет из " + messages.size() + " сообщений");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            for(Message mes : messages) {
                if (mes.getId() == 0) {
                    try {
                        getServer().client.close();
                        System.out.println("Server is closed");
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    getServer().history.add(mes);
                    getServer().final_set.put(mes.getId(), mes);
                }
            }
        }
        getServer().saveHistory(fileHistory);
        System.out.println("История сохранена");
        getServer().saveFinalSet(fileFinal);
        System.out.println("Финальный сет сохранен");
    }
}
