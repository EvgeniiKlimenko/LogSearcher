/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.logsearcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private Scanner scnr;
    private FileInputStream is;

    public ArrayList processPath(String enteredPath) {
        File requestedPath = new File(enteredPath);
        ArrayList<String> al = new ArrayList();
        if (requestedPath.exists()) {
            boolean isDir = requestedPath.isDirectory();
            if (isDir) {
                File[] filesList = requestedPath.listFiles();
                for (File fl : filesList) {
                    String ap = fl.getAbsolutePath();
                    al.add(ap);
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

    public void readFileFirstTime(String path, String text, TextArea fileContentArea) {
        boolean isFound = false;
        try {
            // first clean previous file shit
            if (is != null) {// first clean previous file shit
                is.close();
            }
            if (scnr != null) {
                scnr.close();
            }
            // then create new 

            is = new FileInputStream(path);
            scnr = new Scanner(is, "UTF-8");
            while (scnr.hasNextLine() && !isFound) {
                String line = scnr.nextLine();
                fileContentArea.appendText(line);   // first append..
                if (line.contains(text)) {            // then search and hightlight
                    isFound = true;
                    // highlight founded word(s)
                }
                //fileContentArea.appendText(line);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            scnr.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            try {
                is.close();
            } catch (IOException ex1) {
                ex1.printStackTrace();
            }
        }
    }

    public void readFileAgain(String text, TextArea fileContentArea) {
        boolean isFound = false;
        while (scnr.hasNextLine() && !isFound) {
            String line = scnr.nextLine();
            if (line.contains(text)) {
                isFound = true;
            }
            fileContentArea.appendText(line);
        }
    }

}
