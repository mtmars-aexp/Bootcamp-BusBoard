package training.busboard.Clients;

import org.glassfish.jersey.jackson.JacksonFeature;
import training.busboard.Classes.PostcodeClasses.Postcode;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

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
