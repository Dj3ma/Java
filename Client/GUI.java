package ru.smirnov;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GUI extends JFrame {

    public GUI(){
        setTitle("Messenger");
        setBounds(100, 100, 500, 100);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Socket clientSocket = null;
        try {
            clientSocket = new Socket("localhost",4444);
            System.out.println("Подключение к серверу успешно");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Нет подключения к серверу");
        }

        ObjectOutputStream serializer = null;
        try {
            serializer = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        int amount = 100000;
        File dir = new File("src//ru//smirnov");
        File fileTemplates = new File(dir, "template.txt");
        File fileHistory = new File(dir, "history.txt");
        File fileFinal = new File(dir, "final.txt");
        ReaderTemplates keys_templates = new ReaderTemplates();
        keys_templates.readerTemplates(fileTemplates);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton start = new JButton("Start");
        panel.add(start);


        ObjectOutputStream finalSerializer = serializer;
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i<amount; i++){
                    int random = (int)(Math.random()*5);
                    try {
                        finalSerializer.writeObject((new Message(keys_templates.getTemplate(random))));
                        finalSerializer.flush();
                    } catch (IOException e1) {
                        System.out.println(e1.getMessage());
                    }
                }
                try {
                    finalSerializer.writeObject((new Message(keys_templates.getTemplate(-1))));
                    finalSerializer.flush();
                } catch (IOException e1) {
                    System.out.println(e1.getMessage());
                }
                System.out.println("Done");
            }
        });
        getContentPane().add(panel);

    }
}
