package training.busboard;

import org.glassfish.jersey.jackson.JacksonFeature;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.*;

//Tasks:
//Figure out what the fuck all this code means. Comment it.
//


public class Main {

    public static void main(String args[]) throws KeyManagementException, NoSuchAlgorithmException {


        //Declaring variables.

        //Clients.
        PostcodeClient postcodeClient = new PostcodeClient();
        TFLClient tflClient = new TFLClient();

        //List.
        List<Arrivals> arrivalsList;
        List<BusStop> busStopsList;
        Comparator<BusStop> compareByDistance = Comparator.comparing(BusStop::getDistance);
        Comparator<Arrivals> compareByTime = Comparator.comparing(Arrivals::getTimeToStation);
        String userInput = "NW51TL";

        //Start proxy settings.
        System.setProperty("http.proxyHost", "localhost");
        System.setProperty("http.proxyPort", "9090");
        System.setProperty("https.proxyHost", "localhost");
        System.setProperty("https.proxyPort", "9090");

        SSLContext sslcontext = SSLContext.getInstance("TLS");

        sslcontext.init(null, new TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) {}
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) {}
            public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
        }}, new java.security.SecureRandom());
        //End proxy settings.


        //Grabbing info by APIs (Postcodes API fed into TFL API).
        Postcode postcode = postcodeClient.getPostcode(sslcontext, "NW51TL");
        NearbyBusStops nearbyBusStops = tflClient.getNearbyBusStops(sslcontext,postcode);
        busStopsList = nearbyBusStops.getStopPoints();
        busStopsList.sort(compareByDistance);

        //Outputting information.
        System.out.println("The 2 nearest bus stops to " + userInput + " are:");
        for(int i = 0; i != 10; i++){


            String naptanId = busStopsList.get(i).getNaptanId();
            String name = busStopsList.get(i).getCommonName();
            int distance = (int)busStopsList.get(i).getDistance();
            String towards = busStopsList.get(i).getTowards();

            System.out.println("Bus stop '" + name + "' towards " + towards + " is " + distance + " meters away.");

            arrivalsList = tflClient.getArrivals(sslcontext, naptanId);
            arrivalsList.sort(compareByTime);

            for(int n = 0; n != arrivalsList.size(); n++){

                int id = arrivalsList.get(n).getLineId();
                int timeToStation = arrivalsList.get(n).getTimeToStation();
                //String towards = arrivalsList.get(n).getTowards();

                System.out.println("Bus " + id + " will arrive at this stop in " + timeToStation/60 + " minutes.");
            }
            System.out.println(" ");
        }

    }
}