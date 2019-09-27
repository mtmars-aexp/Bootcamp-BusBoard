package training.busboard;

import training.busboard.Classes.BusStopClasses.BusStop;
import training.busboard.Classes.BusStopClasses.NearbyBusStops;
import training.busboard.Clients.PostcodeClient;
import training.busboard.Clients.TFLClient;
import training.busboard.Classes.PostcodeClasses.Postcode;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import java.util.*;

public class Main {

    public static void main(String args[]) throws KeyManagementException, NoSuchAlgorithmException {


        //Declaring variables.

        //Clients.
        PostcodeClient postcodeClient = new PostcodeClient();
        TFLClient tflClient = new TFLClient();

        //Lists.
        List<Arrivals> arrivalsList;
        List<BusStop> busStopsList;

        //Comparators.
        Comparator<BusStop> compareByDistance = Comparator.comparing(BusStop::getDistance);

        //User inputs.
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
        outputBusInformation(userInput, busStopsList, tflClient, sslcontext);


    }

    public static void outputBusInformation(String userInput, List<BusStop> busStopsList, TFLClient tflClient, SSLContext sslcontext){
        System.out.println("The 2 nearest bus stops to " + userInput + " are:");
        for(int i = 0; i != 2; i++){


            String naptanId = busStopsList.get(i).getNaptanId();
            String name = busStopsList.get(i).getCommonName();
            int distance = (int)busStopsList.get(i).getDistance();
            String towards = busStopsList.get(i).getTowards();

            System.out.println("Bus stop '" + name + "' towards " + towards + " is " + distance + " meters away.");


            List<Arrivals> arrivalsList = tflClient.getArrivals(sslcontext, naptanId);
            Comparator<Arrivals> compareByTime = Comparator.comparing(Arrivals::getTimeToStation);
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