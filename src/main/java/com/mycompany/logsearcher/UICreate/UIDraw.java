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
        txt.setMaxSize(60, 15);
        return txt;
    }
    
    public TextField drawTextToSearchField(){
        TextField txt = new TextField();
        txt.setPromptText("Enter text here.");
        txt.setMaxSize(60, 15);
        return txt;
    }
    
    public TextArea drawFileContentArea(){
        TextArea fileContent = new TextArea();
        fileContent.setPromptText("File's content here.");
        fileContent.setMaxSize(500, 900);
        
        return fileContent;
    }
    
    public ListView drawFilesListView(){
        ListView filesFound = new ListView();
        filesFound.setMaxSize(650, 300);
        return filesFound;
    }
    
}
