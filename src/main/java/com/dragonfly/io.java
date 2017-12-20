package com.dragonfly;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class io {
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        List<Object> objectList = new ArrayList<>();
        // objectList.toArray()；


        FileInputStream in = null;
        FileOutputStream out = null;

        try {
            in = new FileInputStream("/Users/fanggang/test/11.jpg");
            out = new FileOutputStream("/Users/fanggang/test/21.jpg");
            int c;

            while ((c = in.read()) != -1) {
                out.write(c);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
