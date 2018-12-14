package ru.smirnov;

import java.io.*;

public class ReaderTemplates {
    private static Template templates[] = new Template[5];

    public static void readerTemplates(File file){
        for(int i = 0; i<templates.length; i++){
            templates[i] = new Template();
        }
        int index_template = -1;
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String strLine;
            boolean check_id = false;
            while((strLine = br.readLine()) != null){
                if(strLine.indexOf("Template")!=-1){
                    index_template++;
                    check_id = false;
                    System.out.println("Template " + (index_template+1));
                }
                else if(strLine.indexOf("String")!=-1){
                    String temp = "";
                    for(int i = strLine.indexOf("String")+7; i<strLine.indexOf(";"); i++) {
                        temp += strLine.charAt(i);
                    }
                    System.out.println(temp);
                    templates[index_template].addKey(temp);
                }
                else if(check_id == false) {
                    for (int i = 0; i < strLine.length(); i++) {
                        if (strLine.charAt(i) == '=') {
                            templates[index_template].setId(Character.getNumericValue(strLine.charAt(i + 1)));
                            check_id = true;
                            System.out.println("Id = " + templates[index_template].getId());
                        }
                    }
                }
            }


        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Template getTemplate(int index){
        if(index!=-1) {
            return templates[index];
        }
        else{
            return new Template();
        }
    }
}
