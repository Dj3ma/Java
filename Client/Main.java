package ru.smirnov;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static ArrayList<Message> messages = new ArrayList<>(10);
    private static ArrayList<Message> completedMessages = null;
    private static int counter = 0;
    private static int maxCounter = 30000;
    private static Socket clientSocket = null;

    public static void main(String[] args) {
        GUI app = new GUI();
        app.setVisible(true);

        while(clientSocket==null) {
            try {
                clientSocket = new Socket("localhost", 4444);
                System.out.println("Подключение к серверу успешно");
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("Нет подключения к серверу");
            }
        }

        ObjectOutputStream serializer = null;
        try {
            serializer = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        while (true){
            if(messages.size()>= 10){
                 completedMessages = messages;
                 messages = new ArrayList<>(10);
                 try {
                     serializer.writeObject(completedMessages);
                     serializer.flush();
                     System.out.println("Отправлен пакет из " + completedMessages.size() + " сообщений");
                     counter =+ completedMessages.size();
                     completedMessages = new ArrayList<>();
                 } catch (IOException e) {
                     System.out.println(e.getMessage());
                 }
                 if(counter >= maxCounter){
                     app.executorService.shutdownNow();
                     completedMessages.add(new Message(new Template()));
                     try {
                         serializer.writeObject(completedMessages);
                         serializer.flush();
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                     System.out.println("Выполнено");
                     System.out.println(Message.getNumber());
                     return;
                 }
             }
            try{
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
     public synchronized static void pullMessage(Message mes){
        messages.add(mes);
     }

    public static int getMaxCounter() {
        return maxCounter;
    }
}
