package ru.smirnov;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GUI extends JFrame {
    private static int countThread = 4;
    public ExecutorService executorService = Executors.newFixedThreadPool(countThread);

    public GUI(){

        setTitle("Messenger");
        setBounds(100, 100, 500, 100);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




        int amount = 100000;
        File dir = new File("src//ru//smirnov");
        File fileTemplates = new File(dir, "template.txt");
        File fileHistory = new File(dir, "history.txt");
        File fileFinal = new File(dir, "final.txt");
        ReaderTemplates.readerTemplates(fileTemplates);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton start = new JButton("Start");
        panel.add(start);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < countThread; i++) {
                    executorService.submit(new GeneratorMessageThread());
                }
                /*for(int i = 0; i<amount; i++){
                    int random = (int)(Math.random()*5);
                    try {
                        finalSerializer.writeObject((new Message(ReaderTemplates.getTemplate(random))));
                        finalSerializer.flush();
                    } catch (IOException e1) {
                        System.out.println(e1.getMessage());
                    }
                }
                try {
                    finalSerializer.writeObject((new Message(ReaderTemplates.getTemplate(-1))));
                    finalSerializer.flush();
                } catch (IOException e1) {
                    System.out.println(e1.getMessage());
                }*/
            }
        });
        getContentPane().add(panel);

    }
    public static int getCountThread(){
        return countThread;
    }
}
