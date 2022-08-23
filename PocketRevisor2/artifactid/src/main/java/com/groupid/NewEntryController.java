package com.groupid;

import java.io.IOException;
import java.time.LocalTime;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


public class NewEntryController {

    @FXML
	public Text result;

    @FXML
    public DatePicker datePicker;

    @FXML
    public TextField startTime;

    @FXML
    public TextField endTime;

    Wages wage = new Wages();
    SaveHandler sh = new SaveHandler();

    @FXML
    private void backToOverview() throws IOException{
        App.setRoot("overview");
    }

    @FXML
    private void switchToOverview() throws IOException {
        result.setVisible(false);
        //TODO: quality control
         //restrict ability to write letters, should only be able to type numbers and ":"
         //check if user has written valid times, e.g. not "12:70"
        if(datePicker.getValue() == null || startTime.getText().isEmpty() || endTime.getText().isEmpty()){
            result.setVisible(true);
            result.setText("ERROR: please choose date, start and end time");
        }
        else if(startTime.getText().length() != 5 || endTime.getText().length() != 5){
            result.setVisible(true);
            result.setText("ERROR: please choose valid start and end time");
        }
        else{
            LocalTime localStart = wage.getTime(startTime.getText());
            LocalTime localEnd = wage.getTime(endTime.getText());
            if(localStart == null || localEnd == null){
                result.setVisible(true);
                result.setText("ERROR: please choose valid start and end time");
            }
            else if(localStart.isAfter(localEnd)){
                result.setVisible(true);
                result.setText("ERROR: start time has to be before end time");
            }
            else{
                wage.writeToSaveFile(datePicker.getValue(), localStart, localEnd);
                App.setRoot("overview");
            }
        }
    }
}
