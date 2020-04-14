package tqs.assignment.airquality;

import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Repository
public class ResultsCache {

    private TreeSet<AirQualityResult> cache;
    private int numberOfCallsToCitiesStillInCache;
    private int totalAddedResults;
    private int totalRemovedResuls;

    // static variable single_instance of type Singleton
    private static ResultsCache single_instance = null;

    // variable of type String
    public String s;

    // private constructor restricted to this class itself
    private ResultsCache()
    {
        cache = new TreeSet<>();
        numberOfCallsToCitiesStillInCache = 0;
        totalAddedResults= 0;
        totalRemovedResuls= 0;

        Runnable cleaner = () -> {
            //System.out.println("Cleaner");
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            long t = timestamp.getTime();

            Iterator<AirQualityResult> iter = this.cache.iterator();

            while(iter.hasNext()){
                AirQualityResult result = iter.next();

                long dif = t-result.getTimestamp().getTime(); //ms
                double limit = 1000*60*2; //1000 = 1 second
                System.out.println("Dif: " + dif + ", time: " + result.getTimestamp().getTime() + ", now: " + t);
                if(dif>=limit){
                    System.out.println("Removing");
                    iter.remove();
                    this.totalRemovedResuls+=1;
                }
            }

        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.scheduleAtFixedRate(cleaner, 0, 10, TimeUnit.SECONDS);
    }

    // static method to create instance of Singleton class
    public static ResultsCache getInstance()
    {
        if (single_instance == null)
            single_instance = new ResultsCache();

        return single_instance;
    }

    public TreeSet<AirQualityResult> getCache() {
        return cache;
    }

    public void setCache(TreeSet<AirQualityResult> cache) {
        this.cache = cache;
    }

    public void addToCache(AirQualityResult result){
        this.cache.add(result);
        this.totalAddedResults+=1;
    }

    public AirQualityResult checkCache(String city){

        for (AirQualityResult r: this.cache) {
            if(r.getCity().equalsIgnoreCase(city)){
                this.numberOfCallsToCitiesStillInCache+=1;
                return r;
            }
        }
        return null;
    }

    public void cleanCache(){
        int size = this.cache.size();
        this.cache = new TreeSet<>();
        this.totalRemovedResuls+=size;
    }

    @Override
    public String toString() {
        return "Cache=" + Arrays.toString(cache.toArray());
    }

    public ArrayList<String> getStatistics() {
        ArrayList<String> lista = new ArrayList<>();
        lista.add("Total added results to cache: " + this.totalAddedResults);
        lista.add("Total removed results from cache: " + this.totalRemovedResuls);
        lista.add("Number of calls to data still in cache: " + this.numberOfCallsToCitiesStillInCache);
        return lista;
    }
}
