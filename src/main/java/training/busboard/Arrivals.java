package training.busboard;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Arrivals {

    int lineId;
    int timeToStation;
    String towards;


    public int getTimeToStation(){
        return timeToStation;
    }

    public int getLineId(){
        return lineId;
    }

    public String getTowards(){
        return towards;
    }
}
