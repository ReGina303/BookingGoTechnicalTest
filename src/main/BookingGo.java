/**
 * BookingGo Technical Test
 * Last update: 02.02.2019
 *
 * @author Nikolett Bakos
 */
package main;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BookingGo {
    /**
     * Add the current date and time to the debug file
     *
     * @param debug The debug file
     */
    private static void getCurrentTimeUsingCalendar(PrintWriter debug) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        debug.println("*****Date/time of run: " + timeStamp + "******");
    }

    /**
     * Implementation of bubble sort for array type Offer
     *
     * @param needToSort Offer type array that need to be sorted
     */
    private static void bubbleSort (Offer[] needToSort) {
        int unsortedLength = needToSort.length;
        // If no change is made on a pass the loop can stop
        boolean isChanged;
        do {
            isChanged = false;
            for (int index = 0; index < unsortedLength - 1; index++) {
                if (needToSort[index].compareTo(needToSort[index + 1]) < 0) {
                    Offer temp = needToSort[index];
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
     * @param debug Debug file
     * @param offers HashMap of offers got so far (may empty)
     */
    private static void getTheOffersFromSuppliers (String supplier, Map<String, String> param,
                                                  PrintWriter debug, Map<String, Offer> offers) {
        // Get the required URL to the request
        String supplierURL = ParameterAndResponseHandler.getURL(supplier, param);

        debug.println("(BookingGo) The URL for " + supplier + " is: " + supplierURL);

        try {
            URL url = new URL(supplierURL);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set timeouts
            connection.setConnectTimeout(2000);

            // Set the request
            connection.setRequestMethod("GET");

            connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");

            int responseCode = connection.getResponseCode();
            debug.println("(BookingGo) The response code is: " + responseCode);

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

                debug.println("(BookingGo) Response: " + response.toString());

                // Call function that handle with the response
                ParameterAndResponseHandler.getCarsAndPrices(response.toString(), debug, supplier, offers);

                // Debug
                debug.println("(BookingGo) Before sorting...");
                for (Map.Entry<String, Offer> offer : offers.entrySet())
                    debug.println(offer.getValue().toString());

            }

            connection.disconnect();
        }
        catch (IOException e){
            System.err.println(e);
        }

    }

    public static void main (String [] args) {
        /**
         * Create a debug file used across the application
         */
        PrintWriter debug = null;
        try {
            debug = new PrintWriter(new BufferedWriter(new FileWriter("debug.txt", true)));
        }
        catch (IOException e){
            System.err.println(e);
        }

        // Set timestamp for the debug file
        if(debug != null)
            getCurrentTimeUsingCalendar(debug);

        // Check the commandline arguments
        if (args.length != 5) {
            System.out.println("There must be exactly 5 arguments: Pick up latitude and longitude" +
                    " and drop off latitude and longitude and also the number of passengers");
            debug.println("(BookingGo) Wrong number of commandline arguments");
            debug.println("(BookingGo) *******EXIT*******");
            System.exit(0);
        }

        // Set parameters from commandline argument
        Map<String, String> param = new HashMap<>();
        String pickUp = args[0] + "," + args[1];
        String dropOff = args[2] + "," +  args[3];
        param.put("pickup", pickUp);
        param.put("dropoff", dropOff);

        int noOfPassengers = Integer.parseInt(args[4]);

        // Array to hold the suppliers
        String[] suppliers = new String[3];
        suppliers[0] = "dave";
        suppliers[1] = "jeff";
        suppliers[2] = "eric";

        // Create HashMap to hold the offers
        Map<String, Offer> offers = new HashMap<>();

        // Get the offers from each supplier and keep the cheapest for each car type
        for (String supplier : suppliers)
            getTheOffersFromSuppliers(supplier, param, debug, offers);

        // Convert HashMap to Array
        Offer[] offersArray = offers.values().toArray(new Offer[0]);

        //Sort the list of offers in descending order based on the price
        bubbleSort(offersArray);

        // Before print the list of offers it takes into account the number of passengers
        System.out.println("Your offers in descending order:");
        System.out.println();
        for (Offer offer : offersArray)
            if (offer.getNoOfPassengers() >= noOfPassengers)
                System.out.println(offer.toString());

        debug.println("**********Finished*********");
        debug.println();
        debug.println();

        if(debug != null) {
            debug.close();
            if (debug.checkError()) {
                System.err.println("Something went wrong with the debug file");
            }
        }
    }
}
