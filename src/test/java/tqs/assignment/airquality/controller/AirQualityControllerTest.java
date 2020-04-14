package tqs.assignment.airquality.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.assignment.airquality.AirQualityResult;
import tqs.assignment.airquality.JsonUtil;
import tqs.assignment.airquality.ResultsCache;
import tqs.assignment.airquality.services.AirQualityService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AirQualityController.class)
class AirQualityControllerTest {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AirQualityService service;

    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    public void whenSearchInvalid_thenGet404() throws Exception {
        AirQualityResult result = new AirQualityResult("wrong", "Portugal");
        given(service.getResultForCity("wrong", "Portugal")).willReturn(null);

        mvc.perform(post("/api/locals").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(result))).andExpect(status().isNotFound());

        reset(service);
    }

    @Test
    public void whenPostSearch_thenGetResult() throws Exception {
        AirQualityResult result = new AirQualityResult("aveiro", "portugal");
        given(service.getResultForCity("aveiro", "portugal")).willReturn(result);

        String json = mapper.writeValueAsString(result);

        mvc.perform(post("/result").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated()).andExpect(jsonPath("$.city", is("aveiro")));
        verify(service, VerificationModeFactory.times(1)).getResultForCity(Mockito.any(), Mockito.any());
        reset(service);
    }


}