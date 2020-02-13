package com.hallberg;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Label title;

    @FXML
    private Label inputTitle;

    @FXML
    private Label inputText;

    @FXML
    private RadioButton thisMonth;

    @FXML
    private RadioButton nextMonth;

    @FXML
    private ToggleGroup sign;

    @FXML
    private RadioButton aries;

    private void init() {
        title.setFont(new Font("Arial", 18));
        title.setText("Welcome to Horoscope FX");
        inputTitle.setStyle("-fx-font-weight: bold;");
        thisMonth.setSelected(true);
        aries.setSelected(true);
        scrape();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
    }

    public void exitProgram() {
        System.exit(0);
    }

    public void aboutProgram() {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/images/h.png").toString()));
        dialog.setTitle("About");
        //alert.setHeaderText("Dialog popup");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        String s ="Horoscope FX version 1.0\n\n" +
                "Pick your sign and desired time period.\n" +
                "Your forecast will be shown for that \n" +
                "sign and time period in the lower\n" +
                "right corner.\n\n" +
                "\u00a9 Mikael Hallberg 2020";
        dialog.setContentText(s);
        dialog.getDialogPane().setBackground(Background.EMPTY);
        dialog.getDialogPane().setLayoutX(100.100);
        dialog.getButtonTypes().set(0, new ButtonType("OK", ButtonBar.ButtonData.LEFT));
        dialog.show();
    }

    public String[] chooseDate() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int monthInput = localDate.getMonthValue();
        int year = localDate.getYear();

        if(nextMonth.isSelected()) {

            monthInput++;

            if(monthInput == 13) {
                monthInput = 1;
                year++;
            }
        }

        MonthPicker monthPicker = new MonthPicker();
        String month = monthPicker.select(monthInput);

        String[] currentDate = new String[2];

        currentDate[0] = month;
        currentDate[1] = Integer.toString(year);

        return currentDate;
    }

    public String chooseSign() {
        RadioButton selectedRadioButton = (RadioButton) sign.getSelectedToggle();

        return selectedRadioButton.getText().toLowerCase();
    }

    @FXML
    private void scrape() {

        String[] date = chooseDate();
        String sign = chooseSign();

        //System.out.println(date[0]);
        //System.out.println(date[1]);
        //System.out.println(sign);

        String url = "https://www.sunsigns.org/" + sign + "-" + date[0] + "-" + date[1] + "-horoscope/";
        //String url = "https://www.svd.se/";

        try {
            Document doc = Jsoup.connect(url).get();

            String pipeDelimitedTitle = doc.title();
            String[] titleArray = pipeDelimitedTitle.split("\\|");
            String title = titleArray[0];

            inputTitle.setText(title);

            Element p = doc.select("p[style]").first();
            String rawText = p.text();
            //Element body = doc.body();
            //Element h2 = body.select("h2[class='Teaser-heading']").first();
            //String rawText = h2.text();

            String text = rawText.replaceAll("((?:\\S+\\s){6}\\S+)(\\s)", "$1\n");

            inputText.setText(text);

        } catch (IOException e) {
            inputTitle.setText("Can't connect to " + e.getMessage() + ".");
            inputText.setText("Check your internet connection.");
        }
    }
}