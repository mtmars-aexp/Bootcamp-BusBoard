package training.busboard.web;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import training.busboard.BusInformation;
import training.busboard.resultPostCode;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
@EnableAutoConfiguration
public class Website {

    @RequestMapping("/")
    ModelAndView home() {
        return new ModelAndView("index");
    }

    @RequestMapping("/busInfo")
    ModelAndView busInfo(@RequestParam("postcode") String postcode) {

        List<BusInformation> busInfo = new ArrayList<BusInformation>();
        busInfo.add(new BusInformation(216, 416, "Hell"));
        busInfo.add(new BusInformation(666, 999999999, "Sandris' house :)"));
        busInfo.add(new BusInformation(420,7,"Enlightenment"));

        return new ModelAndView("info", "busInfo", new BusInfo(postcode, busInfo));

    }

    public static void main(String[] args) throws Exception {

        System.setProperty("http.proxyHost", "localhost");
        System.setProperty("http.proxyPort", "9090");
        System.setProperty("https.proxyHost", "localhost");
        System.setProperty("https.proxyPort", "9090");

        SpringApplication.run(Website.class, args);



    }

}