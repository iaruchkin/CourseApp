package com.iaruchkin.library;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {

    public static String loadJson(String fileName) {
        try {
            InputStream inputStream = new FileInputStream(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
