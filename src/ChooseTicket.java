import java.sql.*;
import java.util.Scanner;
import model.Flight;
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
