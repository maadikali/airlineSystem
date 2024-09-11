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
