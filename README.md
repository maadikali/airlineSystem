# airlineSystem
The project is on building an online airline ticket booking system in Java utilising Object-Oriented Programming concepts, and includes an in-depth discussion of the project structure, source code, and outcomes.

Astana IT University

Java Endterm Project Report on Airline Tickets System

Students Name: Madiyar Kaliyev

Table of Contents


1.	Introduction………………………………………………………………………………………………………………….2	
2.	Structure of the project ……………………………………………………………………………………………….2
3.	The source code……………………………………………………………………………………………………………2
4.	Screenshots…………………………………………………………………………………………………………………..2
5.	Conclusion…………………………………………………………………………………………………………………….2

Introduction
The topic of the Endterm Project is online booking of airplane tickets within Kazakhstan and between different cities around the world. My project will incorporate Object-Oriented Programming principles that will demonstrate my ability to work with the Java language.  In the following parts, I will carefully discuss the project's structure, that is, the OOP capabilities that I used in the project, step by step. After that, I will provide the application's source code in text format, with comments to help to understand the code, and the report will include the results of each component of the application, which will be described in detail.

1.	Structure of project
![structure](https://github.com/user-attachments/assets/99c92219-0830-474d-b275-ba1e3f8a5194)

 
3.	Diagram, provided by Intellij Idea
 


Here is the class and interface provided for database connectivity
package database;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDB {
    Connection getConnection() throws SQLException, ClassNotFoundException;
}

package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreDatabase implements IDB {
    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        String URL = "jdbc:postgresql://localhost:5432/airline";
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, "postgres", "Aa123456");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }
}

I have provided 5 models in project (Flight, Airplane, Ticket, Passenger, Person). 
Each of them contains their fields.
package model;

public class Airplane
{
    private int airplaneID;
    private String airplaneModel;
    private int businessSitsNumber;
    private int economySitsNumber;
    private int crewSitsNumber;

    public Airplane(int airplaneID, String airplaneModel, int businessSitsNumber, int economySitsNumber, int crewSitsNumber)
    {
        this.airplaneID=airplaneID;
        this.airplaneModel = airplaneModel;
        this.businessSitsNumber = businessSitsNumber;
        this.economySitsNumber = economySitsNumber;
        this.crewSitsNumber = crewSitsNumber;
    }

    public Airplane(String airplaneModel, int businessSitsNumber, int economySitsNumber, int crewSitsNumber) {
        this.airplaneModel = airplaneModel;
        this.businessSitsNumber = businessSitsNumber;
        this.economySitsNumber = economySitsNumber;
        this.crewSitsNumber = crewSitsNumber;
    }

    public int getAirplaneID() {
        return airplaneID;
    }

    public void setAirplaneID(int airplaneID) {
        this.airplaneID = airplaneID;
    }

    public String getAirplaneModel() {
        return airplaneModel;
    }

    public void setAirplaneModel(String airplaneModel) {
        this.airplaneModel = airplaneModel;
    }

    public int getBusinessSitsNumber() {
        return businessSitsNumber;
    }

    public void setBusinessSitsNumber(int businessSitsNumber) {
        this.businessSitsNumber = businessSitsNumber;
    }

    public int getEconomySitsNumber() {
        return economySitsNumber;
    }

    public void setEconomySitsNumber(int economSitsNumber) {
        this.economySitsNumber = economSitsNumber;
    }

    public int getCrewSitsNumber() {
        return crewSitsNumber;
    }

    public void setCrewSitsNumber(int crewSitsNumber) {
        this.crewSitsNumber = crewSitsNumber;
    }

    public String toString()
    {
        return "Airplane{" +
                "model=" + getAirplaneModel() + '\'' +
                ", business sits=" + getBusinessSitsNumber() + '\'' +
                ", economy sits=" + getEconomySitsNumber() + '\'' +
                ", crew sits=" + getCrewSitsNumber() + '\'' +
                '}';
    }
}

package model;

import java.sql.Timestamp;

public class Flight {
    private int flightID;
    private String departTo;
    private String departFrom;
    private String code;
    private String company;
    private Timestamp dateFrom;
    private Timestamp dateTo;
    //Flight "has-an" Airplane
    Airplane airplane;
    public Flight(){}

    public Flight(int flight_id, String departTo, String departFrom, String code, String company, Timestamp dateFrom,Timestamp dateTo, Airplane airplane)
    {
        this.flightID=flight_id;
        this.departTo = departTo;
        this.departFrom = departFrom;
        this.code = code;
        this.company = company;
        this.airplane = airplane;
        this.dateTo = dateTo;
        this.dateFrom = dateFrom;
    }

