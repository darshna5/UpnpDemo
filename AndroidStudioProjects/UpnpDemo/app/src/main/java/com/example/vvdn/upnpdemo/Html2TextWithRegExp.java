package com.example.vvdn.upnpdemo;

import java.io.BufferedReader;
import java.io.FileReader;

public class Html2TextWithRegExp {
    private Html2TextWithRegExp() {}

    public static void main (String[] args) throws Exception{
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader("java-new.html"));
        String line;
        while ( (line=br.readLine()) != null) {
            sb.append(line);
            // or
            //  sb.append(line).append(System.getProperty("line.separator"));
        }
        String nohtml = sb.toString().replaceAll("\\<.*?>","");
        System.out.println(nohtml);
    }
}
