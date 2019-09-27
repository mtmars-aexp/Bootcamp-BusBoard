package training.busboard;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AdditionalProperties {

    String key;
    String value;

    public String getKey(){
        return key;
    }

    public String getValue(){
        return value;
    }

}