    public Flight(String departTo, String departFrom, String code, String company, Timestamp dateFrom, Timestamp dateTo, Airplane airplane) {
        this.departTo = departTo;
        this.departFrom = departFrom;
        this.code = code;
        this.company = company;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.airplane = airplane;
    }

    public int getFlightID() {
        return flightID;
    }

    public void setFlightID(int flightid) {
        this.flightID = flightid;
    }

    public String getDepartTo() {
        return departTo;
    }

    public void setDepartTo(String departTo) {
        this.departTo = departTo;
    }

    public String getDepartFrom() {
        return departFrom;
    }

    public void setDepartFrom(String departFrom) {
        this.departFrom = departFrom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Timestamp getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Timestamp dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Timestamp getDateTo() {
        return dateTo;
    }

    public void setDateTo(Timestamp dateTo) {
        this.dateTo = dateTo;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    //providing only airplane id when output flight, because airplane has many fields
    public String toString()
    {
        return "Flight{" + "airplane id= " + airplane.getAirplaneID() +
                ", date to=" +  getDateTo() + ", " + '\'' +
                ", date from='" + getDateFrom() + '\'' +
                ", depart from='" + getDepartFrom() + '\'' +
                ", depart to='" + getDepartTo() + '\'' +
                ", code=" + getCode() + '\'' +
                ", company=" + getCompany() + '\'' +
                ", code=" + getCode() + '\'' +
                '}';
    }
}

package model;

public class Ticket
{
    private int ticketId;
    private int price;
    private boolean classVip;
    private boolean status;
    Passenger passenger;
    Flight flight;

    public Ticket() {}

    public Ticket(int ticketId, int price, Flight flight, boolean classVip, Passenger passenger)
    {
        this.ticketId=ticketId;
        this.price = price;
        this.flight = flight;
        this.classVip = classVip;
        this.status = false;
        this.passenger=passenger;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getPrice() { return price; }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public boolean getClassVip() {
        return classVip;
    }

    public void setClassVip(boolean classVip) {
        this.classVip = classVip;
    }

    public boolean ticketStatus()
    {
        return status;
    }

    public void setTicketStatus(boolean status)
    {
        this.status = status;
    }

    public void serviceTax(){
        this.price *= 1.2;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public void setPrice(int price)
    {
        this.price = price;
        if(passenger.getAge() < 15)
        {
            price-= (int)price * 0.5;
            this.price = price;

        } else if(passenger.getAge() >= 60){
            this.price = 0;
        }
        serviceTax();
    }

    public String toString()
    {
        return"Ticket[" + "Price=" + getPrice() + " KZT, "
                + '\n' + getFlight()
                + '\n' + "VIP =" + getClassVip() + '\n' +
                getPassenger()+'\n'+ "Ticket was purchased = " + ticketStatus() + "\n]";
    }
}

package model;

//extend from Person with field which every person has
public class Passenger extends Person
{
    //in addition, passenger has email, phone number, card number, passport number and security code
    private String email;
    private String phoneNumber;
    private String cardNumber;
    private String passport;
    private int securityCode;

    public Passenger(){}

    public Passenger(String firstName, String secondName, int age, String gender,String email, String phoneNumber, String passport, String cardNumber,int securityCode)
    {
        super();
        this.securityCode=securityCode;
        this.cardNumber=cardNumber;
        this.passport=passport;
        this.email=email;
        this.phoneNumber=phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return super.getFirstName();
    }

    public String getSecondName() {
        return super.getSecondName();
    }

    public void setSecondName(String secondName) {
        super.setSecondName(secondName);
    }

    public void setFirstName(String firstName) {
        super.setFirstName(firstName);
    }

    public String getPassport() {
        return passport;
    }

    public void setGender(String gender) {
        super.setGender(gender);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getSecurityCode() {
        return securityCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setSecurityCode(int securityCode) {
        this.securityCode = securityCode;
    }

    @Override
    public void setAge(int age) {
        super.setAge(age);
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    @Override
    public String getGender() {
        return super.getGender();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int getAge() {
        return super.getAge();
    }

    @Override
    public String toString()
    {
        return "Passenger{" +
                "email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", passport='" + passport +
                '}';
    }
}

package model;

public abstract class Person
{
    private String firstName;
    private String secondName;
    private String gender;
    private int age;

    public Person(){}

    public Person(String firstName, String secondName, int age, String gender){
        this.age=age;
        this.firstName=firstName;
        this.secondName=secondName;
        this.gender=gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public String toString()
    {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}

Relationships between models:
1.	Passenger is-a Person
2.	Flight has-an Airplane
3.	Ticket has-a Flight

Then, I provided two classes: 1st for choosing a ticket, 2nd one for buy the ticket.
Here is the code with comments accordingly:

import java.sql.*;
import java.util.Scanner;
import database.PostgreDatabase;

public class ChooseTicket{
    // providing a scanner
    Scanner in = new Scanner(System.in);

    // declare connection, statement and result set references
    Connection con = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    PostgreDatabase postgreDatabase;

    // creating a statement of Buy Ticket class
    BuyTicket buyTicket;

    public ChooseTicket() throws SQLException, ClassNotFoundException {
        postgreDatabase = new PostgreDatabase();
        buyTicket = new BuyTicket();
    }

    public void choose(String city1, String city2) throws SQLException, ClassNotFoundException {
        // connecting to database
        con = postgreDatabase.getConnection();

        // preparing statement using sql injection
        // and get data from flight table
        preparedStatement = con.prepareStatement("SELECT * FROM flight WHERE depart_from =? and depart_to = ?");

        preparedStatement.setString(1, city1);
        preparedStatement.setString(2, city2);

        // get results of prepared statement
        resultSet = preparedStatement.executeQuery();


        if(resultSet!=null && resultSet.next())
        {
            while (resultSet.next())
            {
                System.out.println( resultSet.getInt("flight_id")+ " "+
                        resultSet.getInt("airplane_id")+ " "+
                        resultSet.getString("code")+ " "+
                        resultSet.getString("depart_from")+ " "+
                        resultSet.getString("depart_to")+ " "+
                        resultSet.getString("company_name")+ " "+
                        resultSet.getString("date_from")+ " "+
                        resultSet.getString("date_to"));

            }

            // ask to enter the ticket id
            System.out.println("\nEnter ID of ticket you want to choose:");
            int ticketId = in.nextInt();

            // preparing statement using sql injection for ticket table
            preparedStatement = con.prepareStatement("SELECT * FROM ticket WHERE ticket_id = ?");
            preparedStatement.setInt(1, ticketId);

            // get results of ticket table
            ResultSet ticket = preparedStatement.executeQuery();

            //check if we have data or not
            if(!ticket.next()){
                System.out.println("This ticket does not exist.");
            } else{
                // move to buyTicket method of BuyTicket class with our ticket id which we have input
                buyTicket.buyTicket(ticketId);
            }
        }
        else
        {
            System.out.println("This race is not available");
        }

    }


}

import database.PostgreDatabase;
import model.*;

import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class BuyTicket <T> {

    Scanner in = new Scanner(System.in);

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Ticket ticket;
    Flight flight;
    Passenger passenger;


    public BuyTicket() throws SQLException, ClassNotFoundException {
        passenger = new Passenger();
        ticket = new Ticket();
        flight = new Flight();

        PostgreDatabase database = new PostgreDatabase();
        connection = database.getConnection();
    }

    public void checkMismatch(T info, String regex) {
        // declare a flag for match
        boolean flag;

        do {
            // get information of inputten data
            info = (T) in.next();

            // declare a pattern using regex
            Pattern pattern = Pattern.compile(regex);

            // declare matcher for check that our info matches a regex
            Matcher matcher = pattern.matcher( (CharSequence) info);
            flag = matcher.lookingAt();

            //check if our flag is true
            if (!flag) {
                System.out.println("Invalid data! Try input again .");
            }
        } while(!flag);
    }

    public void showTicket()
    {
        try{
            System.out.println("You have bought a ticket for flight " + ticket.getFlight().getDepartFrom() + " - " + ticket.getFlight().getDepartTo() + "\nDetails:");
            System.out.println(this.ticket.toString());
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    public void buyTicket(int ticketId) throws SQLException {
        // declare flight id
        int flightId = 0;

        // preparing statement for ticket table with sql injection
        preparedStatement = connection.prepareStatement("select * from ticket where ticket_id = ?");

        // setting our ticket id to ? mark in query
        preparedStatement.setInt(1, ticketId);

        // check if ticket does exist or not
        ResultSet validTicket = preparedStatement.executeQuery();

        if(!validTicket.next()){
            System.out.println("This ticket does not exist.");
        } else {
            // get flight id(-s) from ticket table with our ticket id
            preparedStatement = connection.prepareStatement("SELECT flight_id FROM ticket WHERE ticket_id = ?");
            preparedStatement.setInt(1, ticketId);

            ResultSet flightResultSet = preparedStatement.executeQuery();

            // check if our flight result set has data or not
            if (flightResultSet.next()) {
                // if yes, then save its value
                flightId = flightResultSet.getInt("flight_id");
            }
            try {
                // ask user to input data
                // each time we input the data, we use checkMismatch method with pattern (regular expressions)
                System.out.println("Enter your First Name: ");
                String firstName = "";
                String regexName = "^[A-Za-z]+$";

                checkMismatch((T) firstName, regexName);
                passenger.setFirstName(firstName);

                System.out.println("Enter your Second name:");
                String secondName = "";
                checkMismatch((T) secondName, regexName);
                passenger.setSecondName(secondName);

                System.out.println("Enter your age:");
                Integer age = -1;
                String regexAge = "^[0-9]{1,2}$";
                checkMismatch((T) age, regexAge);
                in.nextLine();
                passenger.setAge(age);

                System.out.println("Enter your gender: ");
                String gender = "";
                String regexGender = "^[A-Za-z]{4,6}$";
                checkMismatch((T) gender, regexGender);
                passenger.setGender(gender);

                System.out.println("Enter your e-mail address");
                String email = "";
                String regexEmail = "^[A-Za-z][A-Za-z0-9]+@((gmail.com)|(yandex.ru)|(mail.ru)|(yahoo.com))$";
                checkMismatch((T) email, regexEmail);
                passenger.setEmail(email);

                System.out.println("Enter your phone number (+7):");
                String phoneNumber = "";
                String regexPhone = "^\\+7\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})$";
                checkMismatch((T) phoneNumber, regexPhone);
                passenger.setPhoneNumber(phoneNumber);

                System.out.println("Enter your passport number:");
                String passportNumber = "";
                String regexPassport = "[0-9]{7}$";
                checkMismatch((T) passportNumber, regexPassport);
                passenger.setPassport(passportNumber);

                System.out.println("Do you want to purchase?\n 1-YES 0-NO");
                int purchase = in.nextInt();

                if (purchase != 0) {
                    // prepare statement where we get information about flight and airplanes according to flight
                    preparedStatement = connection
                            .prepareStatement("SELECT * FROM flight, airplane " +
                                    "WHERE flight_id = ? AND flight.airplane_id = airplane.airplane_id");
                    preparedStatement.setInt(1, flightId);

                    flightResultSet = preparedStatement.executeQuery();


                    while (flightResultSet.next()) {
                        // if result set has data, we save airplane and flight
                        Airplane airplane = new Airplane(
                                flightResultSet.getInt("airplane_id"),
                                flightResultSet.getString("airplane_model"),
                                flightResultSet.getInt("business_sits"),
                                flightResultSet.getInt("economy_sits"),
                                flightResultSet.getInt("crew_sits"));

                        flight.setAirplane(airplane);
                        flight.setFlightID(flightResultSet.getInt("flight_id"));
                        flight.setCode(flightResultSet.getString("code"));
                        flight.setDepartFrom(flightResultSet.getString("depart_from"));
                        flight.setDepartTo(flightResultSet.getString("depart_to"));
                        flight.setCompany(flightResultSet.getString("company_name"));
                        flight.setDateTo(flightResultSet.getTimestamp("date_to"));
                        flight.setDateFrom(flightResultSet.getTimestamp("date_from"));
                    }

                    // preparing statement where we get information of ticket according to ticket id
                    preparedStatement = connection.prepareStatement("SELECT * FROM ticket WHERE ticket_id = ?");
                    preparedStatement.setInt(1, ticketId);

                    // getting result from statement
                    ResultSet rs2 = preparedStatement.executeQuery();

                    while (rs2.next()) {
                        // so we set all information we have got to ticket
                        ticket.setPassenger(passenger);
                        ticket.setTicketId(rs2.getInt("ticket_id"));
                        ticket.setFlight(flight);
                        ticket.setPrice(rs2.getInt("price"));
                        ticket.setClassVip(rs2.getBoolean("class_vip"));
                        ticket.setTicketStatus(true);

                        if (ticket.getClassVip() ) {
                            flight.getAirplane().setBusinessSitsNumber(flight.getAirplane().getBusinessSitsNumber() - 1);
                        } else {
                            flight.getAirplane().setEconomySitsNumber(flight.getAirplane().getEconomySitsNumber() - 1);
                        }

                        // so we change the status of ticket to true which means that this ticket has already bought
                        preparedStatement = connection.prepareStatement("UPDATE ticket SET status ='true' WHERE ticket_id = ?");
                        preparedStatement.setInt(1, ticketId);

                        //updating our database
                        preparedStatement.executeUpdate();
                    }

                    // select ticket from database
                    preparedStatement = connection.prepareStatement("SELECT * FROM ticket WHERE ticket_id = ?");
                    preparedStatement.setInt(1, ticketId);

                    //get price of ticket
                    ResultSet price = preparedStatement.executeQuery();

                    if (price.next()) {
                        ticket.setPrice(price.getInt("price"));
                    }

                    //output price of ticket
                    System.out.println("Your bill: " + ticket.getPrice() + "\n");

                    // entering our card number and security code
                    System.out.println("Enter your card number:");
                    String cardNumber = "";
                    String cardNumberRegex = "[0-9]{16}$";
                    checkMismatch((T) cardNumber, cardNumberRegex);
                    passenger.setCardNumber(cardNumber);

                    System.out.println("Enter your security code:");
                    Integer securityCode = -1;
                    String securityCodeRegex = "[0-9]{3}$";
                    checkMismatch((T) securityCode, securityCodeRegex);
                    passenger.setSecurityCode(securityCode);
                }
            } catch (PatternSyntaxException patternException) {
                patternException.printStackTrace();
            }
        }

    }

}

And last one is Application class, where we start working with our application
import database.PostgreDatabase;

import java.util.*;
import java.sql.*;

public class Application
{
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // declaring scanner for input data
        Scanner in = new Scanner(System.in);

        // providing an object of database, especially for connection
        PostgreDatabase database = new PostgreDatabase();

        // getting connection to database
        Connection con = database.getConnection();
        ResultSet resultSet = null;

        System.out.println("AIRLINE RESERVATION SYSTEM");

        // declare necessary variables for input
        boolean choice = false;
        int decision = 0;
        String city1 = null, city2 = null;

        // create an instance of ChooseTicket class
        ChooseTicket chooseTicket = new ChooseTicket();

        do
        {
            try
            {
                System.out.println("1. Buy ticket");
                System.out.println("3. Exit");

                // get decision from user
                decision = in.nextInt();
                in.nextLine();

                switch (decision)
                {
                    case 1:
                        System.out.println("Enter city you depart from: ");
                        city1 = in.nextLine();
                        System.out.println("Enter city you arrive to");
                        city2 = in.nextLine();

                        try{
                            // preparing statement with cities depart from and arrive to (for example, depart from Almaty to Nur-Sultan and etc.)
                            PreparedStatement preparedStatement = con.prepareStatement("SELECT flight.flight_id, depart_from, depart_to, ticket.ticket_id, class_vip, date_from, date_to " +
                                    "                               FROM flight INNER JOIN ticket ON ticket.flight_id = flight.flight_id " +
                                    "                               and depart_from = ? AND depart_to = ?");

                            preparedStatement.setString(1, city1);
                            preparedStatement.setString(2, city2);

                            // getting result, after providing query with 2 cities
                            resultSet = preparedStatement.executeQuery();

                            System.out.println("Available tickets with corresponding class are:\nif(t) = business class, if(f)=econom class\n");

                            // output all available tickets from Database
                            while (resultSet.next()){
                                System.out.println(resultSet.getInt("ticket_id")+ " "
                                        + resultSet.getString("depart_from")+" "+
                                        resultSet.getString("depart_to")+ " "+
                                        resultSet.getString("class_vip")+" "+
                                        resultSet.getString("date_from")+ " "+
                                        resultSet.getString("date_to"));
                            }

                            // move to choose method of object of ChooseTicket class
                            chooseTicket.choose(city1, city2);

                        } catch(SQLException | ClassNotFoundException exception){
                            exception.printStackTrace();
                        }

                        //after choosing we show which ticket we have bought

                        chooseTicket.buyTicket.showTicket();
                        break;
                    case 2:
                        return;
                    default:
                        System.out.println("Incorrect number was inputted, write correct number (1 or 2)");
                }
                choice = true;
            }
            catch(InputMismatchException exception)
            {
                System.out.println("Please input an integer");
                System.out.println("Required number, instead of: " + in.nextLine());
            }
        } while (!choice || (decision >= 1 || decision <= 2));
    }
}

So, the last one is what we got from console, so here it is: 
Null in ticket list means that ticket is available for buy and date_to means when we arrive with that ticket, if it is null, it is one-way ticket

Conclusion 
In conclusion, it must be mentioned that this project provided me with valuable experience when developing a Java project. I liked working with generics, regular expression, java database connectivity.
I believe that the program will considerably accelerate the booking of airline tickets.




