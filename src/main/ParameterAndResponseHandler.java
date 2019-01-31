/**
 * The class is used to handle with the parameters of a request and extract information from response
 *
 * Last updated: 31.01.2019
 * @author Nikolett Bakos
 */
package main;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ParameterAndResponseHandler {

    /**
     * Method to build URL with the given parameters
     *
     * @param supplier The required basic URL
     * @param params Latitude and longitude of pick up and drop off locations
     * @return The URL used to requests
     */
    public static String getURL(String supplier, Map<String, String> params) {
        String URL = "https://techtest.rideways.com/";
        URL += supplier + "?pickup=" + params.get("pickup") + "&dropoff=" + params.get("dropoff");
        return URL;
    }

    public static Map<String, Offer> getCarsAndPrices(String response, PrintWriter debug, String supplier,
                                                      Map<String, Offer> offers) {
        // Get the number of offers from response and create an array to hold them
        int noOfOffers = response.split("car_type", -1).length-1;
        debug.println("(ParameterAndResponseHandler) The number of offers: " + noOfOffers);

        int firstIndex, lastIndex = 0;

        while (response.indexOf("car_type", lastIndex) > 0) {
            // Find the car type
            firstIndex = response.indexOf("car_type", lastIndex);
            lastIndex = response.indexOf("price", lastIndex + 1);
            String car = response.substring(firstIndex + 11, lastIndex -3);

            // Find the price corresponding to car type
            firstIndex = response.indexOf("price", firstIndex);
            lastIndex = response.indexOf('}', firstIndex + 1);
            int price = Integer.parseInt(response.substring(firstIndex + 7, lastIndex));

            // Create a new instance of Offer
            Offer temp = new Offer (car, supplier, price);

            if(offers.containsKey(car)) {
                debug.println("(ParameterAndResponseHandler) includes key " + car);
                if (temp.getPrice() < offers.get(car).getPrice())
                    offers.replace(car, temp);
            }
            else {
                offers.put(car, temp);
                debug.println("(ParameterAndResponseHandler) add key " + car + " and offer " + temp.toString());
            }
        }
        return offers;
    }
}
