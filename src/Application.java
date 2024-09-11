import database.PostgreDatabase;
import model.Airplane;
import model.Flight;

import java.util.*;
import java.sql.*;

public class Application
{
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // declaring scanner for input data
        Scanner in = new Scanner(System.in);

        // providing an object of database, especially for connection
        PostgreDatabase database = new PostgreDatabase();

        // declare repositories of airplane and flight for admin panel using database
        AirplaneRepository airplaneRepository = new AirplaneRepository(database);
        FlightRepository flightRepository = new FlightRepository(database);

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
                System.out.println("2. Admin panel");
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
