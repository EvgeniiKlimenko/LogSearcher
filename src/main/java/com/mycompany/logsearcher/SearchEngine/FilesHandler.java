/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.logsearcher.SearchEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.TextArea;

/**
 *
 * @author brokenhead
 */
public class FilesHandler {
    //key - path; value - FoundFile.obj
    private Map<String, FoundFile> processedFiles = new HashMap<>();
    private FileReader fr;
    private BufferedReader br;
    boolean isFound = false;

    private boolean isNewFile(String path) {
        boolean check = processedFiles.containsKey(path);
        //FoundFile ff = processedFiles.get(path);
        if (!check) {
            System.out.println("----> File is new!");
            return true;
        } else {
            System.out.println("----> File is old!");
            return false;
        }
    }

    public FoundFile getSavedFile(String path) {
        FoundFile ff = processedFiles.get(path);
        return ff;
    }
    
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

    // Create FoundFile here
    public FoundFile readWholeFile(String basicPath, String path, TextArea fileContentArea) {
        isFound = false;
        FoundFile ff = null;
        System.out.println(basicPath + File.separator + path); // The system-dependent default name-separator character
        try {
            // first clean previous file shit
            if (fr != null) {
                fr.close();
            }
            if (br != null) {
                br.close();
            }
            // then create new
            String fullPath = basicPath + File.separator + path;
            File requestedFile = new File(fullPath);
            fr = new FileReader(requestedFile);
            br = new BufferedReader(fr);
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            fileContentArea.appendText(sb.toString());
            
            System.out.println("File: " + fullPath + " Lenght: " + fileContentArea.getLength());
            if (isNewFile(fullPath)) {
                ff = new FoundFile(fullPath, fileContentArea.getLength());
                processedFiles.put(fullPath, ff);
                System.out.println("----> New FoundFile created");
            } else {
                ff = processedFiles.get(fullPath);
                System.out.println("----> Old FoundFile returned");
            }
            
        } catch (IOException ex) {
            try {
                fr.close();
                br.close();
            } catch (IOException ex1) {
                ex1.printStackTrace();
            }
            fileContentArea.appendText("Something went wrong. I can't read this file.");
        }
        return ff;
    }
    
}
