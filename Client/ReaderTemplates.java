package ru.smirnov;

import java.io.*;

public class ReaderTemplates {
    private Template templates[] = new Template[5];

    public ReaderTemplates(){
        for(int i = 0; i<templates.length; i++){
            templates[i] = new Template();
        }
    }

    public void readerTemplates(File file){
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

    public Template getTemplate(int index){
        if(index!=-1) {
            return this.templates[index];
        }
        else{
            return new Template();
        }
    }
}
