package training.busboard;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StopInformation {

    String naptanId;
    String commonName;
    int index;

    public String getNaptanId() {
        return naptanId;
    }
    public String getCommonName() {
        return commonName;
    }
    public int getIndex() {
        return  index;
    }

    public String returnSouthOrNorth() {
        if(naptanId.charAt(naptanId.length() -1) == 'S' ) {
            return "Heading South";
        }
        if(naptanId.charAt(naptanId.length() -1) == 'W' ) {
            return "Heading West";
        }
        if(naptanId.charAt(naptanId.length() -1) == 'N' ) {
            return "Heading North";
        }
        return "Heading East";
    }
}
