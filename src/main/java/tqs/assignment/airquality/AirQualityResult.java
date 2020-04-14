package tqs.assignment.airquality;

import java.sql.Timestamp;

public class AirQualityResult implements Comparable<AirQualityResult>{

    private String city;
    private String country;
    private String carbon_monoxide;
    private String nitrogen_dioxide;
    private String ozone;
    private String inhalable_particulate_matter;
    private String sulfur_dioxide;
    private String msg;
    private Timestamp timestamp;

    public AirQualityResult(String city, String country) {
        this.city = city;
        this.country = country;
        this.msg = "";
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;

        if(city.equals("-")){
            this.msg = "Invalid inputs inserted";
        }else{
            this.msg = this.city + ", " + this.country;
        }

    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCarbon_monoxide() {
        return carbon_monoxide;
    }

    public void setCarbon_monoxide(String carbon_monoxide) {
        this.carbon_monoxide = carbon_monoxide;
    }

    public String getNitrogen_dioxide() {
        return nitrogen_dioxide;
    }

    public void setNitrogen_dioxide(String nitrogen_dioxide) {
        this.nitrogen_dioxide = nitrogen_dioxide;
    }

    public String getOzone() {
        return ozone;
    }

    public void setOzone(String ozone) {
        this.ozone = ozone;
    }

    public String getInhalable_particulate_matter() {
        return inhalable_particulate_matter;
    }

    public void setInhalable_particulate_matter(String inhalable_particulate_matter) {
        this.inhalable_particulate_matter = inhalable_particulate_matter;
    }

    public String getSulfur_dioxide() {
        return sulfur_dioxide;
    }

    public void setSulfur_dioxide(String sulfur_dioxide) {
        this.sulfur_dioxide = sulfur_dioxide;
    }

    @Override
    public int compareTo(AirQualityResult result) {
        String city1 = result.getCity();
        return this.city.compareTo(city1);
    }

    @Override
    public String toString() {
        return "AirQualityResult{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", carbon_monoxide='" + carbon_monoxide + '\'' +
                ", nitrogen_dioxide='" + nitrogen_dioxide + '\'' +
                ", ozone='" + ozone + '\'' +
                ", inhalable_particulate_matter='" + inhalable_particulate_matter + '\'' +
                ", sulfur_dioxide='" + sulfur_dioxide + '\'' +
                ", msg='" + msg + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
