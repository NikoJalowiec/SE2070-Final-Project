import java.util.Scanner;
import java.net.*;
public class Forecast {
    private String name;
    private String[] tempHigh = new String[7];
    private String[] tempLow = new String[7];
    private String[] windSpeed = new String[7];
    private String[] precipChance = new String[7];
    private String[] description = new String[7];

    public String getName(){
        return name;
    }

    public String[] getTempHigh(){
        return tempHigh;
    }

    public String[] getTempLow(){
        return tempLow;
    }

    public String[] getDesc(){
        return description;
    }

    public String[] getWindSpeed(){
        return windSpeed;
    }

    public String[] getPrecipChance(){
        return precipChance;
    }

    Forecast(int code, int system){
        dataGet(code, system);
    }

    //read data from API response
    private void dataGet(int code, int system){
        try {
            String urlString = "https://api.weatherapi.com/v1/forecast.json?key=e861eb47c7104edcb73220752261504&q="+ code + "&days=7";
            URL url = new URL(urlString);
            Scanner input = new Scanner(url.openStream());

            String apiOutput = input.nextLine();
            System.out.println(apiOutput);
            //split API response by "date"
            String[] split = apiOutput.split("date");
            String[] split2 = split[0].split("\"");

            //place name
            for(int i = 0; i < split2.length; i++){
                if(split2[i].contains("name")){
                    name = split2[i+2];
                }
            }
            int day = 0;
            for(int i = 3; i < split.length; i++){
                split2 = split[i].split("\"");
                for(int j = 0; j < split2.length; j++){
                    //temp high in F
                    if(split2[j].contains("maxtemp_f") && system == 0){
                        String temp = split2[j+1].replace(":","");
                        temp = temp.replace(",","");
                        tempHigh[day] = temp+"F";
                    }
                    //temp high in C
                    else if(split2[j].contains("maxtemp_c") && system == 1){
                        String temp = split2[j+1].replace(":","");
                        temp = temp.replace(",","");
                        tempHigh[day] = temp+"C";
                    }
                    //temp low in F
                    else if(split2[j].contains("mintemp_f") && system == 0){
                        String temp = split2[j+1].replace(":","");
                        temp = temp.replace(",","");
                        tempLow[day] = temp+"F";
                    }
                    //temp low in C
                    else if(split2[j].contains("mintemp_c") && system == 1){
                        String temp = split2[j+1].replace(":","");
                        temp = temp.replace(",","");
                        tempLow[day] = temp+"C";
                    }
                    //text description e.g. "Partly cloudy"
                    else if(split2[j].contains("text")){
                        description[day] = split2[j+2];
                        }
                    //max windspeed in mph
                    else if(split2[j].contains("maxwind_mph") && system == 0){
                        String temp = split2[j+1].replace(":","");
                        temp = temp.replace(",","");
                        windSpeed[day] = temp+" MPH";
                    }
                    //max windspeed in kph
                    else if(split2[j].contains("maxwind_kph") && system == 1){
                        String temp = split2[j+1].replace(":","");
                        temp = temp.replace(",","");
                        windSpeed[day] = temp+" KPH";
                    }
                    //chance of precipitation
                    else if(split2[j].contains("daily_chance_of_rain")){
                        String temp = split2[j+1].replace(":","");
                        temp = temp.replace(",","");
                        precipChance[day] = temp;
                    }
                }
                day = day + 1;
            }
        }
        catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
