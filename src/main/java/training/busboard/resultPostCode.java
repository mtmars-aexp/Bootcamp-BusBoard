package training.busboard;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class resultPostCode {

    Location result;

    public Location getResult() {
        return result;
    }
}
