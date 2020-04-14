package tqs.assignment.airquality.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.assignment.airquality.AirQualityResult;
import tqs.assignment.airquality.ResultsCache;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.TreeSet;

@Service
public class AirQualityService {

    @Autowired
    private ResultsCache cache;

    public TreeSet<AirQualityResult> getAll() {
        ResultsCache cache = ResultsCache.getInstance();
        return cache.getCache();
    }

    public AirQualityResult getResultForCity(String city, String country) {

        ResultsCache cache = ResultsCache.getInstance();

        AirQualityResult check = cache.checkCache(city);
        if(check!=null){
            String msg = check.getCity() + ", " + check.getCountry() + " (got from cache!)";
            check.setMsg(msg);
            return check;
        }else{
            AirQualityResult airquality = new AirQualityResult("","");
            String token = "f7eedd8d2dc192";
            String getCoordinatesURL = "https://eu1.locationiq.com/v1/search.php?key=" + token +"&q=" + city + "&format=json";

            try {
                Unirest.setTimeouts(0, 0);
                HttpResponse<String> response = Unirest.get(getCoordinatesURL).header("x-access-token", "eyJhbGciOiJIUzI1NiJ9.ZWQ3OGQ1YjAtNzdmYS0xMWVhLWE3NGQtOWQ0MjQ3NmJlNDc1.o7JxO3XFgGpng_cFOqEnQl0DoBTYCJV9z6WqZg62VHE")
                        .asString();

                String json = response.getBody();
                JSONArray array = new JSONArray(json);
                JSONObject json1 = array.getJSONObject(0);
                System.out.println("Response -> " + response.getBody());
                System.out.println("Json -> " + json1);

                String lat = json1.get("lat").toString();
                String lon = json1.get("lon").toString();

                String breezokey = "b1758dd830434b929d0656846ec135aa";
                String url = "https://api.breezometer.com/air-quality/v2/current-conditions?lat=" + lat + "&lon=" + lon + "&key=" + breezokey + "&features=breezometer_aqi,pollutants_concentrations";
                try {
                    Unirest.setTimeouts(0, 0);
                    HttpResponse<String> responseFinal = Unirest.get(url).asString();

                    System.out.println("responseFinal -> " + responseFinal.getBody());

                    JSONObject jsonFinal = new JSONObject(responseFinal.getBody());
                    System.out.println("jsonFinal -> " + jsonFinal);
                    JSONObject jsonFinal1 = jsonFinal.getJSONObject("data");
                    System.out.println("jsonFinal1 -> " + jsonFinal1);
                    JSONObject pollutants = jsonFinal1.getJSONObject("pollutants");
                    System.out.println("pollutants -> " + pollutants);
                    JSONObject no2_values = pollutants.getJSONObject("no2").getJSONObject("concentration");
                    System.out.println("no2_values -> " + no2_values);
                    String no2 = no2_values.get("value").toString() + " " + no2_values.get("units").toString();
                    airquality.setNitrogen_dioxide(no2);
                    JSONObject o3_values = pollutants.getJSONObject("o3").getJSONObject("concentration");
                    String o3 = o3_values.get("value").toString() + " " + o3_values.get("units").toString();
                    airquality.setOzone(o3);
                    JSONObject so2_values = pollutants.getJSONObject("so2").getJSONObject("concentration");
                    String so2 = so2_values.get("value").toString() + " " + so2_values.get("units").toString();
                    airquality.setSulfur_dioxide(so2);
                    JSONObject co_values = pollutants.getJSONObject("co").getJSONObject("concentration");
                    String co = co_values.get("value").toString() + " " + co_values.get("units").toString();
                    airquality.setCarbon_monoxide(co);
                    JSONObject pm10_values = pollutants.getJSONObject("pm10").getJSONObject("concentration");
                    String pm10 = pm10_values.get("value").toString() + " " + pm10_values.get("units").toString();
                    airquality.setInhalable_particulate_matter(pm10);

                    airquality.setCountry(country);
                    airquality.setCity(city);

                    try{
                        //Add to cache
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        airquality.setTimestamp(timestamp);
                        cache.addToCache(airquality);
                    }catch(Exception e){
                        System.out.println("Erro -> " + e.getMessage());
                    }

                }catch (Exception e){
                    airquality.setCity("-");
                    airquality.setCountry("-");
                }


            }catch(Exception e){
                //System.out.println(e.getMessage());
                airquality.setCity("-");
                airquality.setCountry("-");
            }
            return airquality;
        }

    }

    public AirQualityResult getCityFromCache(String city){
        ResultsCache cache = ResultsCache.getInstance();
        AirQualityResult result = cache.checkCache(city);
        return result;
    }

    public ResultsCache getCache() {
        return ResultsCache.getInstance();
    }

    public ArrayList<String> getCacheStatistics() {
        ResultsCache cache = ResultsCache.getInstance();
        ArrayList<String> statistics = cache.getStatistics();
        return statistics;
    }
}
