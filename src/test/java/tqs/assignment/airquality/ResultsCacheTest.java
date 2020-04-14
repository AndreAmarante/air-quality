package tqs.assignment.airquality;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class ResultsCacheTest {

    // Not testing getInstance() because it's the way to get
    // a singleton object, so it's equivalent to the constructor call

    private static ResultsCache cache;

    @BeforeEach
    void setUp() {
        cache = ResultsCache.getInstance();
    }

    @Test
    void set_then_get_cache() {
        AirQualityResult r = new AirQualityResult("aveiro", "portugal");
        TreeSet<AirQualityResult> tree = new TreeSet<>();
        tree.add(r);
        cache.setCache(tree);

        TreeSet<AirQualityResult> result = cache.getCache();
        assertEquals(tree, result);
    }

    @Test
    void add_and_check_cache() {
        //Clean cache first
        cache.cleanCache();

        AirQualityResult r = new AirQualityResult("aveiro", "portugal");
        cache.addToCache(r);

        AirQualityResult result = cache.checkCache("aveiro");

        assertEquals(r, result);
    }

    @Test
    void check_non_existing_object_in_cache() {
        //Clean cache first
        cache.cleanCache();

        AirQualityResult r = new AirQualityResult("Ílhavo", "portugal");
        AirQualityResult result = cache.checkCache("Ílhavo");

        assertNotEquals(r, result);
        assertNull(result);
    }

    @Test
    void check_clean_cache() {
        //Clean cache first
        cache.cleanCache();

        AirQualityResult r = new AirQualityResult("Ílhavo", "portugal");
        AirQualityResult r1 = new AirQualityResult("Aveiro", "portugal");
        cache.addToCache(r);
        cache.addToCache(r1);

        assertNotEquals(cache.getCache().size(), 0);

        cache.cleanCache();

        assertEquals(cache.getCache().size(), 0);
    }

    @Test
    void add_two_equal_then_check_size_one() {
        //Clean cache first
        cache.cleanCache();

        AirQualityResult r = new AirQualityResult("Ílhavo", "portugal");
        AirQualityResult r1 = new AirQualityResult("Ílhavo", "portugal");
        cache.addToCache(r);
        cache.addToCache(r1);

        assertEquals(cache.getCache().size(),1);
    }

    @Test
    void add_two_then_check_size() {
        //Clean cache first
        cache.cleanCache();

        AirQualityResult r = new AirQualityResult("Ílhavo", "portugal");
        AirQualityResult r1 = new AirQualityResult("Aveiro", "portugal");
        cache.addToCache(r);
        cache.addToCache(r1);

        assertEquals(cache.getCache().size(),2);
    }
}