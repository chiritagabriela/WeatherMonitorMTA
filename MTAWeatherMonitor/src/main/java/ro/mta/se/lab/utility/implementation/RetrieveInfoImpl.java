package ro.mta.se.lab.utility.implementation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import ro.mta.se.lab.model.CityModel;
import ro.mta.se.lab.model.WeatherInfoModel;
import ro.mta.se.lab.utility.RetrieveInfo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Class defining how to get data from OpenWeatherMap
 *
 * @author Chirita Gabriela
 */
public class RetrieveInfoImpl implements RetrieveInfo {

    /**
     * Method getWeatherString
     * Returns the JSON that is obtained for calling OpenWeatherMap API.
     * @param cityName indicates the name of the city that needs to be searched.
     */
    @Override
    public String getWeatherString(String cityName) {

        WeatherInfoModel weatherInfoModel = new WeatherInfoModel();
        try {
            String api = "166bd569c8bfda7b6c4b06b670cf411c";
            String link = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + api;

            URL site = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) site.openConnection();
            connection.connect();
            String line;

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    /**
     * Method getCities
     * takes the input file and create and observable list of city models.
     *
     */
    @Override
    public ObservableList<CityModel> getCities() {

        ObservableList<CityModel> cityModels = FXCollections.observableArrayList();

        File myObj = new File("src/main/resources/inputFile/input.txt");
        Scanner myReader = null;
        try {
            myReader = new Scanner(myObj);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (true) {
            assert myReader != null;
            if (!myReader.hasNextLine()) break;
            String data = myReader.nextLine();
            String[] result = data.split("\t");

            String cityID = "";
            String cityName = "";
            String cityLat = "";
            String cityLon = "";
            String cityCountryCode = "";

            for (int i=0; i<result.length; i++){
                if(i == 0) {
                    cityID = result[i];
                }
                if(i == 1) {
                    cityName = result[i];
                }
                if(i == 2) {
                    cityLat = result[i];
                }
                if(i == 3) {
                    cityLon = result[i];
                }
                if(i == 4) {
                    cityCountryCode = result[i];
                }
            }
            CityModel cityModel = new CityModel(cityID,cityName,cityLat,cityLon,cityCountryCode);
            cityModels.add(cityModel);
        }
        myReader.close();
        return cityModels;
    }

}
