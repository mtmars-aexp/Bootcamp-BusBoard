package training.busboard;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Arrivals {

    int id;
    String vehicleId;
    

    public int getid(){
        return id;
    }

}
