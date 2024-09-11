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
