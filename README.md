# BookingGoTechnicalTest

## Setup

I use IntelliJ IDEA 2018.3.4 to build the project. It uses javac compiler, the target bytecode version is 1.8 and Maven 3.3.9 for the API. The JDK version is 1.8.

## Part 1

Documentation created to each class.

The application creates/extends debug file.

When you configure the run the main class is 'BookingGo' and the program arguments are:
1. 4 arguments which must be 4 double number stand for pick up and drop off latitude and longitude
    - It returns with offers from every supplier without constraints.
2. 5 arguments, 4 the same as by the first and the fifth is an integer (number of passengers)
    - It returns with offers from every supplier considering only those offers where the car type can hold the passengers
3. 5 arguments, 4 the same as by the first and the fifth is "dave"
    - Returns with offers from Dave without constraints
4. 6 arguments, 4 the same as by the first, the fifth is "dave" and sixth is an integer (number of passengers)
    - eturns with offers from Dave considering only those offers where the car type can hold the passengers
It doesn't accept less than 4 or more than 6 arguments however there is no validity check for the type of arguments.

## Part2

It uses port 8085 as default but in case of error of this port it can be changed via `resources/application.properties`.

When edit the configuration set the main class as `com.example.demo.BookingGoAPI`.

To check the result of latest query use: 
`curl -v localhost:8085/offers`
To submit a new query:
`curl -X POST localhost:8085/offers -H 'Content-type:application/json' -d '{"picklat": "51.470020", "picklong": "-0.454295", "droplat": "51.00000", "droplong": "1.0000", "passengers": "4"}'` where the numbers can be varied between the curly brackets
The new query will list the offers from all the suppliers considering the number of passengers, the cheapest offer for each car type in descending order.
