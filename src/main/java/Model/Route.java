package com.group3.ticketing.project.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

/**
 * Route entity representing a train route in the system.
 * Each route has a unique name, start and end destinations, a date, 
 * and is associated with a Train.
 */
@Entity
@Table(name = "route") // Maps this entity to the "route" table in the database
public class Route {

    @Id
    private String routeName;   // Unique identifier for the route

    private String startDest;   // Starting destination of the route
    private String endDest;     // Ending destination of the route
    private String date;        // Date of the route (could be improved to LocalDate)

    @ManyToOne
    @JoinColumn(name = "trainID")   // Foreign key mapping to Train entity
    private Train train;

    /**
     * Default constructor required by JPA.
     */
    public Route() {
    }

    /**
     * Constructor with all fields.
     *
     * @param routeName unique route name
     * @param startDest starting destination
     * @param endDest   ending destination
     * @param date      date of the route
     * @param train     associated train
     */
    public Route(String routeName, String startDest, String endDest, String date, Train train) {
        this.routeName = routeName;
        this.startDest = startDest;
        this.endDest = endDest;
        this.date = date;
        this.train = train;
    }

    /** @return the route name */
    public String getRouteName() {
        return routeName;
    }

    /** @param routeName sets the route name */
    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    /** @return the starting destination */
    public String getStartDest() {
        return startDest;
    }

    /** @param startDest sets the starting destination */
    public void setStartDest(String startDest) {
        this.startDest = startDest;
    }

    /** @return the ending destination */
    public String getEndDest() {
        return endDest;
    }

    /** @param endDest sets the ending destination */
    public void setEndDest(String endDest) {
        this.endDest = endDest;
    }

    /** @return the route date */
    public String getDate() {
        return date;
    }

    /** @param date sets the route date */
    public void setDate(String date) {
        this.date = date;
    }

    /** @return the associated train */
    public Train getTrain() {
        return train;
    }

    /** @param train sets the associated train */
    public void setTrain(Train train) {
        this.train = train;
    }
}
