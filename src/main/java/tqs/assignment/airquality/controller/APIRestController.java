package tqs.assignment.airquality.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
