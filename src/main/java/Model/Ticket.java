package com.group3.ticketing.project.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

/**
 * Ticket entity representing a booked train ticket.
 * Each ticket is uniquely identified by its ticket number
 * and is associated with a Passenger and a Seat.
 */
@Entity
@Table(name = "ticket") // Maps this entity to the "ticket" table in the database
public class Ticket {

    @Id
    private int ticketNum;   // Primary key - unique ticket number

    private String date;     // Date of the ticket (e.g., travel date)
    private double price;    // Price of the ticket

    @ManyToOne
    @JoinColumn(name = "pId") // Foreign key linking ticket to a passenger
    private Passenger passenger;

    @ManyToOne
    @JoinColumn(name = "seatNo") // Foreign key linking ticket to a seat
    private Seats seat;

    @ManyToOne
    @JoinColumn(name = "routeName")
    private Route route;
    
    /**
     * Default constructor required by JPA.
     */
    public Ticket() {
    }

    /**
     * Constructor with all fields.
     *
     * @param ticketNum unique ticket number
     * @param date      date of travel
     * @param price     price of the ticket
     * @param passenger associated passenger
     * @param seat      associated seat
     * @param route     associated route
     */
    public Ticket(int ticketNum, String date, double price, Passenger passenger, Seats seat, Route route) {
        this.ticketNum = ticketNum;
        this.date = date;
        this.price = price;
        this.passenger = passenger;
        this.seat = seat;
        this.route = route;
    }

    /** @return the unique ticket number */
    public int getTicketNum() {
        return ticketNum;
    }

    /** @param ticketNum sets the unique ticket number */
    public void setTicketNum(int ticketNum) {
        this.ticketNum = ticketNum;
    }

    /** @return the travel date of the ticket */
    public String getDate() {
        return date;
    }

    /** @param date sets the travel date of the ticket */
    public void setDate(String date) {
        this.date = date;
    }

    /** @return the ticket price */
    public double getPrice() {
        return price;
    }

    /** @param price sets the ticket price */
    public void setPrice(double price) {
        this.price = price;
    }

    /** @return the passenger linked to this ticket */
    public Passenger getPassenger() {
        return passenger;
    }

    /** @param passenger sets the passenger for this ticket */
    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    /** @return the seat linked to this ticket */
    public Seats getSeat() {
        return seat;
    }

    /** @param seat sets the seat for this ticket */
    public void setSeat(Seats seat) {
        this.seat = seat;
    }
    
    /** @return the route name linked to this ticket */
    public Route getRoute() {
        return route;
    }

    /** @param route sets the route name for this ticket */
    public void setRoute(Route route) {
        this.route = route;
    }
}
