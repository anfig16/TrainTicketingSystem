package com.group3.ticketing.project.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

/**
 * Seats entity representing a seat on a train.
 * Each seat has a unique seat number, availability status,
 * and is associated with a Train.
 */
@Entity
@Table(name = "seats") // Maps this entity to the "seats" table in the database
public class Seats {

    @Id
    private int seatNo;  // Unique identifier for a seat

    private boolean seatAvailable;  // Indicates if the seat is available (true) or booked (false)

    @ManyToOne
    @JoinColumn(name = "trainID")   // Foreign key linking seat to a train
    private Train train;

    /**
     * Default constructor required by JPA.
     */
    public Seats() {
    }

    /**
     * Constructor with all fields.
     *
     * @param seatNo        seat number (unique ID)
     * @param seatAvailable availability status of the seat
     * @param train         associated train
     */
    public Seats(int seatNo, boolean seatAvailable, Train train) {
        this.seatNo = seatNo;
        this.seatAvailable = seatAvailable;
        this.train = train;
    }

    /** @return the seat number */
    public int getSeatNo() {
        return seatNo;
    }

    /** @param seatNo sets the seat number */
    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    /** @return true if the seat is available, false if booked */
    public boolean isSeatAvailable() {
        return seatAvailable;
    }

    /** @param seatAvailable sets the availability status */
    public void setSeatAvailable(boolean seatAvailable) {
        this.seatAvailable = seatAvailable;
    }

    /** @return the train this seat belongs to */
    public Train getTrain() {
        return train;
    }

    /** @param train sets the train this seat belongs to */
    public void setTrain(Train train) {
        this.train = train;
    }
}
