package training.busboard;

import org.glassfish.jersey.jackson.JacksonFeature;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) throws KeyManagementException, NoSuchAlgorithmException {

        System.setProperty("http.proxyHost", "localhost");
        System.setProperty("http.proxyPort", "9090");
        System.setProperty("https.proxyHost", "localhost");
        System.setProperty("https.proxyPort", "9090");

        SSLContext sslcontext = SSLContext.getInstance("TLS");

        sslcontext.init(null, new TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) {
            }

            public void checkServerTrusted(X509Certificate[] arg0, String arg1) {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }}, new java.security.SecureRandom());

        Scanner myObj1 = new Scanner(System.in);
        System.out.println("Enter your post code: ");
        String postCode = myObj1.nextLine();

        Client client2 = ClientBuilder.newBuilder().register(JacksonFeature.class).sslContext(sslcontext).hostnameVerifier((s1, s2) -> true).build();
        resultPostCode locationGetter = client2.target("https://api.postcodes.io/postcodes/" + postCode)
                .request("text/json")
                .get(resultPostCode.class);

        double latitude = locationGetter.result.getLatitude();
        double longitude = locationGetter.result.getLongitude();

        Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).sslContext(sslcontext).hostnameVerifier((s1, s2) -> true).build();
        StopPointsWrapper stops = client.target("https://api.tfl.gov.uk/StopPoint?stopTypes=NaptanPublicBusCoachTram&radius=500&modes=bus%2Ctram&lat=" + latitude + "&lon=" + longitude)
                .request("text/json")
                .get(StopPointsWrapper.class);

        for (int i = 1; i <=  stops.stopPoints.size(); i++) {
            stops.stopPoints.get(i-1).index = i;
            System.out.println(i + " " + stops.stopPoints.get(i-1).commonName + " " + stops.stopPoints.get(i-1).returnSouthOrNorth());
        }

        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter the number for the bus stop you want to see times for: ");
        int number = myObj.nextInt();
        String stopId = stops.stopPoints.get(number-1).naptanId;

        List<BusInformation> buses = client.target("https://api.tfl.gov.uk/StopPoint/" + stopId + "/Arrivals")
                .request("text/json")
                .get(new GenericType<List<BusInformation>>() {});



        Comparator<BusInformation> compareByTimeToStation = Comparator.comparing(BusInformation::getTimeToStation);

        buses.sort(compareByTimeToStation);

        for (int i = 0; i < buses.size(); i++) {

            String timeDue;

            if (buses.get(i).getTimeToStation() / 60 == 0) {
                timeDue = "Due";
            } else if (buses.get(i).getTimeToStation() / 60 == 1) {
                timeDue = "arriving in 1 minute.";
            } else {
                timeDue = "arriving in " + Integer.toString(buses.get(i).getTimeToStation() / 60) + " minutes.";
            }
            System.out.println("Bus nr " + buses.get(i).getLineId() + " is going to " + buses.get(i).getDestinationName() + " and is " + timeDue);
        }


        Scanner myObj3 = new Scanner(System.in);
        System.out.println("Enter bus ID number that you want to see arrival times for: ");
        int busId = new Integer(myObj3.nextLine());


        for (int i = 0; i < buses.size(); i++) {

            String timeDue;

            if (buses.get(i).getTimeToStation() / 60 == 0) {
                timeDue = "Due";
            } else if (buses.get(i).getTimeToStation() / 60 == 1) {
                timeDue = "arriving in 1 minute.";
            } else {
                timeDue = "arriving in " + Integer.toString(buses.get(i).getTimeToStation() / 60) + " minutes.";
            }

            if (buses.get(i).lineId == busId) {
                System.out.println("Bus nr " + buses.get(i).getLineId() + " is going to " + buses.get(i).getDestinationName() + " and is " + timeDue);
            }

        }

        System.out.println();

    }
}
