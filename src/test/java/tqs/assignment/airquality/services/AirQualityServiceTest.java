package tqs.assignment.airquality.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.assignment.airquality.AirQualityResult;
import tqs.assignment.airquality.ResultsCache;

import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AirQualityServiceTest {

    @Mock( lenient = true)
    private ResultsCache cache;

    @InjectMocks
    private AirQualityService service;

    @BeforeEach
    public void setUp() {
        AirQualityResult result1 = new AirQualityResult("Aveiro", "Portugal");
        result1.setCarbon_monoxide("59.12 ppb");

        AirQualityResult result2 = new AirQualityResult("Lisbon", "Portugal");
        result2.setCarbon_monoxide("69.12 ppb");

        TreeSet<AirQualityResult> allResults = new TreeSet<>();
        allResults.add(result1);
        allResults.add(result2);

        Mockito.when(cache.checkCache("Aveiro")).thenReturn(result1);
        Mockito.when(cache.checkCache("wrong")).thenReturn(null);
        Mockito.when(cache.getCache()).thenReturn(allResults);
    }

    @Test
    public void whenValidResult_thenResultShouldBeFound() {
        String city = "Aveiro";
        AirQualityResult result = service.getResultForCity("Aveiro", "Portugal");

        assertThat(result.getCity()).isEqualTo(city);
    }

    @Test
    public void whenInValidCity_thenResultShouldNotBeFound() {
        AirQualityResult result = service.getCityFromCache("wrong");
        assertThat(result).isNull();

    }

    private void verifySearchIsCalledOnce(String city) {
        Mockito.verify(cache, VerificationModeFactory.times(1)).checkCache(city);
        Mockito.reset(cache);
    }
}