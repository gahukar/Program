package ProgramUsingRestAPI;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
public class WeatherApp 
{
	    private static final String API_URL = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";

	    private static JSONObject getWeatherData() throws IOException {
	        URL url = new URL(API_URL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");

	        if (conn.getResponseCode() == 200) {
	            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            StringBuilder response = new StringBuilder();
	            String line;
	            while ((line = reader.readLine()) != null) {
	                response.append(line);
	            }
	            reader.close();
	            return new JSONObject(response.toString());
	        } else {
	            System.out.println("Failed to fetch weather data. Please try again later.");
	            return null;
	        }
	    }

	    private static double getWeatherByDate(JSONObject weatherData, String date) {
	        JSONArray list = weatherData.getJSONArray("list");
	        for (int i = 0; i < list.length(); i++) {
	            JSONObject entry = list.getJSONObject(i);
	            if (entry.getString("dt_txt").contains(date)) {
	                return entry.getJSONObject("main").getDouble("temp");
	            }
	        }
	        return Double.NaN;
	    }

	    private static double getWindSpeedByDate(JSONObject weatherData, String date) {
	        JSONArray list = weatherData.getJSONArray("list");
	        for (int i = 0; i < list.length(); i++) {
	            JSONObject entry = list.getJSONObject(i);
	            if (entry.getString("dt_txt").contains(date)) {
	                return entry.getJSONObject("wind").getDouble("speed");
	            }
	        }
	        return Double.NaN;
	    }

	    private static double getPressureByDate(JSONObject weatherData, String date) {
	        JSONArray list = weatherData.getJSONArray("list");
	        for (int i = 0; i < list.length(); i++) {
	            JSONObject entry = list.getJSONObject(i);
	            if (entry.getString("dt_txt").contains(date)) {
	                return entry.getJSONObject("main").getDouble("pressure");
	            }
	        }
	        return Double.NaN;
	    }

	    public static void main(String[] args) {
	        JSONObject weatherData;
	        try {
	            weatherData = getWeatherData();
	            if (weatherData == null) {
	                return;
	            }

	            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	            while (true) {
	                System.out.println("\nMenu:");
	                System.out.println("1. Get weather");
	                System.out.println("2. Get Wind Speed");
	                System.out.println("3. Get Pressure");
	                System.out.println("0. Exit");

	                try {
	                    System.out.print("Enter your choice: ");
	                    int choice = Integer.parseInt(reader.readLine());

	                    switch (choice) {
	                        case 1:
	                            System.out.print("Enter the date (YYYY-MM-DD HH:MM:SS): ");
	                            String dateWeather = reader.readLine();
	                            double temperature = getWeatherByDate(weatherData, dateWeather);
	                            if (!Double.isNaN(temperature)) {
	                                System.out.println("Temperature on " + dateWeather + ": " + temperature + " °C");
	                            } else {
	                                System.out.println("Weather data not available for the specified date.");
	                            }
	                            break;
	                        case 2:
	                            System.out.print("Enter the date (YYYY-MM-DD HH:MM:SS): ");
	                            String dateWindSpeed = reader.readLine();
	                            double windSpeed = getWindSpeedByDate(weatherData, dateWindSpeed);
	                            if (!Double.isNaN(windSpeed)) {
	                                System.out.println("Wind Speed on " + dateWindSpeed + ": " + windSpeed + " m/s");
	                            } else {
	                                System.out.println("Weather data not available for the specified date.");
	                            }
	                            break;
	                        case 3:
	                            System.out.print("Enter the date (YYYY-MM-DD HH:MM:SS): ");
	                            String datePressure = reader.readLine();
	                            double pressure = getPressureByDate(weatherData, datePressure);
	                            if (!Double.isNaN(pressure)) {
	                                System.out.println("Pressure on " + datePressure + ": " + pressure + " hPa");
	                            } else {
	                                System.out.println("Weather data not available for the specified date.");
	                            }
	                            break;
	                        case 0:
	                            System.out.println("Exiting the program. Goodbye!");
	                            return;
	                        default:
	                            System.out.println("Invalid choice. Please try again.");
	                            break;
	                    }
	                } catch (NumberFormatException | IOException e) {
	                    System.out.println("Invalid input. Please try again.");
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Error fetching weather data. Please try again later.");
	        }
	    }
	}


