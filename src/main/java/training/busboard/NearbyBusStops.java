package training.busboard;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NearbyBusStops {

    ArrayList<BusStop> stopPoints;

    public ArrayList<BusStop> getStopPoints(){
        return stopPoints;
    }
}
