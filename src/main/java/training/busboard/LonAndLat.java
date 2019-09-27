package training.busboard;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LonAndLat {

    double longitude;
    double latitude;

    public double getLongitude(){
        return longitude;
    }

    public double getLatitude(){
        return latitude;
    }

}
