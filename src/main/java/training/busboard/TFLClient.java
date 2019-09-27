package training.busboard;

import org.glassfish.jersey.jackson.JacksonFeature;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import java.util.List;

public class TFLClient {


    public NearbyBusStops getNearbyBusStops(SSLContext sslcontext, Postcode postcode){

        double latitude = postcode.getLatitude();
        double longitude = postcode.getLongitude();

        Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).sslContext(sslcontext).hostnameVerifier((s1, s2) -> true).build();
        NearbyBusStops nearbyBusStops = client.target("https://api.tfl.gov.uk/StopPoint?StopTypes=NaptanPublicBusCoachTram&lat="+ latitude +"&lon="+ longitude + "&radius=1000")
                .request("text/json")
                .get(NearbyBusStops.class);

        return nearbyBusStops;
    }

    public List<Arrivals> getArrivals(SSLContext sslcontext, String stopID){

        Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).sslContext(sslcontext).hostnameVerifier((s1, s2) -> true).build();
        List<Arrivals> arrivalsList = client.target("https://api.tfl.gov.uk/StopPoint/"+stopID+"/Arrivals/")
                .request("text/json")
                .get(new GenericType<List<Arrivals>>() {});
        return arrivalsList;
    }
}
