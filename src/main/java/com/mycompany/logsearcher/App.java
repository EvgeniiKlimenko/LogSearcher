package com.mycompany.logsearcher;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import com.mycompany.logsearcher.UICreate.UIDraw;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 * JavaFX App
 */

/*
/home/brokenhead/MyFolder

ObservableList<T> content = ...
 listView.setItems(content);
*/
public class App extends Application {
    private final UIDraw drawUI = new UIDraw();
    private final Controller ctrl = new Controller();
    private ObservableList<String> files = FXCollections.observableArrayList("No results yet."); // default value
    private boolean isFilePathChanged = false;
    
    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();
        Label label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        
        Label folderPathLabel = new Label("Enter folder's path:");
        Label listFilesLabel = new Label("Text log files found:");
        Label textToFindLabel = new Label("Text to find:");
        
        //???
        Label fileSizeLabel = new Label("Curret file size:");
        
        Button nextBtn = drawUI.drawButton("Next=>");
        Button prevBtn = drawUI.drawButton("<=Previous");
        Button findFilesBtn = drawUI.drawButton("Find files");
        Button copyTextBtn = drawUI.drawButton("Copy text");
        
        TextField pathInputField = drawUI.drawPathTextField();  // single line text input
        TextField textInputField = drawUI.drawTextToSearchField();        
        TextArea fileContentArea = drawUI.drawFileContentArea();      // multiple lines text input
        TextArea copyTextArea = drawUI.drawCopyTextArea();
        
        ListView foundFilesView = drawUI.drawFilesListView();
        foundFilesView.setItems(files);
        
        // Add action contentArea.clear on change selection on listView!!!
        
        
        findFilesBtn.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                isFilePathChanged = true;
                files.clear();
                files.addAll(ctrl.processPath(pathInputField.getText()));
                //foundFilesView.setItems(files); // i dont need this?
            }
        }));
        
        nextBtn.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                fileContentArea.clear();
                if(isFilePathChanged){  // means that file changed and need to read for the first time
                    System.out.println("-----> Reading file for the first time");
                    isFilePathChanged = false;  // means that we search in the same file
                    String basicPath = pathInputField.getText();
                    String selectedFile =  (String)foundFilesView.getSelectionModel().getSelectedItem();// get file's name
                    System.out.println(selectedFile);
                    ctrl.readFileFirstTime(basicPath, selectedFile, textInputField.getText(), fileContentArea);
                } else{
                    System.out.println("-----> Reading the same file");
                    ctrl.readFileAgain(textInputField.getText(), fileContentArea);
                }
            }
        }));
        
        
        copyTextBtn.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                copyTextArea.appendText(fileContentArea.getSelectedText());
            }
        }));
        
        HBox mainPane = new HBox(50);
        mainPane.setPadding(new Insets(20, 20, 20, 20));
        mainPane.setFillHeight(true);
        //mainPane.autosize();
        
        VBox firstPane = new VBox(20);
        firstPane.getChildren().addAll(folderPathLabel, pathInputField, findFilesBtn, listFilesLabel, foundFilesView);
        firstPane.prefWidth(500);
        
        
        
        GridPane secondPane = new GridPane();
        secondPane.setPadding(new Insets(10, 10, 10, 10));
        
        secondPane.setHgap(20);
        secondPane.setVgap(20);
        secondPane.add(textToFindLabel, 0, 0, 2, 1);
        secondPane.add(textInputField, 0, 1, 2, 1);
        secondPane.add(prevBtn, 0, 2);
        secondPane.add(nextBtn, 1, 2);
        secondPane.add(copyTextBtn, 2, 2);
        secondPane.add(fileContentArea, 0, 3, 3, 1);
        secondPane.add(fileSizeLabel, 0, 4);
        
        VBox thirdPane = new VBox(20);
        thirdPane.getChildren().addAll(copyTextArea);
        
        mainPane.getChildren().addAll(firstPane, secondPane, thirdPane);
        
        var scene = new Scene(mainPane, 1400, 900);
        stage.setScene(scene);
        stage.setTitle("LogSearcher " + SystemInfo.appVersion());
        stage.setMinHeight(800);
        stage.setMinWidth(800);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}