package training.busboard.Classes.PostcodeClasses;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Postcode {

    public LonAndLat result;

    public double getLongitude(){
        return result.longitude;
    }

    public double getLatitude(){
        return result.latitude;
    }

    public LonAndLat getResult(){
        return result;
    }
}
