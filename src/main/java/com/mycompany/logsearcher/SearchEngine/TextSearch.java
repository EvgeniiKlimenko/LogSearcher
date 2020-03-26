/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.logsearcher.SearchEngine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.TextArea;

/**
 *
 * @author brokenhead
 */
public class TextSearch {

    /*
    String text = "I love you so much";
    String wordToFind = "love";
    Pattern word = Pattern.compile(wordToFind);
    Matcher match = word.matcher(text);

while (match.find()) {
     int end = matcher.end();
     int start = matcher.start();
     file.
}
    
     */
    public void doSearch(FoundFile file, TextArea fileContentArea, String textToSearch) {
        Pattern textPattern = Pattern.compile(textToSearch);
        Matcher matcher = textPattern.matcher(fileContentArea.getText());
        while (matcher.find()) {
            //int end = matcher.end();
            int start = matcher.start();
            file.addIndex(start);
            System.out.println("Found match at: " + start);
        }
        // point to the first found, maybe
    }

}
