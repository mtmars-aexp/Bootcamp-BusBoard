package training.busboard;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BusStop {

    public String commonName;
    public double distance;
    public String naptanId;
    public List<AdditionalProperties> additionalProperties;

    public String getCommonName(){
        return commonName;
    }

    public double getDistance(){
        return distance;
    }

    public String getNaptanId() { return naptanId; }

    public List<AdditionalProperties> getAdditionalProperties(){ return additionalProperties; }

    public String getTowards(){
        for(int i = 0; i != additionalProperties.size(); i++){
            if (additionalProperties.get(i).getKey().equals("Towards")){
                return additionalProperties.get(i).getValue();
            }
        }
        return "Not found.";
    }

}
