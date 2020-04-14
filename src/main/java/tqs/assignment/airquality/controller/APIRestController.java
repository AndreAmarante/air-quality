package tqs.assignment.airquality.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tqs.assignment.airquality.AirQualityResult;
import tqs.assignment.airquality.services.AirQualityService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.TreeSet;

@RestController
@RequestMapping("/api")
public class APIRestController {

    @Autowired
    private AirQualityService service;

    @GetMapping("/locals/{city}")
    public AirQualityResult getInfoByCity(@PathVariable String city, HttpServletRequest request) {
        return service.getResultForCity(city, "");
    }

    @GetMapping(path="/locals")
    public TreeSet<AirQualityResult> getAllCars() {
        return service.getAll();
    }

    @GetMapping("/cache/statistics")
    public ArrayList<String> getCacheStatistics() {
        return service.getCacheStatistics();
    }
}
