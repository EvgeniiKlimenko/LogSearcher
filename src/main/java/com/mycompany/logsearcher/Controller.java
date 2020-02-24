/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.logsearcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

/**
 *
 * @author brokenhead
 */
public class Controller {

    private FileReader fr;
    private BufferedReader br;
    boolean isFound = false;

    public ArrayList processPath(String enteredPath) {
        File requestedPath = new File(enteredPath);
        ArrayList<String> al = new ArrayList();
        if (requestedPath.exists()) {
            boolean isDir = requestedPath.isDirectory();
            if (isDir) {
                File[] filesList = requestedPath.listFiles();
                for (File fl : filesList) {
                    if (fl.isFile()) {
                        String ap = fl.getName();
                        al.add(ap);
                    }
                }
                return al;
            } else {
                al.add(requestedPath.getAbsolutePath());
                return al;
            }
        }
        al.add("No such file. Check your path, dude.");
        return al;
    }

    public void readFileFirstTime(String basicPath, String path, String text, TextArea fileContentArea) {
        System.out.println("readFileFirstTime() method");
        isFound = false;
        System.out.println(basicPath + File.separator + path); // The system-dependent default name-separator character
        try {
            // first clean previous file shit
            if (fr != null) {// first clean previous file shit
                fr.close();
            }
            if (br != null) {
                br.close();
            }
            // then create new 
            fr = new FileReader(basicPath + File.separator + path);
            br = new BufferedReader(fr);

            while (!isFound) {                  // br.hasNextLine() && 
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                fileContentArea.appendText(line + "\n");
                if (line.contains(text)) {            // then search and hightlight
                    isFound = true;
                    // highlight founded word(s)
                }
            }
            System.out.println("----->... end");
        } catch (IOException ex) {
            try {
                fr.close();
                br.close();
            } catch (IOException ex1) {
                ex1.printStackTrace();
            }

        }
    }

    public void readFileAgain(String text, TextArea fileContentArea) {
        System.out.println("readFileAgain() method");
        isFound = false;
        try {
            while (!isFound) {
                System.out.println("readFileAgain() - > while(){}");
                String line = br.readLine();
                if (line == null) {
                    System.out.println("readFileAgain() - > if line empty");
                    break;  // if null => reached end of file
                }
                fileContentArea.appendText(line + "\n");
                if (line.contains(text)) {            // then search and hightlight
                    System.out.println("readFileAgain() - > find text");
                    isFound = true;
                    // highlight founded word(s)
                }
            }
        } catch (IOException ex) {
            try {
                fr.close();
                br.close();
            } catch (IOException ex1) {
                ex1.printStackTrace();
            }
        }
    }

}
