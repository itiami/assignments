package co.wali;

class WeatherStats {
    private final double maxTemp;
    private final String maxTempDate;
    private final double lowestTemp;
    private final String lowestTempDate;
    private final double lowestHumidity;
    private final String lowestHumidityDate;
    private final double heightHumidity;
    private final String heightHumidityDate;
    private final double avgTemp;
    private final int totalRecords;

    public WeatherStats(double maxTemp, String maxTempDate, double lowestTemp, String lowestTempDate,
                        double lowestHumidity,double heightHumidity, String lowestHumidityDate, String heightHumidityDate, double avgTemp, int totalRecords) {
        this.maxTemp = maxTemp;
        this.maxTempDate = maxTempDate;
        this.lowestTemp = lowestTemp;
        this.lowestTempDate = lowestTempDate;
        this.lowestHumidity = lowestHumidity;
        this.heightHumidity = heightHumidity;
        this.lowestHumidityDate = lowestHumidityDate;
        this.heightHumidityDate = heightHumidityDate;
        this.avgTemp = avgTemp;
        this.totalRecords = totalRecords;
    }

    public double getMaxTemp() { return maxTemp; }
    public String getMaxTempDate() { return maxTempDate; }
    public double getLowestTemp() { return lowestTemp; }
    public String getLowestTempDate() { return lowestTempDate; }
    public double getLowestHumidity() { return lowestHumidity; }
    public double getHeightHumidity() { return heightHumidity; }
    public String getLowestHumidityDate() { return lowestHumidityDate; }
    public String getHeightHumidityDate() { return heightHumidityDate; }
    public double getAvgTemp() { return avgTemp; }
    public int getTotalRecords() { return totalRecords; }
}