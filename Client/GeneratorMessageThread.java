package ru.smirnov;

public class GeneratorMessageThread implements Runnable {

    @Override
    public void run() {
        System.out.println("Поток запущен");
        for(int i=0;i<(Main.getMaxCounter()/GUI.getCountThread());i++) {
            int random = (int) (Math.random() * 5);
            Message mes = new Message(ReaderTemplates.getTemplate(random));
            Main.pullMessage(mes);
        }
    }
}
