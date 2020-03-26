package com.mycompany.logsearcher;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import com.mycompany.logsearcher.UICreate.UIDraw;
import com.mycompany.logsearcher.SearchEngine.TextSearch;
import com.mycompany.logsearcher.SearchEngine.FilesHandler;
import com.mycompany.logsearcher.SearchEngine.FoundFile;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

    private FoundFile currentSelectedFile;
    private final UIDraw drawUI = new UIDraw();
    private final FilesHandler filesWork = new FilesHandler();
    private final TextSearch search = new TextSearch();
    private ObservableList<String> files = FXCollections.observableArrayList("No results yet."); // default value
    private boolean isFilesPathChanged = false;
    private String currentTextToSearch;
    private String currentFilesPath;

    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();
        Label label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

        Label folderPathLabel = new Label("Enter folder's path:");
        Label listFilesLabel = new Label("Text log files found:");
        Label textToFindLabel = new Label("Text to find:");
        Label notesTextLabel = new Label("Saved notes:");
        //???
        Label fileSizeLabel = new Label("Curret file size:");

        Button nextBtn = drawUI.drawButton("Next=>");
        Button prevBtn = drawUI.drawButton("<=Previous");
        Button findFilesBtn = drawUI.drawButton("Find files");
        Button doSearchBtn = drawUI.drawButton("Find text");
        Button copyTextBtn = drawUI.drawButton("Copy text");

        TextField pathInputField = drawUI.drawPathTextField();  // single line text input
        TextField textInputField = drawUI.drawTextToSearchField();
        TextArea fileContentArea = drawUI.drawFileContentArea();      // multiple lines text input
        TextArea notesTextArea = drawUI.drawNotesTextArea();

        ListView foundFilesView = drawUI.drawFilesListView();
        foundFilesView.setItems(files);

        foundFilesView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                //isFilePathChanged = true;   //reset the flag on every change of selection
                fileContentArea.clear();
                String basicPath = pathInputField.getText();
                String selectedFile = (String) foundFilesView.getSelectionModel().getSelectedItem();// get file's name
                if(currentSelectedFile != null){
                    currentSelectedFile.resetIndex();
                }
                currentSelectedFile = filesWork.readWholeFile(basicPath, selectedFile, fileContentArea);
                //currentSelectedFile = filesWork.getSavedFile(basicPath + selectedFile);
            }
        });

        findFilesBtn.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                //isFilesPathChanged = true;
                if (currentFilesPath == null) { // at application's star point
                    currentFilesPath = pathInputField.getText();   
                }
                String newPath = pathInputField.getText();
                // Empty field or same path:
                if (newPath.trim().length() == 0 || newPath.equalsIgnoreCase(currentFilesPath)) {
                    System.out.println("-----> Same files path or empty field.");
                    return;
                }
                currentFilesPath = newPath;
                files.clear();
                files.addAll(filesWork.processPath(newPath));
            }
        }));
        
        doSearchBtn.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                String newText = textInputField.getText();
                // Empty field or same text:
                if (newText.trim().length() == 0  || newText.equalsIgnoreCase(currentTextToSearch)) {
                    System.out.println("-----> Same text or empty field.");
                    return;
                }
                if (currentSelectedFile == null) {    //no file selected yet
                    return;
                }
                currentTextToSearch = newText;
                currentSelectedFile.resetCurrentSettings();
                search.doSearch(currentSelectedFile, fileContentArea, currentTextToSearch);
            }
        }));

        //text.trim().length() != 0
        nextBtn.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (currentSelectedFile != null) {
                    int index = currentSelectedFile.getNextFound();
                    fileContentArea.deselect();
                    fileContentArea.selectPositionCaret(index);
                    fileContentArea.selectNextWord();
                } else {System.out.println("----> No file selected yet!");}
            }
        }));

        prevBtn.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (currentSelectedFile != null) {
                    int index = currentSelectedFile.getPreviousFound();
                    fileContentArea.deselect();
                    fileContentArea.selectPositionCaret(index);
                    fileContentArea.selectNextWord();
                } else {System.out.println("----> No file selected yet!");}
            }
        }));

        copyTextBtn.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                notesTextArea.appendText("\n" + fileContentArea.getSelectedText());
            }
        }));

        HBox mainPane = new HBox(50);
        mainPane.setPadding(new Insets(20, 20, 20, 20));
        mainPane.setFillHeight(true);
        //mainPane.autosize();

        VBox firstPane = new VBox(20);
        firstPane.getChildren().addAll(folderPathLabel, pathInputField, findFilesBtn,
                listFilesLabel, foundFilesView, notesTextLabel, notesTextArea);
        firstPane.prefWidth(500);

        GridPane secondPane = new GridPane();
        secondPane.setPadding(new Insets(10, 10, 10, 10));

        secondPane.setHgap(20);
        secondPane.setVgap(20);
        secondPane.add(textToFindLabel, 0, 0, 2, 1);
        secondPane.add(textInputField, 0, 1, 2, 1);
        secondPane.add(doSearchBtn, 0, 2);
        secondPane.add(prevBtn, 1, 2);
        secondPane.add(nextBtn, 2, 2);
        secondPane.add(copyTextBtn, 3, 2);
        secondPane.add(fileContentArea, 0, 3, 4, 1);
        secondPane.add(fileSizeLabel, 0, 4);

        mainPane.getChildren().addAll(firstPane, secondPane);

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
