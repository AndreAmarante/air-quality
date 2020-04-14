package tqs.assignment.airquality.controller;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tqs.assignment.airquality.AirQualityResult;
import tqs.assignment.airquality.ResultsCache;
import tqs.assignment.airquality.services.AirQualityService;

import java.sql.Timestamp;


@Controller
public class AirQualityController {

    @Autowired
    private AirQualityService service;


    @GetMapping("/")
    public String homeForm1(Model model) {
        model.addAttribute("airquality", new AirQualityResult("", ""));
        return "home";
    }

    @GetMapping("/error")
    public String errorPage(Model model) {
        return "error";
    }

    @GetMapping("/home")
    public String homeForm(Model model) {
        model.addAttribute("airquality", new AirQualityResult("", ""));
        return "home";
    }

    @PostMapping("/result")
    public String homeSubmit(@ModelAttribute AirQualityResult airquality, BindingResult result, ModelMap model) throws UnirestException {

        String city = airquality.getCity().replace(" ", "%20");
        String country = airquality.getCountry().replace(" ", "%20");

        AirQualityResult airResult = service.getResultForCity(city, country);

        ResultsCache cache = service.getCache();

        model.addAttribute("airquality", airResult);
        model.addAttribute("cache", cache);
        return "result";
    }
}
