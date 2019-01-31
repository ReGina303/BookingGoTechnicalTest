/**
 * BookingGo Technical Test
 * Last update: 30.01.2019
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
    public static void getCurrentTimeUsingCalendar(PrintWriter debug) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        debug.println("*****Date/time of run: " + timeStamp + "******");
    }

    /**
     * Implementation of bubble sort for array type Offer
     *
     * @param needToSort Offer type array that need to be sorted
     */
    public static void bubbleSort (Offer[] needToSort) {
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

    public static void main (String [] args) throws Exception {
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
        getCurrentTimeUsingCalendar(debug);

        // Check the commandline arguments
        if (args.length != 4) {
            System.out.println("There must be exactly 4 arguments: Pick up latitude and longitude" +
                    " and drop off latitude and longitude");
            debug.println("(BookingGo) Wrong number of commandline arguments");
            debug.println("(BookingGo) *******EXIT*******");
            System.exit(0);
        }

        /**
         * Create an instance of HttpUrlConnection
         */

        // Set parameters from commandline argument
        Map<String, String> param = new HashMap<>();
        String pickUp = args[0] + "," + args[1];
        String dropOff = args[2] + "," +  args[3];
        param.put("pickup", pickUp);
        param.put("dropoff", dropOff);

        // Get the required URL to the request
        String daveURL = ParameterAndResponseHandler.getURL("dave", param);

        debug.println("(BookingGo) The URL is: " + daveURL);

        URL dave = new URL(daveURL);

        HttpURLConnection connection = (HttpURLConnection) dave.openConnection();

        // Set timeouts
        connection.setConnectTimeout(2000);
        //connection.setReadTimeout(2000);

        // Set the request
        connection.setRequestMethod("GET");

        connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");

        int responseCode = connection.getResponseCode();
        debug.println("(BookingGo) The response code is: " + responseCode);

        // If the response code was OK then get the response
        if(responseCode == connection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            debug.println("(BookingGo) " + response.toString());
            System.out.println(response.toString());

            Offer[] results = ParameterAndResponseHandler.getCarsAndPrices(response.toString(), debug);
            debug.println("Before sorting...");
            for (Offer result : results)
                debug.println(result.toString());

            // Sort the list of offers
            bubbleSort(results);

            for (Offer result : results)
                System.out.println(result.toString());
        }

        connection.disconnect();

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
