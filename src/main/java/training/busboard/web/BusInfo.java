package training.busboard.web;
import training.busboard.BusInformation;
import java.util.List;

public class BusInfo {
    private final String postcode;
    List<BusInformation> busInformationList;

    public BusInfo(String postcode, List<BusInformation> busInformationList) {
        this.postcode = postcode;
        this.busInformationList = busInformationList;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getDestination(){
        return busInformationList.get(0).getDestinationName();
    }

    public List<BusInformation> getBusInformationList(){
        return busInformationList;
    }
}
