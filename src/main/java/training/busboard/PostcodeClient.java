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
import java.util.Comparator;
import java.util.List;

public class PostcodeClient {

    public Postcode getPostcode(SSLContext sslcontext, String userInput){

        Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).sslContext(sslcontext).hostnameVerifier((s1, s2) -> true).build();

        Postcode postcode = client.target("http://api.postcodes.io/postcodes/" + userInput)
                .request("text/json")
                .get(Postcode.class);

        //System.out.println(postcode.getLongitude() + " " + postcode.getLatitude());

        return postcode;
    }


}
