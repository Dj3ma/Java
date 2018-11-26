package ru.smirnov;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GUI extends JFrame {

    public GUI(){
        setTitle("Messenger");
        setBounds(100, 100, 500, 100);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int amount = 100;
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

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i<amount; i++){
                    int random = (int)(Math.random()*5);
                    Server.getServer().setMessage(new Message(keys_templates.getTemplate(random)));
                }
                Server.getServer().saveHistory(fileHistory);
                Server.getServer().saveFinalSet(fileFinal);
                System.out.println("Done");
            }
        });
        getContentPane().add(panel);

    }
}
