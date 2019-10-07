package training.busboard;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BusInformation {

    public int lineId;
    public int timeToStation;
    public String destinationName;

    //    int lineId;
//    int timeToStationSeconds;
//    String stationName;
//    String vehicleId;
//    String destinationName;
//
    public BusInformation(int lineId, int timeToStation, String destinationName) {

        this.lineId = lineId;
        this.timeToStation = timeToStation;
        this.destinationName = destinationName;

    }

    public int getLineId() {
        return lineId;
    }

    public int getTimeToStation() {
        return timeToStation;
    }


    //    public void setName(String name) {
//        this.name = name;
//    }
//
    public String getDestinationName() {
        return destinationName;
    }
}


