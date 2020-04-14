package tqs.assignment.airquality.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tqs.assignment.airquality.AirQualityApplication;
import tqs.assignment.airquality.AirQualityResult;
import tqs.assignment.airquality.JsonUtil;
import tqs.assignment.airquality.ResultsCache;
import tqs.assignment.airquality.services.AirQualityService;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AirQualityApplication.class)
@AutoConfigureMockMvc
class APIRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AirQualityService service;

    @Autowired
    private ResultsCache cache;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void whenSearchInvalid_thenGet404() throws Exception {
        AirQualityResult result = new AirQualityResult("wrong", "Portugal");
        given(service.getResultForCity("wrong", "Portugal")).willReturn(null);

        mvc.perform(MockMvcRequestBuilders.post("/api/locals").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(result))).andExpect(status().isNotFound());

        reset(service);
    }

    @Test
    public void givenResult_whenGetResult_thenStatus200() throws Exception {
        AirQualityResult result = new AirQualityResult("aveiro", "Portugal");
        given(service.getResultForCity("aveiro", "Portugal")).willReturn(result);

        mvc.perform(MockMvcRequestBuilders.get("/api/locals/aveiro"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))))
                .andExpect(jsonPath(".city", is("aveiro")));

        reset(service);
    }

    @Test
    public void whenGetStatisticsAtBeggining_returnZeros() throws Exception {
        ArrayList<String> lista = new ArrayList<>();
        lista.add("Total added results to cache: " + 0);
        lista.add("Total removed results from cache: " + 0);
        lista.add("Number of calls to data still in cache: " + 0);
        given(service.getCacheStatistics()).willReturn(lista);

        mvc.perform(MockMvcRequestBuilders.get("/api/cache/statistics").contentType(MediaType.APPLICATION_JSON))
          .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))))
                .andExpect(jsonPath("$[0]", is("Total added results to cache: 0")));

        reset(service);
    }

    @Test
    public void whenGetStatisticsWithOneAdded_returnCorrectStatistics() throws Exception {
        ArrayList<String> lista = new ArrayList<>();
        lista.add("Total added results to cache: " + 1);
        lista.add("Total removed results from cache: " + 0);
        lista.add("Number of calls to data still in cache: " + 0);
        given(service.getCacheStatistics()).willReturn(lista);

        mvc.perform(MockMvcRequestBuilders.get("/api/cache/statistics").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))))
                .andExpect(jsonPath("$[0]", is("Total added results to cache: 1")))
                .andExpect(jsonPath("$[1]", is("Total removed results from cache: 0")));

        reset(service);
    }




}