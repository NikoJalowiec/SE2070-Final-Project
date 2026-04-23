import java.lang.reflect.GenericDeclaration;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.scene.layout.StackPane;

public class App extends Application{

    int code = 0;
    @Override
    public void start(Stage stage){
        try{
            StackPane root = new StackPane();
            GridPane current = new GridPane();
            //build scene
            Scene scene = new Scene(root,1100,600);
            stage.setTitle("Weather");

            //search bar page
            VBox searchPane = new VBox();
            searchPane.setAlignment(Pos.CENTER);
            HBox searchBar = new HBox();
            searchBar.setAlignment(Pos.CENTER);
            searchPane.getChildren().add(searchBar);

            //search bar
            TextField codeEntry = new TextField();
            codeEntry.setPromptText("Please enter a zipcode");
            codeEntry.getText();
            Button enter = new Button("Enter");
            searchBar.getChildren().addAll(codeEntry, enter);
            root.getChildren().add(searchPane);

            //current weather page
            String[] data = getConditions(code);
            Text temp = new Text(data[0] + "F");
            temp.setStyle("-fx-font: 200 arial;");
            Text feel = new Text(data[1] + "F");
            feel.setStyle("-fx-font: 120 arial;");
            Text desc = new Text(data[2]);
            desc.setStyle("-fx-font: 100 arial;");
            Text wind = new Text(data[3]);
            wind.setStyle("-fx-font: 100 arial;");

            //forecast page
            HBox dayFormatting = new HBox();
            HBox forecastButtons = new HBox();
            dayFormatting.setAlignment(Pos.CENTER);
            forecastButtons.setAlignment(Pos.BOTTOM_RIGHT);
            dayFormatting.setSpacing(10);


            StackPane[] days = new StackPane[7];
            for(int i = 0; i < 7; i++){
                days[i] = new StackPane();
            }
            
            dayFormatting.getChildren().addAll(days[0],days[1],days[2],days[3],days[4],days[5],days[6]);

            //make the shapes the data is displayed on
            Rectangle[] panel = new Rectangle[7];
            for(int i = 0; i < 7; i++){
                panel[i] = new Rectangle(1,1);
                panel[i].widthProperty().bind(scene.widthProperty().multiply(0.12));
                panel[i].heightProperty().bind(scene.heightProperty().multiply(0.75));
                panel[i].setFill(Color.LIGHTGRAY);
                panel[i].setStroke(Color.LIGHTGRAY);
                days[i].getChildren().add(panel[i]);
            }

            //make the formatting for the datafields
            VBox[] daysData = new VBox[7];
            String[][] forecastData = getForecast(code);
            Text[][] dataFields = new Text[5][7];

            for(int i = 0; i < 5; i++){
                for(int j = 0; j < 7; j++){
                    dataFields[i][j] = new Text();
                }
            }

            for(int i = 0; i < 7; i++){
                daysData[i] = new VBox();
                daysData[i].setAlignment(Pos.CENTER);
                dataFields[0][i].setText("High: " + forecastData[0][i] + "F");
                dataFields[0][i].setStyle("-fx-font:20 arial;");
                dataFields[1][i].setText("Low: " + forecastData[1][i] + "F");
                dataFields[1][i].setStyle("-fx-font:20 arial;");
                dataFields[2][i].setText(forecastData[2][i]);
                dataFields[3][i].setText(forecastData[3][i] + " MPH");
                dataFields[3][i].setStyle("-fx-font:15 arial;");
                dataFields[4][i].setText(forecastData[4][i] + "%");
                dataFields[4][i].setStyle("-fx-font:15 arial;");

                daysData[i].getChildren().addAll(dataFields[0][i],dataFields[1][i],dataFields[2][i],dataFields[3][i],dataFields[4][i]);
                days[i].getChildren().add(daysData[i]);
            }


            Button refresh = new Button("Refresh");
            refresh.setOnAction(
                new EventHandler<ActionEvent>(){
                    public void handle(ActionEvent e){
                    String[] data = getConditions(code);
                    temp.setText(data[0] + "F");
                    feel.setText(data[1] + "F");
                    desc.setText(data[2]);
                    wind.setText(data[3]);
                    }
                }
            );
            Button forecastButton = new Button("Forecast");
            forecastButton.setOnAction(
                new EventHandler<ActionEvent>(){
                    public void handle(ActionEvent e){
                        root.getChildren().remove(current);


                        root.getChildren().addAll(dayFormatting, forecastButtons);
                        String[][] forecastData = getForecast(code);
                        for(int i = 0; i < 7; i++){
                    
                            dataFields[0][i].setText("High: " + forecastData[0][i] + "F");
                            dataFields[1][i].setText("Low: " +forecastData[1][i] + "F");
                            dataFields[2][i].setText(forecastData[2][i]);
                            dataFields[3][i].setText(forecastData[3][i] + " MPH");
                            dataFields[4][i].setText(forecastData[4][i] + "%");
                        }
                    }
                }
            );

            Button currentButton = new Button("Current");
            forecastButtons.getChildren().add(currentButton);
            currentButton.setOnAction(new EventHandler<ActionEvent>(){
                    public void handle(ActionEvent e){
                        root.getChildren().removeAll(dayFormatting, forecastButtons);

                        String[] data = getConditions(code);
                        temp.setText(data[0] + "F");
                        feel.setText(data[1] + "F");
                        desc.setText(data[2]);
                        wind.setText(data[3]);
                        root.getChildren().add(current);
                    }
                }
            );





            // search enter function
            enter.setOnAction(
                new EventHandler<ActionEvent>(){
                    public void handle(ActionEvent e){
                        //assign location
                        code = Integer.parseInt(codeEntry.getText());
                        //remove search page
                        root.getChildren().remove(searchPane);

                        //add current page
                        String[] data = getConditions(code);
                        temp.setText(data[0] + "F");
                        feel.setText(data[1] + "F");
                        desc.setText(data[2]);
                        wind.setText(data[3]);
                        current.getChildren().addAll(temp,feel,desc,wind,refresh,forecastButton);
                        GridPane.setConstraints(temp,0,0);
                        GridPane.setConstraints(feel,0,1);
                        GridPane.setConstraints(desc,0,2);
                        GridPane.setConstraints(wind,0,3);
                        GridPane.setConstraints(refresh,6,3);
                        GridPane.setConstraints(forecastButton, 7,3);
                        root.getChildren().add(current);

                    }
                });
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        launch(args);
        // Scanner input = new Scanner(System.in);
        // int code;
        // int system;

        // System.out.println("Enter postal code");
        // code = Integer.parseInt(input.nextLine());
        // System.out.println("Enter measurement system");
        // system = Integer.parseInt(input.nextLine());

        // Conditions test = new Conditions(code, system);
        

         //System.out.println(test.getName());
        // System.out.println(test.getTemp());
        // System.out.println(test.getDesc());
        // System.out.println(test.getWindSpeed());
        // System.out.println(test.getFeelsLike());

        // System.out.println();

        // Forecast test2 = new Forecast(code,system);
        //  System.out.println(test2.getName());
        //  for(int i = 0; i < 7; i++){
        //      System.out.println("Day " + i);
        //      System.out.println("High: " +test2.getTempHigh()[i]);
        //      System.out.println("Low: " + test2.getTempLow()[i]);
        //      System.out.println("Description: " + test2.getDesc()[i]);
        //      System.out.println("Wind speed: " + test2.getWindSpeed()[i]);
        //      System.out.println("Chance of Rain: " + test2.getPrecipChance()[i]);
        //      System.out.println();
        //  }
    }

    String[] getConditions(int code){
        Conditions current = new Conditions(code,0);
        String[] data = new String[4];
        data[0] = current.getTemp()+"";
        data[1] = current.getFeelsLike()+"";
        data[2] = current.getDesc();
        data[3] = current.getWindSpeed()+"";
        return data;

    }

    String[][] getForecast(int code){
        Forecast forecast = new Forecast(code,0);
        String[][] data = new String[5][7];
        data[0] = forecast.getTempHigh();
        data[1] = forecast.getTempLow();
        data[2] = forecast.getDesc();
        data[3] = forecast.getWindSpeed();
        data[4] = forecast.getPrecipChance();
        return data;

    }
}
