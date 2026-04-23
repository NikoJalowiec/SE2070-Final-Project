import java.util.Scanner;
import java.net.*;
public class Conditions {
    private String name;
    private double temperature;
    private String description;
    private double windSpeed;
    private double feelsLike;

    Conditions(int code, int system){
        dataGet(code, system);
    }

    public String getName(){
        return name;
    }

    public double getTemp(){
        return temperature;
    }

    public String getDesc(){
        return description;
    }

    public double getWindSpeed(){
        return windSpeed;
    }

    public double getFeelsLike(){
        return feelsLike;
    }

    //read data from API response
    private void dataGet(int code, int system){
        try {
            String urlString = "http://api.weatherapi.com/v1/current.json?key=e861eb47c7104edcb73220752261504&q=" + code;
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
                    temperature = Double.parseDouble(temp);
                }
                //temp in C
                else if(split[i].contains("temp_c") && system == 1){
                    String temp = split[i+1].replace(":","");
                    temp = temp.replace(",","");
                    temperature = Double.parseDouble(temp);
                }
                //text description e.g. "Partly cloudy"
                else if(split[i].contains("text")){
                    description = split[i+2];
                    }
                //windspeed in mph
                else if(split[i].contains("wind_mph") && system == 0){
                    String temp = split[i+1].replace(":","");
                    temp = temp.replace(",","");
                    windSpeed = Double.parseDouble(temp);
                }
                //windspeed in kph
                 else if(split[i].contains("wind_kph") && system == 1){
                    String temp = split[i+1].replace(":","");
                    temp = temp.replace(",","");
                    windSpeed = Double.parseDouble(temp);
                 }
                 //temperature it feels like in F
                 else if(split[i].contains("feelslike_f") && system == 0){
                    String temp = split[i+1].replace(":","");
                    temp = temp.replace(",","");
                    feelsLike = Double.parseDouble(temp);
                 }
                 //temperature it feels like in C
                 else if(split[i].contains("feelslike_c") && system == 1){
                    String temp = split[i+1].replace(":","");
                    temp = temp.replace(",","");
                    feelsLike = Double.parseDouble(temp);
                 }
            }
        }
        catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
