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
    private UIDraw drawUI = new UIDraw();
    private Controller ctrl = new Controller();
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
        
        Button nextBtn = drawUI.drawButton("Next=>");
        Button prevBtn = drawUI.drawButton("<=Previous");
        Button findFilesBtn = drawUI.drawButton("Find files");
        
        TextField pathInputField = drawUI.drawPathTextField();  // single line text input
        TextField textInputField = drawUI.drawTextToSearchField();        
        TextArea fileContentArea = drawUI.drawFileContentArea();      // multiple lines text input
        
        ListView foundFilesView = drawUI.drawFilesListView();
        foundFilesView.setItems(files);
        
        
        
        
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
                if(isFilePathChanged){  // means that file changed and need to read for the first time
                    isFilePathChanged = false;  // means that we search in the same file
                    String selectedPath =  (String)foundFilesView.getSelectionModel().getSelectedItem();
                    System.out.println(selectedPath);
                    ctrl.readFileFirstTime(selectedPath, textInputField.getText(), fileContentArea);
                } else{
                    //ctrl.readFileAgain();
                }
            }
        }));
        
        HBox mainPane = new HBox();
        
        VBox leftPane = new VBox(10);
        leftPane.getChildren().addAll(folderPathLabel, pathInputField, findFilesBtn, foundFilesView);
        
        GridPane rightPane = new GridPane();
        rightPane.setPadding(new Insets(10, 10, 10, 10));
        rightPane.setHgap(8);
        rightPane.setVgap(8);
        rightPane.add(textToFindLabel, 0, 0, 2, 1);
        rightPane.add(textInputField, 0, 1, 2, 1);
        rightPane.add(prevBtn, 0, 2);
        rightPane.add(nextBtn, 1, 2);
        rightPane.add(fileContentArea, 0, 3, 2, 1);
        
        mainPane.getChildren().addAll(leftPane, rightPane);
        
        
        var scene = new Scene(mainPane, 800, 800);
        stage.setScene(scene);
        stage.setTitle("LogSearcher " + SystemInfo.appVersion());
        stage.setMinHeight(800);
        stage.setMinWidth(800);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}