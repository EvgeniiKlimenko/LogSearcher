/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.logsearcher.UICreate;

import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
/**
 * drawing UI.
 * @author brokenhead
 */
public class UIDraw {
    
    public Button drawButton(String title){
        Button btn = new Button(title);
        return btn;
    }
    
    public TextField drawPathTextField(){
        TextField txt = new TextField();
        txt.setPromptText("Full path to folder with log files...");
        txt.setPrefSize(500, 25);
        return txt;
    }
    
    public TextField drawTextToSearchField(){
        TextField txt = new TextField();
        txt.setPromptText("Enter text here.");
        txt.setPrefSize(500, 25);
        return txt;
    }
    
    public TextArea drawFileContentArea(){
        TextArea fileContent = new TextArea();
        fileContent.setPromptText("File's content here.");
        fileContent.setPrefSize(600, 800);
        
        //fileContent.setFont(font);
        fileContent.setWrapText(true); //...the text should wrap onto another line on area borders. 
        return fileContent;
    }
    
    public TextArea drawCopyTextArea(){
        TextArea copyContent = new TextArea();
        copyContent.setPromptText("Copy here selected text.");
        copyContent.setPrefSize(400, 600);
        copyContent.setEditable(true);
        
        //fileContent.setFont(font);
        copyContent.setWrapText(true); //...the text should wrap onto another line on area borders. 
        return copyContent;
    }
    
    public ListView drawFilesListView(){
        ListView filesFound = new ListView();
        filesFound.setMaxWidth(500);
        //filesFound.setMinSize(800, 300);
        filesFound.setPrefSize(500, 300);
        return filesFound;
    }
    
}
