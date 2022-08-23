package com.groupid;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class OverviewController {

    @FXML
    public ScrollPane scrollPane;

    @FXML
    public VBox root;

    @FXML
    public Text totalText;

    @FXML
    public DatePicker startDate;

    @FXML
    public DatePicker endDate;

    private HashSet<WorkingDay> set = new HashSet<>();
    private Wages w = new Wages();
    private double total = 0;

    @FXML
    private void switchToNewEntry() throws IOException {
        total = 0;
        App.setRoot("newEntry");
    }

    //TODO:hva er problemet?
    //Ønsker å lagre Hashset-et mellom, men aner ikke hvordan
    //w.calculateWage() vil returnere set.
    //Lagre informasjonen når man taster det inn
    //Putte denne informasjonen inn i calculateWage() i en loop
    //Kjøre calculateWage() her.
    @FXML
    private void loadEntries(){
        total = 0;
        set.clear();
        SaveHandler saveHandler = new SaveHandler();
        String path = String.valueOf(Paths.get("artifactid", "src", "main", "java", "com", "groupid", "savefile.txt"));
        String textFromFile = saveHandler.readFromFile(path);
        String[] lines = textFromFile.split("\n");
        String[][] words = new String[lines.length][lines.length];
        for(int i = 0; i < lines.length; i++){
            words[i] = lines[i].split(" ");
        }
        //words[x][0] is date
        //words[x][1] is startTime
        //words[x][2] is endtime
        //wages.calculateWage(date, startTime, endTime) -> this will return the set
        //use this set to create the buttons
        for(int i = 0; i < words.length; i++){
            LocalDate date = LocalDate.parse(words[i][0]);
            LocalTime startTime = w.getTime(words[i][1]);
            LocalTime endTime = w.getTime(words[i][2]);
            set = w.calculateWage(date, startTime, endTime);
          }

        for(WorkingDay wd:set){
            total += wd.wage;
            Button btn = new Button("Date: " + wd.date + "\nShift: " + wd.startTime + " - " + wd.endTime + "\nEarned: " + String.format("%.2f", wd.wage) + ",-");
            btn.setMinWidth(370);
            btn.setMinHeight(65);
            root.getChildren().add(btn);
        }
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        scrollPane.setContent(root);
        scrollPane.setPannable(true);
        totalText.setText("Total: " + String.format("%.2f", total) + ",-");
    }

    @FXML
    private void search(){
        total = 0;
        scrollPane.setContent(null);
        LocalDate start = startDate.getValue();
        LocalDate end = endDate.getValue();
        total = 0;
        for(WorkingDay wd:set){
            if((wd.date.isAfter(start) || wd.date.isEqual(start)) && (wd.date.isBefore(end)) || wd.date.isEqual(end)){
                total += wd.wage;
                /*Button btn = new Button("Date: " + wd.date + "\nShift: " + wd.startTime + " - " + wd.endTime + "\nEarned: " + String.format("%.2f", wd.wage) + ",-");
                btn.setMinWidth(370);
                btn.setMinHeight(65);
                root.getChildren().add(btn);*/
            }
        }
        /*root.setSpacing(10);
        root.setPadding(new Insets(10));
        scrollPane.setContent(root);
        scrollPane.setPannable(true);*/
        totalText.setText("Total: " + String.format("%.2f", total) + ",-");
    }
}

//TODO: slette/redigere dager
//TODO: sjekke om man kan ha to like vakter