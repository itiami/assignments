package co.wali;

public class CountryExportModel {

    private String country;
    private String exports;
    private String value;

    public CountryExportModel(String country, String exports, String value){
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

    public String getValue() {
        return value;
    }
}
