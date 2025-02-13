package co.wali;

public class CountryExportModel {

    private String country;
    private String exports;
    private long value;

    public CountryExportModel(){};

    public CountryExportModel(String country, String exports, long value){
        this.country = country;
        this.exports = exports;
        this.value = value;
    }

    public String getCountry() {
        return country;
    }

    public String getExports() {
        return exports;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toString(){
        return "Country: " + country + ", " + "Exports: " + exports + ", " + "Value: " + value;
    }
}
