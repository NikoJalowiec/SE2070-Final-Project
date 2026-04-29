import java.util.Scanner;
import java.net.*;
public class Conditions {
    private String name;
    private String temperature;
    private String description;
    private String windSpeed;
    private String feelsLike;

    Conditions(int code, int system){
        dataGet(code, system);
    }

    public String getName(){
        return name;
    }

    public String getTemp(){
        return temperature;
    }

    public String getDesc(){
        return description;
    }

    public String getWindSpeed(){
        return windSpeed;
    }

    public String getFeelsLike(){
        return feelsLike;
    }

    //read data from API response
    private void dataGet(int code, int system){
        try {
            String urlString = "http://api.weatherapi.com/v1/current.json?key=37f38c74e7b74c9daab133617262904&q=" + code;
            URL url = new URL(urlString);
            Scanner input = new Scanner(url.openStream());

            String apiOutput = input.nextLine();
            System.out.println(apiOutput);
            //split API response by quotation marks
            String[] split = apiOutput.split("\"");

            //cylce through split array to find data
            for(int i = 0; i < split.length; i++){
                //place name
                if(split[i].contains("name")){
                            name = split[i+2];
                    }
                //temp in F
                else if(split[i].contains("temp_f") && system == 0){
                    String temp = split[i+1].replace(":","");
                    temp = temp.replace(",","");
                    temperature = temp+"F";
                }
                //temp in C
                else if(split[i].contains("temp_c") && system == 1){
                    String temp = split[i+1].replace(":","");
                    temp = temp.replace(",","");
                    temperature = temp+"C";
                }
                //text description e.g. "Partly cloudy"
                else if(split[i].contains("text")){
                    description = split[i+2];
                    }
                //windspeed in mph
                else if(split[i].contains("wind_mph") && system == 0){
                    String temp = split[i+1].replace(":","");
                    temp = temp.replace(",","");
                    windSpeed = temp+" MPH";
                }
                //windspeed in kph
                 else if(split[i].contains("wind_kph") && system == 1){
                    String temp = split[i+1].replace(":","");
                    temp = temp.replace(",","");
                    windSpeed = temp+" KPH";
                 }
                 //temperature it feels like in F
                 else if(split[i].contains("feelslike_f") && system == 0){
                    String temp = split[i+1].replace(":","");
                    temp = temp.replace(",","");
                    feelsLike = temp+"F";
                 }
                 //temperature it feels like in C
                 else if(split[i].contains("feelslike_c") && system == 1){
                    String temp = split[i+1].replace(":","");
                    temp = temp.replace(",","");
                    feelsLike = temp+"C";
                 }
            }
        }
        catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
