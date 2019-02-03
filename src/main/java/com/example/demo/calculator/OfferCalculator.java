package com.example.demo.calculator;

/**
 * OfferCalculator Technical Test
 * Last update: 02.02.2019
 *
 * @author Nikolett Bakos
 */

import com.example.demo.entities.OfferEntity;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class OfferCalculator {
    private static Map<String, Integer> noOfPassengersByCar = new HashMap<>();
    private static PrintWriter debug = null;
    private static int noOfRequiredPassengers;

    /**
     * Add the current date and time to the debug file
     */
    private static void getCurrentTimeUsingCalendar() {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        debug.println("*****Date/time of run: " + timeStamp + "******");
    }

    /**
     * Function that handles with response of suppliers. If there is already an offer for a car type it compares it
     * and keeps the cheaper otherwise adds it to the HashMap
     * @param response Response that got from a supplier
     * @param supplier Name of the current supplier
     * @param offers HashMap including final offers
     */
    public static void getCarsAndPrices(String response, String supplier,
                                        Map<String, OfferEntity> offers) {
        // Get the useful information from the response
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

            // Create a new instance of OfferEntity
            OfferEntity temp = new OfferEntity(car, supplier, price);

            // If there is already an offer for a car type it compares it and keeps the cheaper otherwise adds
            // it to the HashMap
            if(offers.containsKey(car)) {
                debug.println("(ParameterAndResponseHandler) includes key " + car);
                if (temp.getPrice() < offers.get(car).getPrice()) {
                    debug.println("SWAP!!!!! OfferEntity: " + offers.get(car).toString() + " to offer: " + temp.toString());
                    offers.replace(car, temp);
                }
            }
            else if (noOfPassengersByCar.get(car) >= noOfRequiredPassengers) {
                offers.put(car, temp);
                debug.println("(ParameterAndResponseHandler) add key " + car + " and offer " + temp.toString());
            }
        }
        debug.println();
    }

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

    /**
     * Implementation of bubble sort for array type OfferEntity
     *
     * @param needToSort OfferEntity type array that need to be sorted
     */
    private static void bubbleSort (OfferEntity[] needToSort) {
        int unsortedLength = needToSort.length;
        // If no change is made on a pass the loop can stop
        boolean isChanged;
        do {
            isChanged = false;
            for (int index = 0; index < unsortedLength - 1; index++) {
                if (needToSort[index].compareTo(needToSort[index + 1]) < 0) {
                    OfferEntity temp = needToSort[index];
                    needToSort[index] = needToSort[index + 1];
                    needToSort[index + 1] = temp;
                    isChanged = true;
                }
            }
            unsortedLength--;
        } while (isChanged);
    }

    /**
     * Get the offers from a supplier and store the cheapest offer for each car type in a HashMap
     * @param supplier Name of the supplier
     * @param param Latitude and longitude of pick up and drop off locations
     * @param offers HashMap of offers got so far (may empty)
     */
    private static void getTheOffersFromSuppliers (String supplier, Map<String, String> param,
                                                   Map<String, OfferEntity> offers) {
        // Get the required URL to the request
        String supplierURL = getURL(supplier, param);

        debug.println("(OfferCalculator) The URL for " + supplier + " is: " + supplierURL);

        try {
            URL url = new URL(supplierURL);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set timeouts
            connection.setConnectTimeout(2000);

            // Set the request
            connection.setRequestMethod("GET");

            connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");

            int responseCode = connection.getResponseCode();
            debug.println("(OfferCalculator) The response code is: " + responseCode);

            // If the response code was OK then get the response
            if (responseCode == connection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                debug.println("(OfferCalculator) Response: " + response.toString());

                // Call function that handle with the response
                getCarsAndPrices(response.toString(), supplier, offers);

                // Debug
                debug.println("(OfferCalculator) Before sorting...");
                for (Map.Entry<String, OfferEntity> offer : offers.entrySet())
                    debug.println(offer.getValue().toString());

            }

            connection.disconnect();
        }
        catch (IOException e){
            System.err.println(e);
        }

    }

    /**
     * Main method of the calculator invoked by the offer controller
     * @param pickLat Pick up latitude
     * @param pickLong Pick up longitude
     * @param dropLat Drop off latitude
     * @param dropLong Drop off longitude
     * @param noOfPassengers Number of required passengers
     * @return Offer array contains the offers based on the request
     */
    public OfferEntity[] getOffers(double pickLat, double pickLong, double dropLat,
                             double dropLong, int noOfPassengers) {

        // Add the car types and the maximum number of passengers to HashMap
        noOfPassengersByCar.put("STANDARD", 4);
        noOfPassengersByCar.put("EXECUTIVE", 4);
        noOfPassengersByCar.put("LUXURY", 4);
        noOfPassengersByCar.put("PEOPLE_CARRIER", 6);
        noOfPassengersByCar.put("LUXURY_PEOPLE_CARRIER", 6);
        noOfPassengersByCar.put("MINIBUS", 16);

        // Array to hold suppliers
        String[] suppliers;

        noOfRequiredPassengers = noOfPassengers;

        // Create a debug file used across the calculation
        try {
            debug = new PrintWriter(new BufferedWriter(new FileWriter("debugAPI.txt", true)));
        }
        catch (IOException e){
            System.err.println(e);
        }

        // Set timestamp for the debug file
        if(debug != null)
            getCurrentTimeUsingCalendar();

        // Set parameters from commandline argument
        Map<String, String> param = new HashMap<>();
        String pickUp = pickLat + "," + pickLong;
        String dropOff = dropLat + "," +  dropLong;
        param.put("pickup", pickUp);
        param.put("dropoff", dropOff);

        // Array to hold the suppliers
        suppliers = new String[3];
        suppliers[0] = "dave";
        suppliers[1] = "jeff";
        suppliers[2] = "eric";

        // Create HashMap to hold the offers
        Map<String, OfferEntity> offers = new HashMap<>();

        // Get the offers from each supplier and keep the cheapest for each car type
        if(suppliers != null) {
            for (String supplier : suppliers)
                getTheOffersFromSuppliers(supplier, param, offers);
        }
        else {
            System.out.println("Something went wrong");
            debug.println("Something went wrong when creating supplier's array");
            System.exit(0);
        }

        // Convert HashMap to Array
        OfferEntity[] offersArray = offers.values().toArray(new OfferEntity[0]);

        //Sort the list of offers in descending order based on the price
        bubbleSort(offersArray);

        debug.println("**********Finished calculation*********");
        debug.println();
        debug.println();

        if(debug != null) {
            debug.close();
            if (debug.checkError()) {
                System.err.println("Something went wrong with the debug file");
            }
        }
        return offersArray;
    }
}
